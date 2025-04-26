package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ArtemisCardTest {

    private Game game;
    private Player player;
    private Tile spawnTile1;
    private Tile spawnTile2;
    private Tile firstMoveTile;
    private Tile secondMoveTile;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = game.getCurrentPlayer();
        player.setGodCard(new ArtemisCard());

        spawnTile1 = game.getTileAt(new Position(1, 1));
        spawnTile2 = game.getTileAt(new Position(0, 0));
        firstMoveTile = game.getTileAt(new Position(1, 2));
        secondMoveTile = game.getTileAt(new Position(2, 2));

        game.handleTilePress(spawnTile1);
        game.handleTilePress(spawnTile2);

        game.handleTilePress(game.getTileAt(new Position(4, 4)));
        game.handleTilePress(game.getTileAt(new Position(3, 3)));
    }

    @Test
    void testSecondMoveNotBackToFirstTile() {
        game.handleTilePress(spawnTile1);
        game.handleTilePress(firstMoveTile);

        assertFalse(game.getCurrentAction().getValidTiles(game).contains(spawnTile1),
            "Artemis should not be able to move back to original tile.");
        assertTrue(game.getCurrentAction().getValidTiles(game).contains(secondMoveTile),
            "Artemis should be able to move to a different tile.");
    }
}
