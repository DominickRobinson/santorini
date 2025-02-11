package com.santorini.board;

import com.santorini.game.Worker;

public class Field {

    private Position position;
    private Tower tower;
    private Worker worker;

    public Field (Position position) {
        this.position = position;
        this.tower = new Tower();
    }

    public boolean isOccupied() {
        return this.worker != null;
    }

    public Position getPosition() {
        return this.position;
    }

    public Tower getTower() {
        return this.tower;
    }

    public Worker getWorker() {
        return this.worker;
    }

    public void assignWorker(Worker worker) {
        if (this.isOccupied()) {
            throw new IllegalStateException("Cannot place a worker on an occupied field!");
        }
        
        this.worker = worker;
    }
    
    public void removeWorker() {
        this.worker = null;
    }

}