package com.santorini.game;

import com.santorini.board.Tile;

/**
 * The Worker class represents a worker in the Santorini game.
 * Workers are placed on tiles, can move to adjacent tiles, and build towers.
 */
public class Worker {
    private Tile tile;
    private Tile previousTile;

    /**
     * Initializes a worker at a specific tile.
     * @param startTile The tile where the worker starts.
     */
    public Worker(Tile startTile) {
        this.tile = startTile;
        this.tile.assignWorker(this);
    }

    /**
     * @return The tile where the worker is currently positioned.
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Moves the worker to a new tile.
     * @param to The destination tile.
     */
    public void moveTo(Tile to) {
        this.previousTile = this.tile;
        this.tile.removeWorker();
        this.tile = to;
        this.tile.assignWorker(this);
    }

    /**
     * Builds a tower at a specified tile.
     * @param at The tile where the tower is built.
     */
    public void buildAt(Tile at) {
        at.buildTower();
    }

    /**
     * @return The worker's previously occupied tile.
     */
    public Tile getPreviousTile() {
        return previousTile;
    }

    /**
     * Checks if the worker is on a tile with a tower of a particular height.
     * @param height The height to check.
     * @return True if the occupied tile has a tower with the particular height, otherwise false.
     */
    public boolean onTowerWithHeight(int height) {
        return tile.getTowerHeight() == height;
    }

    public void swapWith(Worker other) {
        Tile myTile = this.tile;
        Tile theirTile = other.tile;
    
        myTile.removeWorker();
        theirTile.removeWorker();
    
        myTile.assignWorker(other);
        theirTile.assignWorker(this);
    
        this.tile = theirTile;
        this.previousTile = myTile;
    
        other.tile = myTile;
        other.previousTile = theirTile;
    }
    
}
