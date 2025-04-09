package com.santorini.board;

/**
 * The Tower class represents a tower that can be built on a tile in the Santorini game.
 * Towers can have up to a maximum of four levels, with the last level forming a dome.
 */
public class Tower {
    private int levels;
    private static final int MAX_LEVELS = 4;

    /**
     * Initializes a new tower with zero levels.
     */
    public Tower() {
        this.levels = 0;
    }

    /**
     * Builds an additional level onto the tower, up to the maximum allowed height.
     * @throws IllegalStateException if the tower has already reached the maximum level (dome).
     */
    public void build() {
        if (this.levels < MAX_LEVELS) {
            this.levels++;
        } else {
            throw new IllegalStateException("Cannot build when a tower already has a dome!");
        }
    }

    /**
     * @return The current height of the tower in levels.
     */
    public int getHeight() {
        return this.levels;
    }

    /**
     * @return True if the tower has reached the maximum height and has a dome, otherwise false.
     */
    public boolean hasDome() {
        return this.levels == MAX_LEVELS;
    }
}
