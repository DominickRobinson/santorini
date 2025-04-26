package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DemeterCardTest {

    private Game game;
    private Player player;
    private Tile workerTile;
    private Tile secondWorkerTile;
    private Tile moveTile;
    private Tile buildTile1;
    private Tile buildTile2;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = game.getCurrentPlayer();
        player.setGodCard(new DemeterCard());

        workerTile = game.getTileAt(new Position(1, 1));
        secondWorkerTile = game.getTileAt(new Position(0, 0));
        moveTile = game.getTileAt(new Position(1, 2));
        buildTile1 = game.getTileAt(new Position(2, 2));
        buildTile2 = game.getTileAt(new Position(1, 3));

        game.handleTilePress(workerTile);
        game.handleTilePress(secondWorkerTile);

        game.handleTilePress(game.getTileAt(new Position(4, 4)));
        game.handleTilePress(game.getTileAt(new Position(4, 3)));

    }

    @Test
    void testSecondBuildOnDifferentTile() {
        game.handleTilePress(workerTile);
        game.handleTilePress(moveTile);
        game.handleTilePress(buildTile1);

        assertFalse(game.getCurrentAction().getValidTiles(game).contains(buildTile1),
            "Should NOT allow second build on the same tile.");
        assertTrue(game.getCurrentAction().getValidTiles(game).contains(buildTile2),
            "Should allow second build on a different adjacent tile.");

        game.handleTilePress(buildTile2);

        assertEquals(1, buildTile1.getTowerHeight(), "First build tile should have 1 level.");
        assertEquals(1, buildTile2.getTowerHeight(), "Second build tile should have 1 level.");
    }
}
