package com.santorini.game;

import com.santorini.board.Field;

/**
 * The Worker class represents a worker in the Santorini game.
 * Workers are placed on fields, can move to adjacent fields, and build towers.
 */
public class Worker {
    private Field field;

    /**
     * Initializes a worker at a specific field.
     * @param startField The field where the worker starts.
     */
    public Worker(Field startField) {
        this.field = startField;
        this.field.assignWorker(this);
    }

    /**
     * @return The field where the worker is currently positioned.
     */
    public Field getField() {
        return this.field;
    }

    /**
     * Moves the worker to a new field.
     * @param to The destination field.
     */
    public void moveTo(Field to) {
        this.field.removeWorker();
        this.field = to;
        this.field.assignWorker(this);
    }

    /**
     * Builds a tower at a specified field.
     * @param at The field where the tower is built.
     */
    public void buildAt(Field at) {
        at.buildTower();
    }

    /**
     * Checks if the worker is standing on a winning field.
     * @param winningHeight The height that determines victory.
     * @return True if the worker is on a winning field, otherwise false.
     */
    public boolean isOnWinningField(int winningHeight) {
        return this.field.isWinningField(winningHeight);
    }
}
