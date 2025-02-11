package com.santorini.game;

import com.santorini.board.Field;
import com.santorini.board.Grid;
import com.santorini.board.Position;
import com.santorini.board.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private Field field1;
    private Field field2;
    private Field field3;

    @BeforeEach
    public void setUp() {
        game = new Game();
        player1 = game.getCurrentPlayer();
        game.nextTurn();
        player2 = game.getCurrentPlayer();
        game.nextTurn();

        field1 = game.getGrid().getFieldAt(new Position(0, 0));
        field2 = game.getGrid().getFieldAt(new Position(0, 1));
        field3 = game.getGrid().getFieldAt(new Position(1, 1));

        game.spawnWorker(player1, field1);
        game.spawnWorker(player2, field3);
    }

    @Test
    public void testSpawnWorker() {
        assertEquals(1, player1.getWorkers().size(), "Player should have one worker after spawning.");
        assertEquals(1, player2.getWorkers().size(), "Second player should also have a worker.");
        assertTrue(field1.isOccupied(), "Player 1's worker should be on field1.");
        assertTrue(field3.isOccupied(), "Player 2's worker should be on field3.");
    }

    @Test
    public void testWorkerCannotMoveToOccupiedField() {
        Worker worker1 = player1.getWorkers().get(0);
        Worker worker2 = player2.getWorkers().get(0);

        assertFalse(game.isValidMove(worker1, field3), "Player 1's worker should not be able to move to Player 2's occupied field.");
        assertFalse(game.isValidMove(worker1, field1), "Worker cannot stay in same position.");
        assertFalse(game.isValidMove(worker2, field1), "Player 2's worker should not be able to move to Player 1's occupied field.");
    }

    @Test
    public void testWorkerBuild() {
        Worker worker = player1.getWorkers().get(0);
        player1.build(worker, field2);

        assertEquals(1, field2.getTower().getLevels(), "Building should increase tower level.");
        assertFalse(game.getValidBuildFields(field1).contains(field1), "Cannot build on occupied field.");
    }

    @Test
    public void testTurnSwitch() {
        Player previousPlayer = game.getCurrentPlayer();
        game.nextTurn();
        Player newPlayer = game.getCurrentPlayer();

        assertNotEquals(previousPlayer, newPlayer, "Should switch to the next player.");

        game.nextTurn();
        assertEquals(previousPlayer, game.getCurrentPlayer(), "Should go back to first player.");
    }

    @Test
    public void testMoveAndWinCondition() {
        Worker worker = player1.getWorkers().get(0);
        Tower tower1 = field1.getTower();
        Tower tower2 = field2.getTower();
    
        tower2.build();
        tower2.build();

        assertFalse(game.getValidMoveFields(field1).contains(field2), "Should not be able to move up two levels.");

        player1.move(worker, field2);

        assertTrue(game.getValidMoveFields(field2).contains(field1), "Should be able to move down two levels.");

        player1.move(worker, field1);

        assertFalse(game.checkWinCondition(worker), "Worker should not win when moving onto a level 2 tower.");

        tower2.build();

        assertFalse(game.getValidMoveFields(field1).contains(field2), "Should not be able to move up three levels.");
        
        player1.move(worker, field2);

        System.out.println("\nAfter move:");
        System.out.println("Worker's New Field Tower Height: " + worker.getField().getTower().getLevels());

        assertTrue(game.checkWinCondition(worker), "Worker should win when moving onto a level 3 tower.");
        game.endGame(player1);
        assertTrue(game.isGameWon(), "Game should be won.");
        assertEquals(player1, game.getWinner(), "Player 1 should be the winner.");
    }
    
}
