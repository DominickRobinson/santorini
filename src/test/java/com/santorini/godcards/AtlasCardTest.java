package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AtlasCardTest {

    private Game game;
    private Player player;
    private Tile spawnTile1;
    private Tile spawnTile2;
    private Tile moveTile;
    private Tile buildTile;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = game.getCurrentPlayer();
        player.setGodCard(new AtlasCard());

        spawnTile1 = game.getTileAt(new Position(2, 2));
        spawnTile2 = game.getTileAt(new Position(2, 0));
        moveTile = game.getTileAt(new Position(2, 3)); 
        buildTile = game.getTileAt(new Position(2, 4));

        game.handleTilePress(spawnTile1);
        game.handleTilePress(spawnTile2);

        game.handleTilePress(game.getTileAt(new Position(4, 4)));
        game.handleTilePress(game.getTileAt(new Position(4, 3)));
    }

    @Test
    void testAtlasBuildDomeAtAnyHeight() {
        game.handleTilePress(spawnTile1);  
        game.handleTilePress(moveTile); 
        game.handleTilePress(buildTile);
        game.handleTilePress(buildTile);

        assertTrue(buildTile.hasDome(), "Tile should have a dome after Atlas builds.");
    }
}
