package com.santorini.game;

import com.santorini.board.Field;
import com.santorini.board.Position;
import com.santorini.board.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Field field1;
    private Field field2;

    @BeforeEach
    public void setUp() {
        player = new Player(1);
        field1 = new Field(new Position(0, 0));
        field2 = new Field(new Position(1, 1));
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals(1, player.getID(), "Player ID should be initialized correctly.");
        assertTrue(player.getWorkers().isEmpty(), "Player should start without workers.");
    }

    @Test
    public void testSpawnWorker() {
        player.spawnWorker(field1);
        assertEquals(1, player.getWorkers().size(), "Player should have one worker after spawning.");
        assertTrue(field1.isOccupied(), "Field should be occupied after spawning a worker.");
    }

    @Test
    public void testMoveWorkerValid() {
        player.spawnWorker(field1);
        Worker worker = player.getWorkers().get(0);

        player.move(worker, field2);

        assertEquals(field2, worker.getField(), "Worker should be moved to the new field.");
        assertFalse(field1.isOccupied(), "Old field should no longer be occupied.");
        assertTrue(field2.isOccupied(), "New field should now be occupied.");
    }

    @Test
    public void testMoveWorkerNotOwned() {
        Player otherPlayer = new Player(2);
        otherPlayer.spawnWorker(field1);
        Worker worker = otherPlayer.getWorkers().get(0);

        assertThrows(IllegalArgumentException.class, () -> player.move(worker, field2), 
            "Should not allow a player to move another player's worker.");
    }

    @Test
    public void testBuildValid() {
        player.spawnWorker(field1);
        Worker worker = player.getWorkers().get(0);
        Tower tower = field2.getTower();

        int initialLevel = tower.getLevels();
        player.build(worker, field2);

        assertEquals(initialLevel + 1, tower.getLevels(), "Building should increase the tower level by 1.");
    }
}
