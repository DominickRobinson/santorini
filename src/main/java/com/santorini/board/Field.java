package com.santorini.board;

import com.santorini.game.Worker;

/**
 * The Field class represents a single square on the Santorini game board.
 * It tracks its position, any worker present, and the height of the tower built on it.
 */
public class Field {
    private Position position;
    private Tower tower;
    private Worker worker;

    /**
     * Initializes a field at a specific position with a new tower.
     * @param position The position of the field on the game board.
     */
    public Field(Position position) {
        this.position = position;
        this.tower = new Tower();
    }

    /**
     * @return True if a worker is currently occupying this field, otherwise false.
     */
    public boolean isOccupied() {
        return this.worker != null;
    }

    /**
     * @return True if the tower on this field has a dome, otherwise false.
     */
    public boolean hasDome() {
        return this.tower.hasDome();
    }

    /**
     * @return The current height of the tower on this field.
     */
    public int getTowerHeight() {
        return this.tower.getHeight();
    }

    /**
     * @return The position of this field on the board.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Builds an additional level onto the tower at this field.
     */
    public void buildTower() {
        this.tower.build();
    }

    /**
     * @return The worker currently on this field, or null if none is present.
     */
    public Worker getWorker() {
        return this.worker;
    }

    /**
     * Assigns a worker to this field, ensuring it is not already occupied.
     * @param worker The worker to be placed on this field.
     * @throws IllegalStateException if the field is already occupied by another worker.
     */
    public void assignWorker(Worker worker) {
        if (this.isOccupied()) {
            throw new IllegalStateException("Cannot place a worker on an occupied field!");
        }
        this.worker = worker;
    }
    
    /**
     * Removes the worker from this field.
     */
    public void removeWorker() {
        this.worker = null;
    }

    /**
     * Checks if this field meets the winning condition based on tower height.
     * @param winningHeight The tower height required for victory.
     * @return True if the tower height matches the winning height, otherwise false.
     */
    public boolean isWinningField(int winningHeight) {
        return this.tower.getHeight() == winningHeight;
    }
}