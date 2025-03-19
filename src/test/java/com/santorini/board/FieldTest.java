package com.santorini.board;

import com.santorini.game.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

// CHECKSTYLE:OFF MagicNumber
class FieldTest {

    @Test
    public void testFieldInitialization() {
        Position position = new Position(1, 1);
        Field field = new Field(position);

        assertEquals(position, field.getPosition(), "Field should store the correct position.");
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
        Position position1 = new Position(3, 3);
        Field field1 = new Field(position1);
        new Worker(field1);
    
        Position position2 = new Position(4, 4);
        Field field2 = new Field(position2);
        Worker worker2 = new Worker(field2);
    
        assertThrows(IllegalStateException.class, 
            () -> field1.assignWorker(worker2), 
            "Should not be able to assign a worker to an occupied field.");
    }
    
    
    @Test
    public void testRemoveWorker() {
        Position position = new Position(3, 3);
        Field field = new Field(position);
        new Worker(field);

        assertTrue(field.isOccupied(), "Field should be occupied after assigning a worker.");
        field.removeWorker();
        assertNull(field.getWorker(), "Worker should be removed from the field.");
        assertFalse(field.isOccupied(), "Field should not be occupied after removing the worker.");
    }

}
