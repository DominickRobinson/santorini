package com.santorini.game;

import com.santorini.board.Field;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private List<Worker> workers;

    public Player(int id) {
        this.id = id;
        this.workers = new ArrayList<>();
    }

    public int getID() {
        return this.id;
    }

    public void spawnWorker(Field startField) {
        Worker worker = new Worker(startField);
        workers.add(worker);
    }
    
    public List<Worker> getWorkers() {
        return this.workers;
    }

    public List<Field> getOccupiedFields() {
        List<Field> occupiedFields = new ArrayList<>();
        for (Worker worker : workers) {
            occupiedFields.add(worker.getField());
        }
        return occupiedFields;
    }

    public Worker chooseWorker(int index) {
        if (index < 0 || index >= workers.size()) {
            throw new IllegalArgumentException("Invalid worker index!");
        }
        return workers.get(index);
    }

    public void move(Worker worker, Field to) {
        if (!this.workers.contains(worker)) {
            throw new IllegalArgumentException("Player cannot move a worker that is not their own!");
        }
        worker.moveTo(to);
    }

    public void build(Worker worker, Field at) {
        if (!this.workers.contains(worker)) {
            throw new IllegalArgumentException("Player cannot build with a worker that is not their own!");
        }
        worker.buildAt(at);
    }
    
    public int getTotalWorkers() {
        return workers.size();
    }

}