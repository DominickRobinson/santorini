package com.santorini.board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

// CHECKSTYLE:OFF MagicNumber
class BoardTest {

    @Test
    public void testValidPosition() {
        Board board = new Board();
        assertTrue(board.isValidPosition(new Position(2, 2)), "should be valid.");
        assertFalse(board.isValidPosition(new Position(-1, 2)), "should be invalid.");
        assertFalse(board.isValidPosition(new Position(5, 5)), "should be invalid.");
    }

    @Test
    public void testGetTileAtValidPosition() {
        Board board = new Board();
        Position position = new Position(3, 3);
        Tile tile = board.getTileAt(position);
        assertNotNull(tile, "Tile at (3,3) should exist.");
        assertEquals(position, tile.getPosition(), "should have the correct position.");
    }

    @Test
    public void testGetTileAtInvalidPositionThrowsException() {
        Board board = new Board();
        Position invalidPosition = new Position(5, 6);
        assertThrows(IllegalArgumentException.class, () -> board.getTileAt(invalidPosition), "Should throw exception for invalid position.");
    }

    @Test
    public void testGetAdjacentTiles() {
        Board board = new Board();
        Tile centerTile = board.getTileAt(new Position(2, 2));
        List<Tile> adjacentTiles = board.getAdjacentTiles(centerTile);
        
        assertEquals(8, adjacentTiles.size(), "A tile in the middle should have 8 adjacent tiles.");
        
        Tile edgeTile = board.getTileAt(new Position(0, 0));
        List<Tile> edgeAdjacentTiles = board.getAdjacentTiles(edgeTile);
        
        assertEquals(3, edgeAdjacentTiles.size(), "A tile at the corner should have 3 adjacent tiles.");
    }
}
