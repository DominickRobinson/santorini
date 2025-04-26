package com.santorini.game;

import com.santorini.board.Board;
import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.actions.TurnAction;
import com.santorini.godcards.DefaultLogicCard;
import com.santorini.godcards.GodCard;

import java.util.Queue;
import java.util.LinkedList;

/**
 * The Game class manages players, turns, movement, building, and win conditions.
 */
public class Game {
    private final Board board;
    private final Queue<Player> playersQueue;
    private Player currentPlayer;
    private boolean gameWon = false;
    private Player winner;
    private static final int WINNING_HEIGHT = 3;
    private static final int MAX_CLIMB_HEIGHT = 1;
    private static final int WORKERS_PER_PLAYER = 2;
    
    private Phase phase = Phase.SPAWN;
    private Worker selectedWorker = null;

    /**
     * Initializes a new game with a board and two players.
     */
    public Game() {
        this.board = new Board();
        this.playersQueue = new LinkedList<>();

        for (int i = 0; i < WORKERS_PER_PLAYER; i++) {
            Player player = new Player(i + 1);
            playersQueue.add(player);
        }

        this.currentPlayer = playersQueue.peek();
    }

    /**
     * @return The game board.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * @return The current player whose turn it is.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

        /**
     * @return The current phase of the game.
     */
    public Phase getPhase() {
        return this.phase;
    }

    /**
     * Changes the phase of the game.
     * @param phase The new phase.
     */
    public void setPhase(Phase phase) {
        if (this.phase == Phase.GAME_OVER && phase == Phase.GAME_OVER) {
            return; 
        }
        this.phase = phase;
    
        if (phase == Phase.GAME_OVER) {
            endGame(getCurrentPlayer());
        }
    }
    
    
    /**
     * @return The currently selected worker.
     */
    public Worker getSelectedWorker() {
        return this.selectedWorker;
    }

    /**
     * Changes the currently selected worker.
     * @param w The new selected worker.
     */
    public void setSelectedWorker(Worker w) {
        this.selectedWorker = w;
    }

    /**
     * Get the tile at a given position on the board.
     * @param position The position at which you want the tile.
     * @return The tile at the given position.
     */
    public Tile getTileAt(Position position) {
        return board.getTileAt(position);
    }

    /**
     * @return True if the game has been won, otherwise false.
     */
    public boolean isGameWon() {
        return this.gameWon;
    }

    /**
     * @return The player who won the game, or null if the game is not over.
     */
    public Player getWinner() {
        return this.winner;
    }

    /**
     * Checks if the a player can spawn a worker on a tile.
     * @param player The player that wants to spawn a worker.
     * @param tile The tile that the worker should be spawned on, if possible.
     * @return True if the spawn was successful, otherwise false.
     */
    public boolean isValidSpawn(Player player, Tile tile) {
        return !tile.isOccupied() && !tile.hasDome();
    }

    /**
     * Spawns a worker for the current player on a specific tile.
     * @param tile The tile where the worker will be placed.
     * @return True if the spawn was successful, otherwise false.
     */
    public boolean trySpawn(Tile tile) {
        if (!isValidSpawn(currentPlayer, tile)) return false;
        currentPlayer.spawn(tile);
        if (playerHasSpawnedAllWorkers(currentPlayer)) {
            nextTurn();
            if (allPlayersHaveSpawned()) setPhase(Phase.TURN_ACTION);
        }
        return true;
    }

    /**
     * Checks if a player has spawned all of their workers.
     * @param p The player you want to check.
     * @return True if the player has spawned all of the workers, otherwise false.
     */
    private boolean playerHasSpawnedAllWorkers(Player p) {
        return p.getWorkerCount() >= WORKERS_PER_PLAYER;
    }    

    /**
     * @return True if all players have spawned all workers, otherwise false.
     */
    private boolean allPlayersHaveSpawned() {
        for (Player p : playersQueue) {
            if (!playerHasSpawnedAllWorkers(p)) {
                return false;
            }
        }
        return true;
    }
    
    public void handleTilePress(Tile tile) {
        if (phase == Phase.SPAWN) {
            trySpawn(tile);
            return;
        }

        if (phase == Phase.TURN_ACTION && !gameWon) {
            var iterator = getGodCard(currentPlayer).getTurnIterator();
            var action = iterator.getCurrent();
            if (action != null && action.attempt(tile, this)) {
                Player player = getCurrentPlayer();
                if (player.getGodCard().checkWinCondition(getSelectedWorker(), this)) {
                    endGame(player);
                    return;
                }
                iterator.advance();
                if (iterator.isDone()) {
                    getGodCard(currentPlayer).endTurn(this);
                    iterator.reset();
                }
            }
        }
    }
    
    public String getCurrentInstruction() {
        if (getPhase() == Phase.SPAWN) 
            return "Spawn";
        if (getPhase() == Phase.TURN_ACTION) 
            return getCurrentAction().getName();
        if (getPhase() == Phase.GAME_OVER)
            return "Game Over";
        return "";
    }

    public boolean canSkip() {
        var iterator = currentPlayer.getGodCard().getTurnIterator();
        var action = iterator.getCurrent();
        if (action != null)
            return action.isSkippable();
        return false;
    }

    public boolean trySkip() {
        if (phase != Phase.TURN_ACTION || gameWon)
            return false;
        var iterator = currentPlayer.getGodCard().getTurnIterator();
        var action = iterator.getCurrent();
        if (action == null || !action.isSkippable())
            return false;
        iterator.advance();
        if (iterator.isDone()) {
            currentPlayer.getGodCard().endTurn(this);
            iterator.reset();
        }
        return true;
    }
    
    /**
     * Ends the current player's turn and switches to the next player.
     */
    public void nextTurn() {
        setSelectedWorker(null);
        playersQueue.add(playersQueue.poll());
        this.currentPlayer = playersQueue.peek();
    }

    /**
     * Checks if the worker is on a winning tile (level 3 tower).
     * @param worker The worker to check.
     * @return True if the worker reached level 3, otherwise false.
     */
    public boolean checkWinCondition(Worker worker) {
        return currentPlayer.getGodCard().checkWinCondition(worker, this);
    }

    /**
     * Ends the game and declares a winner.
     * @param winner The winning player.
     */
    public void endGame(Player winner) {
        this.gameWon = true;
        this.winner = winner;
        setPhase(Phase.GAME_OVER);
    }

    public Player getWorkerOwner(Worker worker) {
        for (Player player : playersQueue) {
            if (player.ownsWorker(worker)) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayer(int id) {
        for (Player p : playersQueue) {
            if (p.getID() == id)
                return p;
        }
        throw new IllegalArgumentException("Player not found with id: " + id);
    }
    
    public int getWinningHeight() {
        return WINNING_HEIGHT;
    }

    public int getMaxClimbHeight() {
        return MAX_CLIMB_HEIGHT;
    }

    public boolean areAdjacent(Tile a, Tile b) {
        return board.areAdjacent(a, b);
    }

    private GodCard getGodCard(Player player) {
        return player.getGodCard() != null ? player.getGodCard() : new DefaultLogicCard();
    }

   public TurnAction getCurrentAction() {
        if (phase != Phase.TURN_ACTION) return null;
        return getGodCard(currentPlayer).getTurnIterator().getCurrent();
    }
}
