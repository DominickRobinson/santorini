package com.santorini.game;

import com.santorini.board.Board;
import com.santorini.board.Position;
import com.santorini.board.Tile;

import java.util.Queue;
import java.util.LinkedList;
import org.json.JSONObject;
import org.json.JSONArray;

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
    private static final int WORKERS_PER_PLAYER = 2;

    public enum Phase {
        SPAWN, SELECT, MOVE, BUILD, GAME_OVER
    }    

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
        Phase oldPhase = this.phase;
        this.phase = phase;

        if (phase == Phase.SELECT) {
            boolean hasMoveableWorker = currentPlayer.getWorkers().stream().anyMatch(worker ->
            board.getAdjacentTiles(worker.getTile()).stream().anyMatch(t -> isValidMove(worker, t)));
            
            if (!hasMoveableWorker) {
                nextTurn();
                endGame(currentPlayer);
            }
        } else if (oldPhase == Phase.MOVE) {
            if (this.selectedWorker != null && checkWinCondition(this.selectedWorker)) {
                endGame(currentPlayer);
            }
        }
    }

    /**
     * Changes the currently selected worker.
     * @param w The new selected worker.
     */
    public void setSelectedWorker(Worker w) {
        this.selectedWorker = w;
    }

    /**
     * @return The currently selected worker.
     */
    public Worker getSelectedWorker() {
        return this.selectedWorker;
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
        if (!isValidSpawn(currentPlayer, tile))
            return false;
        currentPlayer.spawn(tile);

        if (playerHasSpawnedAllWorkers(currentPlayer)) {
            nextTurn();
            if (allPlayersHaveSpawned()) {
                setPhase(Phase.SELECT);
            }
        }

        return true;
    }

    /**
     * Checks if a player can spawn a worker on a tile.
     * @param player The player that wants to select a worker.
     * @param tile The tile that will be checked to see if it has a worker can be selected.
     * @return True if a worker can be selected on the tile, otherwise false.
     */
    public boolean isValidSelect(Player player, Tile tile) {
        Worker worker = tile.getWorker();

        if (phase != Phase.SELECT || worker == null || !player.ownsWorker(worker)) {
            return false;
        }

        return board.getAdjacentTiles(tile).stream().anyMatch(t -> isValidMove(worker, t));
    }
    
    /**
     * Attempts to select a worker on a given tile.
     * @param tile The tile from which a worker may be selected, if possible and legal.
     * @return True if the selection was successful, otherwise false
     */
    public boolean trySelect(Tile tile) {
        if (!isValidSelect(currentPlayer, tile)) return false;
    
        setSelectedWorker(tile.getWorker());
        setPhase(Phase.MOVE);
        return true;
    }
    
    /**
     * Checks if a worker's move is valid based on game rules.
     * @param worker The worker to move.
     * @param to The destination tile.
     * @return True if the move is valid, otherwise false.
     */
    public boolean isValidMove(Worker worker, Tile to) {
        Tile from = worker.getTile();
        return currentPlayer.ownsWorker(worker) && board.getAdjacentTiles(from).contains(to) && !to.isOccupied()
                && !to.hasDome() && to.getTowerHeight() - from.getTowerHeight() <= 1;
    }

    /**
     * Moves the selected worker to a new tile.
     * @param to The destination tile.
     * @return True if the move was successful, otherwise false.
     */
    public boolean tryMove(Tile to) {
        if (selectedWorker == null || !isValidMove(selectedWorker, to))
            return false;
        currentPlayer.moveTo(selectedWorker, to);

        setPhase(Phase.BUILD);

        return true;
    }
    
    /**
     * Checks if a worker's build action is valid.
     * @param worker The worker building.
     * @param at The tile to build on.
     * @return True if the build action is valid, otherwise false.
     */
    public boolean isValidBuild(Worker worker, Tile at) {
        Tile from = worker.getTile();
        return currentPlayer.ownsWorker(worker) && board.getAdjacentTiles(from).contains(at) && !at.isOccupied()
                && !at.hasDome();
    }
    
    /**
     * Builds a tower at a specified tile.
     * @param at The tile where the tower is built.
     * @return True if the build was successful, otherwise false.
     */
    public boolean tryBuild(Tile at) {
        if (selectedWorker == null || !isValidBuild(selectedWorker, at))
            return false;

        currentPlayer.buildAt(selectedWorker, at);
        setSelectedWorker(null);
        nextTurn();
        setPhase(Phase.SELECT);

        return true;
    }

    /**
     * Ends the current player's turn and switches to the next player.
     */
    public void nextTurn() {
        playersQueue.add(playersQueue.poll());
        this.currentPlayer = playersQueue.peek();
    }

    /**
     * Checks if the worker is on a winning tile (level 3 tower).
     * @param worker The worker to check.
     * @return True if the worker reached level 3, otherwise false.
     */
    public boolean checkWinCondition(Worker worker) {
        return worker.isOnWinningTile(WINNING_HEIGHT);
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

    private Player getWorkerOwner(Worker worker) {
        for (Player player : playersQueue) {
            if (player.ownsWorker(worker)) {
                return player;
            }
        }
        return null;
    }
    
    /**
     * @return The game state in JSON format.
     */
    public JSONObject toJSON() {
        JSONObject gameJson = new JSONObject();
        gameJson.put("currentPlayer", getCurrentPlayer().getID());
        gameJson.put("phase", getPhase().toString().toLowerCase());
        gameJson.put("winner", (isGameWon()) ? winner.getID() : JSONObject.NULL);

        JSONArray boardJson = new JSONArray();
        for (int y = 0; y < board.getHeight(); y++) {
            JSONArray row = new JSONArray();
            for (int x = 0; x < board.getWidth(); x++) {
                Tile tile = board.getTileAt(new Position(x, y));
                JSONObject tileJson = new JSONObject();

                tileJson.put("x", x);
                tileJson.put("y", y);
                tileJson.put("height", tile.getTowerHeight());
                tileJson.put("hasDome", tile.hasDome());

                Worker worker = tile.getWorker();
                if (worker != null) {
                    Player owner = getWorkerOwner(worker);
                    tileJson.put("worker", owner.getID());
                } else {
                    tileJson.put("worker", JSONObject.NULL);
                }

                if (phase == Phase.SPAWN && isValidSpawn(currentPlayer, tile)) {
                    tileJson.put("isValidSpawn", true);
                } else if (phase == Phase.SELECT) {
                    if (isValidSelect(currentPlayer, tile)) {
                        tileJson.put("isValidSelect", true);
                    }
                } else if (phase == Phase.MOVE) {
                    if (selectedWorker != null && isValidMove(selectedWorker, tile)) {
                        tileJson.put("isValidMove", true);
                    }
                } else if (phase == Phase.BUILD && selectedWorker != null) {
                    if (isValidBuild(selectedWorker, tile)) {
                        tileJson.put("isValidBuild", true);
                    }
                }

                if (isGameWon() && selectedWorker != null && selectedWorker.getTile().equals(tile)) {
                    tileJson.put("isWinningTile", true);
                }

                row.put(tileJson);
            }
            boardJson.put(row);
        }

        gameJson.put("board", boardJson);
        
        return gameJson;
    }
}
