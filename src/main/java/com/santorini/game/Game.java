package com.santorini.game;

import com.santorini.board.Grid;
import com.santorini.board.Field;
import java.util.Queue;
import java.util.LinkedList;

public class Game {
    private final Grid grid;
    private final Queue<Player> playersQueue;
    private Player currentPlayer;
    private boolean gameWon = false;
    private Player winner;
    private static final int WINNING_HEIGHT = 3;

    public Game() {
        this.grid = new Grid();
        this.playersQueue = new LinkedList<>();

        for (int i = 0; i < 2; i++) {
            Player player = new Player(i + 1);
            playersQueue.add(player);
        }

        this.currentPlayer = playersQueue.peek();
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean isGameWon() {
        return this.gameWon;
    }

    public Player getWinner() {
        return this.winner;
    }

    public void spawnWorker(Player player, Field field) {
        player.spawnWorker(field);
    }

    public boolean isValidMove(Worker worker, Field to) {
        Field from = worker.getField();
        return grid.getAdjacentFields(from).contains(to) &&
               !to.isOccupied() &&
               !to.hasDome() &&
               to.getTowerHeight() - from.getTowerHeight() <= 1;
    }

    public boolean isValidBuild(Worker worker, Field at) {
        Field from = worker.getField();
        return grid.getAdjacentFields(from).contains(at) &&
               !at.isOccupied() &&
               !at.hasDome();
    }

    public void nextTurn() {
        playersQueue.add(playersQueue.poll());
        this.currentPlayer = playersQueue.peek();
    }

    public boolean checkWinCondition(Worker worker) {
        return worker.isOnWinningField(WINNING_HEIGHT);
    }

    public void endGame(Player winner) {
        this.gameWon = true;
        this.winner = winner;
    }
}
