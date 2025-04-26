package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PrometheusCardTest {

    private Game game;
    private Player player;
    private Tile spawnTile;
    private Tile secondSpawnTile;
    private Tile moveTileHigher;
    private Tile moveTileSameLevel;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = game.getCurrentPlayer();
        player.setGodCard(new PrometheusCard());

        spawnTile = game.getTileAt(new Position(1, 1));
        secondSpawnTile = game.getTileAt(new Position(0, 0));
        moveTileHigher = game.getTileAt(new Position(1, 2));
        moveTileSameLevel = game.getTileAt(new Position(2, 1));

        moveTileHigher.buildTower();

        game.handleTilePress(spawnTile);
        game.handleTilePress(secondSpawnTile);

        game.handleTilePress(game.getTileAt(new Position(4, 4)));
        game.handleTilePress(game.getTileAt(new Position(3, 3)));
    }

    @Test
    void testCannotMoveUpAfterBuilding() {
        Tile buildTile = game.getTileAt(new Position(0, 1));
    
        game.handleTilePress(spawnTile);
        game.handleTilePress(buildTile);
    
        assertFalse(game.getCurrentAction().getValidTiles(game).contains(moveTileHigher),
            "Prometheus cannot move up after building first.");
        assertTrue(game.getCurrentAction().getValidTiles(game).contains(moveTileSameLevel),
            "Prometheus can move on same level or lower.");
    }
    
}
