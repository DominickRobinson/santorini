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

    /**
     * Initializes a new game with a board and two players.
     */
    public Game() {
        this.board = new Board();
        this.playersQueue = new LinkedList<>();

        for (int i = 0; i < 2; i++) {
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
     * Spawns a worker for the given player on a specific tile.
     * @param player The player to spawn the worker for.
     * @param tile The tile where the worker will be placed.
     */
    public void spawnWorker(Player player, Tile tile) {
        player.spawnWorker(tile);
    }

    /**
     * Checks if a worker's move is valid based on game rules.
     * @param worker The worker to move.
     * @param to The destination tile.
     * @return True if the move is valid, otherwise false.
     */
    public boolean isValidMove(Worker worker, Tile to) {
        Tile from = worker.getTile();
        return currentPlayer.ownsWorker(worker) && 
                board.getAdjacentTiles(from).contains(to) &&
                !to.isOccupied() &&
                !to.hasDome() &&
                to.getTowerHeight() - from.getTowerHeight() <= 1;
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
     * Moves a worker to a new tile.
     * @param worker The worker to move.
     * @param to The destination tile.
     */
    public void move(Worker worker, Tile to) {
        currentPlayer.moveTo(worker, to);
    }

    /**
     * Builds a tower at a specified tile.
     * @param worker The worker building.
     * @param at The tile where the tower is built.
     */
    public void build(Worker worker, Tile at) {
        currentPlayer.buildAt(worker, at);
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

                row.put(tileJson);
            }
            boardJson.put(row);
        }

        gameJson.put("board", boardJson);
        return gameJson;
    }
}
