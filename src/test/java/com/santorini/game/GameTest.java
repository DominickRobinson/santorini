package com.santorini.game;

import com.santorini.board.Tile;
import com.santorini.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private Tile tile1;
    private Tile tile2;
    private Tile tile3;

    @BeforeEach
    public void setUp() {
        game = new Game();
        player1 = game.getCurrentPlayer();
        game.nextTurn();
        player2 = game.getCurrentPlayer();
        game.nextTurn();

        tile1 = game.getBoard().getTileAt(new Position(0, 0));
        tile2 = game.getBoard().getTileAt(new Position(0, 1));
        tile3 = game.getBoard().getTileAt(new Position(1, 1));

        game.spawnWorker(player1, tile1);
        game.spawnWorker(player2, tile3);
    }

    @Test
    public void testSpawnWorker() {
        assertEquals(1, player1.getWorkers().size(), "Player should have one worker after spawning.");
        assertEquals(1, player2.getWorkers().size(), "Second player should also have a worker.");
        assertTrue(tile1.isOccupied(), "Player 1's worker should be on tile1.");
        assertTrue(tile3.isOccupied(), "Player 2's worker should be on tile3.");
    }

    @Test
    public void testWorkerCannotMoveToOccupiedTile() {
        Worker worker1 = player1.getWorkers().get(0);
        Worker worker2 = player2.getWorkers().get(0);

        assertFalse(game.isValidMove(worker1, tile3), "Player 1's worker should not be able to move to Player 2's occupied tile.");
        assertFalse(game.isValidMove(worker1, tile1), "Worker cannot stay in same position.");
        assertFalse(game.isValidMove(worker2, tile1), "Player 2's worker should not be able to move to Player 1's occupied tile.");
    }

    @Test
    public void testWorkerBuild() {
        Worker worker = player1.getWorkers().get(0);
        player1.buildAt(worker, tile2);

        assertEquals(1, tile2.getTowerHeight(), "Building should increase tower level.");
        assertFalse(game.isValidBuild(worker, tile1), "Cannot build on an occupied tile.");
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
    
        tile2.buildTower();
        tile2.buildTower();

        assertFalse(game.isValidMove(worker, tile2), "Should not be able to move up two levels");

        player1.moveTo(worker, tile2);

        assertTrue(game.isValidMove(worker, tile1), "Should be able to move down two levels");

        player1.moveTo(worker, tile1);

        assertFalse(game.checkWinCondition(worker), "Worker should not win when moving onto a level 2 tower.");

        tile2.buildTower();

        assertFalse(game.isValidMove(worker, tile2), "Should not be able to move up three levels");
        
        player1.moveTo(worker, tile2);

        System.out.println("\nAfter move:");
        System.out.println("Worker's New Tile Tower Height: " + worker.getTile().getTowerHeight());

        assertTrue(game.checkWinCondition(worker), "Worker should win when moving onto a level 3 tower.");
        game.endGame(player1);
        assertTrue(game.isGameWon(), "Game should be won.");
        assertEquals(player1, game.getWinner(), "Player 1 should be the winner.");
    }
    
}
