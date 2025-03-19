package com.santorini.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

// CHECKSTYLE:OFF MagicNumber
public class PositionTest {

    @Test
    public void testGetX() {
        Position pos = new Position(3, 4);
        assertEquals(3, pos.getX(), "getX() should return the correct x coordinate.");
    }

    @Test
    public void testGetY() {
        Position pos = new Position(3, 4);
        assertEquals(4, pos.getY(), "getY() should return the correct y coordinate.");
    }

    @Test
    public void testEquals() {
        Position pos1 = new Position(2, 5);
        Position pos2 = new Position(2, 5);
        Position pos3 = new Position(3, 5);

        assertEquals(pos1, pos2, "Positions with the same x and y should be equal.");
        assertNotEquals(pos1, pos3, "Positions with the same x and y should be equal.");
    }

}
