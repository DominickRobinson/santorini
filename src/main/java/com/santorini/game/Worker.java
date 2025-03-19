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

    public void moveTo(Field newField) {
        this.field.removeWorker();
        this.field = newField;
        this.field.assignWorker(this);
    }

    public void buildAt(Field field) {
        field.buildTower();
    }

    public boolean isOnWinningField(int winningHeight) {
        return this.field.isWinningField(winningHeight);
    }
}