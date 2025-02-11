package com.santorini.game;

import com.santorini.board.Field;
import com.santorini.board.Position;
import com.santorini.board.Tower;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    @Test
    public void testWorkerInitialization() {
        Field startField = new Field(new Position(2, 2));
        Worker worker = new Worker(startField);

        assertEquals(startField, worker.getField(), "Should be placed at the start field.");
        assertTrue(startField.isOccupied(), "Start field should be occupied after worker is placed.");
    }

    @Test
    public void testWorkerMoveToNewField() {
        Field startField = new Field(new Position(2, 2));
        Field newField = new Field(new Position(3, 3));
        Worker worker = new Worker(startField);

        assertEquals(newField, worker.getField(), "Worker should have moved to the new field.");
        assertTrue(startField.isOccupied(), "Start field should be occupied.");
        assertFalse(newField.isOccupied(), "New field should not be occupied.");

        worker.moveTo(newField);

        assertEquals(newField, worker.getField(), "Worker should have moved to the new field.");
        assertFalse(startField.isOccupied(), "Start field should no longer be occupied.");
        assertTrue(newField.isOccupied(), "New field should now be occupied.");
    }

    @Test
    public void testWorkerBuildAtField() {
        Field field = new Field(new Position(2, 2));
        Worker worker = new Worker(field);
        Tower tower = field.getTower();

        int initialLevel = tower.getLevels();
        worker.buildAt(field);
        
        assertEquals(initialLevel + 1, tower.getLevels(), "Building should increase the tower level by 1.");
    }

    @Test
    public void testWorkerCannotMoveToOccupiedField() {
        Field field1 = new Field(new Position(1, 1));
        Field field2 = new Field(new Position(2, 2));

        Worker worker1 = new Worker(field1);
        Worker worker2 = new Worker(field2);

        assertThrows(IllegalStateException.class, () -> worker1.moveTo(field2), 
            "Worker should not be able to move to an occupied field.");
    }
}
