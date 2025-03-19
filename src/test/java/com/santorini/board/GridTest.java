package com.santorini.board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

// CHECKSTYLE:OFF MagicNumber
class GridTest {

    @Test
    public void testValidPosition() {
        Grid grid = new Grid();
        assertTrue(grid.isValidPosition(new Position(2, 2)), "should be valid.");
        assertFalse(grid.isValidPosition(new Position(-1, 2)), "should be invalid.");
        assertFalse(grid.isValidPosition(new Position(5, 5)), "should be invalid.");
    }

    @Test
    public void testGetFieldAtValidPosition() {
        Grid grid = new Grid();
        Position position = new Position(3, 3);
        Field field = grid.getFieldAt(position);
        assertNotNull(field, "Field at (3,3) should exist.");
        assertEquals(position, field.getPosition(), "should have the correct position.");
    }

    @Test
    public void testGetFieldAtInvalidPositionThrowsException() {
        Grid grid = new Grid();
        Position invalidPosition = new Position(5, 6);
        assertThrows(IllegalArgumentException.class, () -> grid.getFieldAt(invalidPosition), "Should throw exception for invalid position.");
    }

    @Test
    public void testGetAdjacentFields() {
        Grid grid = new Grid();
        Field centerField = grid.getFieldAt(new Position(2, 2));
        List<Field> adjacentFields = grid.getAdjacentFields(centerField);
        
        assertEquals(8, adjacentFields.size(), "A field in the middle should have 8 adjacent fields.");
        
        Field edgeField = grid.getFieldAt(new Position(0, 0));
        List<Field> edgeAdjacentFields = grid.getAdjacentFields(edgeField);
        
        assertEquals(3, edgeAdjacentFields.size(), "A field at the corner should have 3 adjacent fields.");
    }
}
