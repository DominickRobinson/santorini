package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import com.santorini.serialization.GameSerializer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PanCardTest {

    private Game game;
    private Player player;

    private Tile spawnTile1;
    private Tile spawnTile2;
    private Tile moveTile1;
    private Tile enemySpawn1;
    private Tile enemySpawn2;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = game.getCurrentPlayer();
        player.setGodCard(new PanCard());

        spawnTile1 = game.getTileAt(new Position(0, 0));
        spawnTile2 = game.getTileAt(new Position(1, 0));
        moveTile1 = game.getTileAt(new Position(0, 1));
        enemySpawn1 = game.getTileAt(new Position(2, 0));
        enemySpawn2 = game.getTileAt(new Position(3, 0));

        spawnTile1.buildTower();
        spawnTile1.buildTower();

        System.out.println("🚀 Spawning player 1 workers...");
        game.handleTilePress(spawnTile1);
        game.handleTilePress(spawnTile2);

        System.out.println("🚀 Spawning enemy workers...");
        game.handleTilePress(enemySpawn1);
        game.handleTilePress(enemySpawn2);
   
        System.out.println("🏗️ Prebuilt board:");
        System.out.println(GameSerializer.toJSON(game).toString(2));
    }

    @Test
    void testWinByMovingDownTwoLevels() {
        System.out.println("\n=== Turn 1 (Player 1) ===");
        System.out.println("🎯 Selecting worker at " + spawnTile1.getPosition());
        game.handleTilePress(spawnTile1); 

        System.out.println("🏗️ Selected worker:");
        System.out.println(GameSerializer.toJSON(game).toString(2));

        System.out.println("🚶 Moving to " + moveTile1.getPosition() + " (height " + moveTile1.getTowerHeight() + ")");
        game.handleTilePress(moveTile1);

        assertTrue(game.isGameWon(), "Pan should win by moving down two or more levels!");
    }
}
