package com.santorini.board;

/**
 * The Position class represents a coordinate on the Santorini game board.
 * It consists of an (x, y) coordinate and implements proper equality checks for comparison.
 */
public class Position {
    private static final int HASH1 = 17;
    private static final int HASH2 = 31;
    private static final int HASH3 = 33;

    private int x;
    private int y;

    /**
     * Initializes a position with the given x and y coordinates.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return The x-coordinate of the position.
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return The y-coordinate of the position.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Checks if two positions are equal based on their coordinates.
     * @param object The object to compare.
     * @return True if the object is a Position with the same x and y coordinates, otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Position) {
            Position position = (Position) object;
            return x == position.x && y == position.y;
        }
        return false;
    }

    /**
     * Generates a unique hash code for the position using predefined constants.
     * @return The hash code of this position.
     */
    @Override
    public int hashCode() {
        int result = HASH1;
        result = HASH2 * result + x;
        result = HASH3 * result + y;
        return result;
    }
}