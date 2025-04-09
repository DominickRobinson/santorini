package com.santorini.game;

import com.santorini.board.Tile;

/**
 * The Worker class represents a worker in the Santorini game.
 * Workers are placed on tiles, can move to adjacent tiles, and build towers.
 */
public class Worker {
    private Tile tile;

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
     * Checks if the worker is standing on a winning tile.
     * @param winningHeight The height that determines victory.
     * @return True if the worker is on a winning tile, otherwise false.
     */
    public boolean isOnWinningTile(int winningHeight) {
        return this.tile.isWinningTile(winningHeight);
    }
}
