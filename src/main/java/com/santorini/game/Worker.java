package com.santorini.game;

import com.santorini.board.Field;

public class Worker {
    private Field field;

    public Worker (Field startField) {
        this.field = startField;
        this.field.assignWorker(this);
    }

    public Field getField() {
        return this.field;
    }

    public void moveTo(Field to) {
        this.field.removeWorker();
        this.field = to;
        this.field.assignWorker(this);
    }

    public void buildAt(Field at) {
        at.buildTower();
    }

    public boolean isOnWinningField(int winningHeight) {
        return this.field.isWinningField(winningHeight);
    }
}