package com.santorini.game;

import com.santorini.board.Tile;
import com.santorini.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    private Player player;
    private Tile tile1;
    private Tile tile2;

    @BeforeEach
    public void setUp() {
        player = new Player(1);
        tile1 = new Tile(new Position(0, 0));
        tile2 = new Tile(new Position(1, 1));
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals(1, player.getID(), "Player ID should be initialized correctly.");
        assertTrue(player.getWorkers().isEmpty(), "Player should start without workers.");
    }

    @Test
    public void testSpawnWorker() {
        player.spawn(tile1);
        assertEquals(1, player.getWorkers().size(), "Player should have one worker after spawning.");
        assertTrue(tile1.isOccupied(), "Tile should be occupied after spawning a worker.");
    }

    @Test
    public void testMoveWorkerValid() {
        player.spawn(tile1);
        Worker worker = player.getWorkers().get(0);

        player.moveTo(worker, tile2);

        assertEquals(tile2, worker.getTile(), "Worker should be moved to the new tile.");
        assertFalse(tile1.isOccupied(), "Old tile should no longer be occupied.");
        assertTrue(tile2.isOccupied(), "New tile should now be occupied.");
    }

    @Test
    public void testMoveWorkerNotOwned() {
        Player otherPlayer = new Player(2);
        otherPlayer.spawn(tile1);
        Worker worker = otherPlayer.getWorkers().get(0);

        assertThrows(IllegalArgumentException.class, () -> player.moveTo(worker, tile2), 
            "Should not allow a player to move another player's worker.");
    }

    @Test
    public void testBuildValid() {
        player.spawn(tile1);
        Worker worker = player.getWorkers().get(0);

        int initialLevel = tile2.getTowerHeight();
        player.buildAt(worker, tile2);

        assertEquals(initialLevel + 1, tile2.getTowerHeight(), "Building should increase the tower level by 1.");
    }
}
