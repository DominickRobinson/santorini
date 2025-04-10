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

        game.trySpawn(tile1);
        game.trySpawn(tile3);
    }

    @Test
    public void testSpawnWorker() {
        Game game = new Game();
        Player p1 = game.getCurrentPlayer();
        Tile spawn1 = game.getBoard().getTileAt(new Position(0, 0));
    
        assertTrue(game.trySpawn(spawn1));
        assertEquals(1, p1.getWorkers().size(), "Player should have one worker after spawning.");
    }

    @Test
    public void testWorkerCannotMoveToOccupiedTile() {
        Tile t1 = game.getBoard().getTileAt(new Position(0, 0));
        Tile t2 = game.getBoard().getTileAt(new Position(0, 1));
    
        game.trySpawn(t1);
        game.nextTurn();
        game.trySpawn(t2);
    
        Worker worker1 = game.getTileAt(new Position(0, 0)).getWorker();
        Worker worker2 = game.getTileAt(new Position(0, 1)).getWorker();
    
        assertFalse(game.isValidMove(worker1, t2), "Cannot move to occupied tile");
        assertFalse(game.isValidMove(worker2, t1), "Cannot move to occupied tile");
    
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

    // @Test
    // public void testMoveAndWinCondition() {
    //     Tile base = game.getBoard().getTileAt(new Position(0, 0));
    //     Tile mid = game.getBoard().getTileAt(new Position(0, 1));
    //     game.trySpawn(base);
    //     Worker w = base.getWorker();
    
    //     mid.buildTower();
    //     mid.buildTower();
    //     mid.buildTower();
    
    //     assertTrue(game.isValidMove(w, mid), "Should be able to move to level 3");
    //     player1.moveTo(w, mid);
    
    //     assertTrue(game.checkWinCondition(w), "Worker should win on level 3");
    
    // }
    
}
