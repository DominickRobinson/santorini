package com.santorini.board;

import com.santorini.game.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

// CHECKSTYLE:OFF MagicNumber
class TileTest {

    @Test
    public void testTileInitialization() {
        Position position = new Position(1, 1);
        Tile tile = new Tile(position);

        assertEquals(position, tile.getPosition(), "Tile should store the correct position.");
        assertNull(tile.getWorker(), "Tile should start with no worker.");
        assertFalse(tile.isOccupied(), "A new tile should not be occupied.");
    }

    @Test
    public void testAssignWorker() {
        Position position = new Position(2, 2);
        Tile tile = new Tile(position);
        Worker worker = new Worker(tile);

        assertEquals(worker, tile.getWorker(), "Tile should store the assigned worker.");
        assertTrue(tile.isOccupied(), "Tile should be occupied after assigning a worker.");
    }
    
    @Test
    public void testAssignWorkerToOccupiedTileThrowsException() {
        Position position1 = new Position(3, 3);
        Tile tile1 = new Tile(position1);
        new Worker(tile1);
    
        Position position2 = new Position(4, 4);
        Tile tile2 = new Tile(position2);
        Worker worker2 = new Worker(tile2);
    
        assertThrows(IllegalStateException.class, 
            () -> tile1.assignWorker(worker2), 
            "Should not be able to assign a worker to an occupied tile.");
    }
    
    
    @Test
    public void testRemoveWorker() {
        Position position = new Position(3, 3);
        Tile tile = new Tile(position);
        new Worker(tile);

        assertTrue(tile.isOccupied(), "Tile should be occupied after assigning a worker.");
        tile.removeWorker();
        assertNull(tile.getWorker(), "Worker should be removed from the tile.");
        assertFalse(tile.isOccupied(), "Tile should not be occupied after removing the worker.");
    }

}
