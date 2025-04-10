package com.santorini.game;

import com.santorini.board.Tile;
import com.santorini.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// CHECKSTYLE:OFF MagicNumber
class WorkerTest {

    @Test
    public void testWorkerInitialization() {
        Tile startTile = new Tile(new Position(2, 2));
        Worker worker = new Worker(startTile);

        assertEquals(startTile, worker.getTile(), "Should be placed at the start tile.");
        assertTrue(startTile.isOccupied(), "Start tile should be occupied after worker is placed.");
    }

    @Test
    public void testWorkerMoveToNewTile() {
        Tile startTile = new Tile(new Position(2, 2));
        Tile newTile = new Tile(new Position(3, 3));
        Worker worker = new Worker(startTile);

        assertNotEquals(newTile, worker.getTile(), "Worker should not have moved to the new tile.");
        assertTrue(startTile.isOccupied(), "Start tile should be occupied.");
        assertFalse(newTile.isOccupied(), "New tile should not be occupied.");

        worker.moveTo(newTile);

        assertEquals(newTile, worker.getTile(), "Worker should have moved to the new tile.");
        assertFalse(startTile.isOccupied(), "Start tile should no longer be occupied.");
        assertTrue(newTile.isOccupied(), "New tile should now be occupied.");
    }

    @Test
    public void testWorkerBuildAtTile() {
        Tile tile = new Tile(new Position(2, 2));
        Worker worker = new Worker(tile);

        int initialLevel = tile.getTowerHeight();
        worker.buildAt(tile);
        
        assertEquals(initialLevel + 1, tile.getTowerHeight(), "Building should increase the tower level by 1.");
    }

    @Test
    public void testWorkerCannotMoveToOccupiedTile() {
        Tile tile1 = new Tile(new Position(1, 1));
        Tile tile2 = new Tile(new Position(2, 2));

        Worker worker1 = new Worker(tile1);
        new Worker(tile2);

        assertThrows(IllegalStateException.class, () -> worker1.moveTo(tile2), 
            "Worker should not be able to move to an occupied tile.");
    }
}
