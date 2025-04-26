package com.santorini.board;

import java.util.List;
import java.util.ArrayList;

/**
 * The Board class represents the game board in Santorini.
 * It consists of a 5x5 board of tiles and provides methods for querying positions and adjacent tiles.
 */
public class Board {

    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private final List<Tile> tiles;

    /**
     * Initializes a 5x5 board of tiles, each assigned a unique position.
     */
    public Board() {
        this.tiles = new ArrayList<>();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.tiles.add(new Tile(new Position(x, y)));
            }
        }
    }

    /**
     * @return The width of the board.
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * @return The height of the board.
    */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Checks if a given position is valid within the board.
     * @param position The position to validate.
     * @return True if the position exists in the board, otherwise false.
     */
    public boolean isValidPosition(Position position) {
        for (Tile tile : tiles) {
            if (position.equals(tile.getPosition())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the tile at a specified position.
     * @param position The position of the desired tile.
     * @return The tile at the given position.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public Tile getTileAt(Position position) {
        for (Tile tile : tiles) {
            if (position.equals(tile.getPosition())) {
                return tile;
            }
        }
        throw new IllegalArgumentException("No tile has this illegal position!");
    }

    /**
     * Retrieves a list of adjacent tiles to a given tile.
     * @param tile The reference tile.
     * @return A list of tiles that are adjacent to the given tile.
     */
    public List<Tile> getAdjacentTiles(Tile tile) {
        Position position = tile.getPosition();

        List<Tile> adjacentTiles = new ArrayList<>();

        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {

                if (dx == 0 && dy == 0) {
                    continue;
                }

                Position adjacentPosition = new Position(position.getX() + dx, position.getY() + dy);
                if (isValidPosition(adjacentPosition)) {
                    adjacentTiles.add(getTileAt(adjacentPosition));
                }
            }
        }

        return adjacentTiles;
    }
    
    /**
     * Checks whether two tiles are adjacent.
     * @param a One of the tiles.
     * @param b One of the tiles.
     * @return True if a and b are adjacent, otherwise false.
     */
    public boolean areAdjacent(Tile a, Tile b) {
        return getAdjacentTiles(a).contains(b);
    }
    
}