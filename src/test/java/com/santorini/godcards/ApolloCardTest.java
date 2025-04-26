package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import com.santorini.game.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class ApolloCardTest {

    private Game game;
    private Player player;
    private Player opponent;
    private Tile playerTile;
    private Tile playerTile2;
    private Tile enemyTile1;
    private Tile enemyTile2;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = game.getCurrentPlayer();
        player.setGodCard(new ApolloCard());

        playerTile = game.getTileAt(new Position(1, 1));
        playerTile2 = game.getTileAt(new Position(0, 0));
        enemyTile1 = game.getTileAt(new Position(1, 2));
        enemyTile2 = game.getTileAt(new Position(0, 1));

        game.handleTilePress(playerTile);
        game.handleTilePress(playerTile2);

        opponent = game.getCurrentPlayer();
        game.handleTilePress(enemyTile1);
        game.handleTilePress(enemyTile2);
    }

    @Test
    void testSwapWithOpponent() {
        Worker playerWorker = player.getWorkers().get(0);
        Worker opponentWorker = opponent.getWorkers().get(0);

        game.handleTilePress(playerTile); 
        game.handleTilePress(enemyTile1);

        assertSame(playerWorker, enemyTile1.getWorker(),
                "Player's worker should now be at enemy's previous tile.");
        assertSame(opponentWorker, playerTile.getWorker(),
                "Enemy's worker should now be at player's original tile.");
    }
}
