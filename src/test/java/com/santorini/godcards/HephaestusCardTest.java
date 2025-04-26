package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HephaestusCardTest {

    private Game game;
    private Player player;
    private Tile workerTile;
    private Tile moveTile;
    private Tile buildTile;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = game.getCurrentPlayer();
        player.setGodCard(new HephaestusCard());

        workerTile = game.getTileAt(new Position(1, 1));
        moveTile = game.getTileAt(new Position(1, 2));
        buildTile = game.getTileAt(new Position(2, 2));

        game.handleTilePress(workerTile);
        game.handleTilePress(game.getTileAt(new Position(0, 0)));

        game.handleTilePress(game.getTileAt(new Position(4, 4)));
        game.handleTilePress(game.getTileAt(new Position(4, 3)));
    }

    @Test
    void testSecondBuildOnSameTile() {
        game.handleTilePress(workerTile);
        game.handleTilePress(moveTile);
        game.handleTilePress(buildTile);

        assertTrue(game.getCurrentAction().getValidTiles(game).contains(buildTile),
            "Should allow second build on same tile with Hephaestus.");

        game.handleTilePress(buildTile);

        assertEquals(2, buildTile.getTowerHeight(),
            "Tile should have two levels after Hephaestus builds twice.");
    }
}
