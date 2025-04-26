package com.santorini.game;

import com.santorini.board.Tile;
import com.santorini.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class GameTest {

    private Game game;
    private Player player1;
    private Tile tile1;
    private Tile tile2;
    private Tile tile3;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = game.getCurrentPlayer();

        tile1 = game.getBoard().getTileAt(new Position(0, 0));
        tile2 = game.getBoard().getTileAt(new Position(0, 1));
        tile3 = game.getBoard().getTileAt(new Position(1, 1));

        game.trySpawn(tile1);
        game.trySpawn(tile3);
    }

    @Test
    void testSpawnWorker() {
        Game newGame = new Game();
        Player newPlayer = newGame.getCurrentPlayer();
        Tile spawnTile = newGame.getBoard().getTileAt(new Position(2, 2));

        assertTrue(newGame.trySpawn(spawnTile), "Worker should successfully spawn on empty tile.");
        assertEquals(1, newPlayer.getWorkers().size(), "Player should have one worker after spawning.");
    }

    @Test
    void testWorkerCannotMoveToOccupiedTile() {
        Tile spawnTile1 = tile1;
        Tile spawnTile3 = tile3;
    
        assertTrue(spawnTile1.isOccupied(), "Tile 0,0 should be occupied after spawn.");
        assertTrue(spawnTile3.isOccupied(), "Tile 1,1 should be occupied after spawn.");
    }
    

    @Test
    void testWorkerBuild() {
        Worker worker = player1.getWorkers().get(0);
        player1.buildAt(worker, tile2);

        assertEquals(1, tile2.getTowerHeight(), "Building should increase tower level.");
    }

    @Test
    void testTurnSwitch() {
        Player previousPlayer = game.getCurrentPlayer();
        game.nextTurn();
        Player newPlayer = game.getCurrentPlayer();

        assertNotEquals(previousPlayer, newPlayer, "Should switch to the next player after turn.");

        game.nextTurn();
        assertEquals(previousPlayer, game.getCurrentPlayer(), "Should cycle back to the first player.");
    }

    @Test
    void testMoveAndWinCondition() {
        Game winGame = new Game();
        Tile baseTile = winGame.getBoard().getTileAt(new Position(1, 1));
        Tile targetTile = winGame.getBoard().getTileAt(new Position(1, 2));
        winGame.trySpawn(baseTile);
        Worker worker = baseTile.getWorker();
        targetTile.buildTower();
        targetTile.buildTower();
        targetTile.buildTower();
        worker.moveTo(targetTile);

        assertTrue(winGame.checkWinCondition(worker), "Worker should win after moving to height 3.");
    }
}
