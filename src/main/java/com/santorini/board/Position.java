package com.santorini.board;

public class Position {
    private static final int HASH1 = 17;
    private static final int HASH2 = 31;
    private static final int HASH3 = 33;

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Position) {
            Position position = (Position) object;
            return x == position.x && y == position.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = HASH1;
        result = HASH2 * result + x;
        result = HASH3 * result + y;
        return result;
    }
}
