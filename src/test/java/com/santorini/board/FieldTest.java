package com.santorini.board;

import com.santorini.game.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Test
    public void testFieldInitialization() {
        Position position = new Position(1, 1);
        Field field = new Field(position);

        assertEquals(position, field.getPosition(), "Field should store the correct position.");
        assertNotNull(field.getTower(), "Field should initialize with a new Tower.");
        assertNull(field.getWorker(), "Field should start with no worker.");
        assertFalse(field.isOccupied(), "A new field should not be occupied.");
    }

    @Test
    public void testAssignWorker() {
        Position position = new Position(2, 2);
        Field field = new Field(position);
        Worker worker = new Worker(field);

        assertEquals(worker, field.getWorker(), "Field should store the assigned worker.");
        assertTrue(field.isOccupied(), "Field should be occupied after assigning a worker.");
    }

    @Test
    public void testAssignWorkerToOccupiedFieldThrowsException() {
        Field field = new Field(new Position(3, 3));
        Worker worker1 = new Worker(field);
    
        Worker worker2 = new Worker(new Field(new Position(4, 4)));
    
        assertThrows(IllegalStateException.class, 
            () -> field.assignWorker(worker2), 
            "Should not be able to assign a worker to an occupied field.");
    }
    
    @Test
    public void testRemoveWorker() {
        Position position = new Position(4, 4);
        Field field = new Field(position);
        Worker worker = new Worker(field);

        assertTrue(field.isOccupied(), "Field should be occupied after assigning a worker.");
        field.removeWorker();
        assertNull(field.getWorker(), "Worker should be removed from the field.");
        assertFalse(field.isOccupied(), "Field should not be occupied after removing the worker.");
    }

}
