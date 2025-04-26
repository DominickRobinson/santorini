package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MinotaurCardTest {

    private Game game;
    private Player player;
    private Tile playerTile;
    private Tile enemyTile;
    private Tile pushTile;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = game.getCurrentPlayer();
        player.setGodCard(new MinotaurCard());

        playerTile = game.getTileAt(new Position(1, 1));
        enemyTile = game.getTileAt(new Position(1, 2));
        pushTile = game.getTileAt(new Position(1, 3));

        game.handleTilePress(playerTile);
        game.handleTilePress(game.getTileAt(new Position(0, 0)));

        game.handleTilePress(enemyTile);
        game.handleTilePress(game.getTileAt(new Position(4, 4)));
    }

    @Test
    void testPush() {
        game.handleTilePress(playerTile);
        game.handleTilePress(enemyTile);

        assertNull(playerTile.getWorker(), "Original tile should be empty after move.");
        assertNotNull(pushTile.getWorker(), "Enemy should be pushed into correct tile.");
    }
}
