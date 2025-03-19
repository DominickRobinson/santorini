package com.santorini.board;

public class Tower {
    private int levels;
    private static final int MAX_LEVELS = 4;


    public Tower () {
        this.levels = 0;
    }

    public void build () {
        if (this.levels < MAX_LEVELS) {
            this.levels++;
        } else {
            throw new IllegalStateException("Cannot build when a tower already has a dome!");
        }
    }

    public int getLevels() {
        return this.levels;
    }

    public boolean hasDome() {
        return this.levels == MAX_LEVELS;
    }

}