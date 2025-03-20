package com.santorini.game;

import com.santorini.board.Grid;
import com.santorini.board.Field;
import java.util.Queue;
import java.util.LinkedList;

/**
 * The Game class manages players, turns, movement, building, and win conditions.
 */
public class Game {
    private final Grid grid;
    private final Queue<Player> playersQueue;
    private Player currentPlayer;
    private boolean gameWon = false;
    private Player winner;
    private static final int WINNING_HEIGHT = 3;

    /**
     * Initializes a new game with a grid and two players.
     */
    public Game() {
        this.grid = new Grid();
        this.playersQueue = new LinkedList<>();

        for (int i = 0; i < 2; i++) {
            Player player = new Player(i + 1);
            playersQueue.add(player);
        }

        this.currentPlayer = playersQueue.peek();
    }

    /**
     * @return The game grid.
     */
    public Grid getGrid() {
        return this.grid;
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
     * Spawns a worker for the given player on a specific field.
     * @param player The player to spawn the worker for.
     * @param field The field where the worker will be placed.
     */
    public void spawnWorker(Player player, Field field) {
        player.spawnWorker(field);
    }

    /**
     * Checks if a worker's move is valid based on game rules.
     * @param worker The worker to move.
     * @param to The destination field.
     * @return True if the move is valid, otherwise false.
     */
    public boolean isValidMove(Worker worker, Field to) {
        Field from = worker.getField();
        return currentPlayer.ownsWorker(worker) && 
                grid.getAdjacentFields(from).contains(to) &&
                !to.isOccupied() &&
                !to.hasDome() &&
                to.getTowerHeight() - from.getTowerHeight() <= 1;
    }

    /**
     * Checks if a worker's build action is valid.
     * @param worker The worker building.
     * @param at The field to build on.
     * @return True if the build action is valid, otherwise false.
     */
    public boolean isValidBuild(Worker worker, Field at) {
        Field from = worker.getField();
        return currentPlayer.ownsWorker(worker) && grid.getAdjacentFields(from).contains(at) && !at.isOccupied()
                && !at.hasDome();
    }
    
    /**
     * Moves a worker to a new field.
     * @param worker The worker to move.
     * @param to The destination field.
     */
    public void move(Worker worker, Field to) {
        currentPlayer.moveTo(worker, to);
    }

    /**
     * Builds a tower at a specified field.
     * @param worker The worker building.
     * @param at The field where the tower is built.
     */
    public void build(Worker worker, Field at) {
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
     * Checks if the worker is on a winning field (level 3 tower).
     * @param worker The worker to check.
     * @return True if the worker reached level 3, otherwise false.
     */
    public boolean checkWinCondition(Worker worker) {
        return worker.isOnWinningField(WINNING_HEIGHT);
    }

    /**
     * Ends the game and declares a winner.
     * @param winner The winning player.
     */
    public void endGame(Player winner) {
        this.gameWon = true;
        this.winner = winner;
    }
}
