package com.santorini.game;

import com.santorini.board.Grid;
import com.santorini.board.Field;
import com.santorini.board.Tower;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;


public class Game {
    private Grid grid;
    private final List<Player> players;
    private Queue<Player> turnQueue;
    private Player currentPlayer = null;
    private boolean gameWon = false;
    private Player winner = null;
    private static final int WINNING_HEIGHT = 3;


    public Game() {
        this.grid = new Grid();
        this.players = new ArrayList<>();
        this.turnQueue = new LinkedList<>();

        for (int i = 0; i < 2; i++) {
            Player player = new Player(i+1);
            players.add(player);
            turnQueue.add(player);
        }

        this.currentPlayer = this.players.get(0);
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
    
    public List<Field> getValidMoveFields(Field currentField) {
        List<Field> adjacentFields = this.grid.getAdjacentFields(currentField);
        
        List<Field> validFields = new ArrayList<>();

        for (Field field : adjacentFields) {

            if (!field.isOccupied() && field.getTower().getLevels() - currentField.getTower().getLevels() <= 1 && !field.getTower().hasDome()) {
                validFields.add(field);
            }
        }

        return validFields;
    }

    public List<Field> getValidBuildFields(Field currentField) {
        List<Field> adjacentFields = grid.getAdjacentFields(currentField);
        
        List<Field> validFields = new ArrayList<>();

        for (Field field : adjacentFields) {
            
            Tower tower = field.getTower();

            if (!field.isOccupied() && !tower.hasDome()) {
                validFields.add(field);
            }
        }

        return validFields;
    }

    public boolean isValidMove(Worker worker, Field to) {
        Field from = worker.getField();
        return getValidMoveFields(from).contains(to);
    }

    public boolean isValidBuild(Worker worker, Field at) {
        Field from = worker.getField();    
        return getValidBuildFields(from).contains(at);
    }

    public void nextTurn() {
        turnQueue.add(turnQueue.poll());
        this.currentPlayer = turnQueue.peek();
    }

    public boolean checkWinCondition(Worker worker) {
        return worker.getField().getTower().getLevels() == this.WINNING_HEIGHT;
    }

    public void endGame(Player winner) {
        this.gameWon = true;
        this.winner = winner;
    }

}