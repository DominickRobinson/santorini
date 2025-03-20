package com.santorini.game;

import com.santorini.board.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * The Player class represents a player in the Santorini game.
 * Each player has an ID and tracks its workers.
 */
public class Player {
    private int id;
    private List<Worker> workers;

    /**
     * Initializes a player with a given ID.
     * @param id The player's unique identifier.
     */
    public Player(int id) {
        this.id = id;
        this.workers = new ArrayList<>();
    }

    /**
     * @return The player's ID.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Spawns a worker at the specified field.
     * @param startField The starting field for the worker.
     */
    public void spawnWorker(Field startField) {
        Worker worker = new Worker(startField);
        workers.add(worker);
    }
    
    /**
     * @return The list of workers owned by the player.
     */
    public List<Worker> getWorkers() {
        return this.workers;
    }

    /**
     * @return The list of fields occupied by the player's workers.
     */
    public List<Field> getOccupiedFields() {
        List<Field> occupiedFields = new ArrayList<>();
        for (Worker worker : workers) {
            occupiedFields.add(worker.getField());
        }
        return occupiedFields;
    }

    /**
     * Chooses a worker by its index in the worker list.
     * @param index The index of the worker to select.
     * @return The selected worker.
     * @throws IllegalArgumentException if the index is invalid.
     */
    public Worker chooseWorker(int index) {
        if (index < 0 || index >= workers.size()) {
            throw new IllegalArgumentException("Invalid worker index!");
        }
        return workers.get(index);
    }

    /**
     * Moves a worker to a specified field.
     * @param worker The worker to move.
     * @param to The destination field.
     * @throws IllegalArgumentException if the worker is not owned by the player.
     */
    public void moveTo(Worker worker, Field to) {
        if (!ownsWorker(worker)) {
            throw new IllegalArgumentException("Player cannot move a worker that is not their own!");
        }
        worker.moveTo(to);
    }

    /**
     * Builds a tower at a specified field with a worker.
     * @param worker The worker building.
     * @param at The field where the tower is built.
     * @throws IllegalArgumentException if the worker is not owned by the player.
     */
    public void buildAt(Worker worker, Field at) {
        if (!ownsWorker(worker)) {
            throw new IllegalArgumentException("Player cannot build with a worker that is not their own!");
        }
        worker.buildAt(at);
    }
    
    /**
     * Checks if the player owns a given worker.
     * @param worker The worker to check.
     * @return True if the player owns the worker, otherwise false.
     */
    public boolean ownsWorker(Worker worker) {
        return workers.contains(worker);
    }
}
