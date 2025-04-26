package com.santorini.board;

import com.santorini.game.Worker;

/**
 * The Tile class represents a single square on the Santorini game board.
 * It tracks its position, any worker present, and the height of the tower built on it.
 */
public class Tile {
    private Position position;
    private Tower tower;
    private Worker worker;

    /**
     * Initializes a tile at a specific position with a new tower.
     * @param position The position of the tile on the game board.
     */
    public Tile(Position position) {
        this.position = position;
        this.tower = new Tower();
    }

    /**
     * @return True if a worker is currently occupying this tile, otherwise false.
     */
    public boolean isOccupied() {
        return this.worker != null;
    }

    /**
     * @return True if the tower on this tile has a dome, otherwise false.
     */
    public boolean hasDome() {
        return this.tower.hasDome();
    }

    /**
     * @return The current height of the tower on this tile.
     */
    public int getTowerHeight() {
        return this.tower.getHeight();
    }

    /**
     * @return The position of this tile on the board.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Builds an additional level onto the tower at this tile.
     */
    public void buildTower() {
        this.tower.build();
    }

    /**
     * @return The worker currently on this tile, or null if none is present.
     */
    public Worker getWorker() {
        return this.worker;
    }

    /**
     * Assigns a worker to this tile, ensuring it is not already occupied.
     * @param worker The worker to be placed on this tile.
     * @throws IllegalStateException if the tile is already occupied by another worker.
     */
    public void assignWorker(Worker worker) {
        if (this.isOccupied()) {
            throw new IllegalStateException("Cannot place a worker on an occupied tile!");
        }
        this.worker = worker;
    }
    
    /**
     * Removes the worker from this tile.
     */
    public void removeWorker() {
        this.worker = null;
    }

    /**
     * Checks if this tile has a tower with a given height.
     * @param height The height to compare to.
     * @return True if the tower height matches the given height, otherwise false.
     */
    public boolean hasTowerWithHeight(int height) {
        return this.tower.getHeight() == height;
    }
}