package com.santorini.board;

import java.util.List;
import java.util.ArrayList;

/**
 * The Grid class represents the game board in Santorini.
 * It consists of a 5x5 grid of fields and provides methods for querying positions and adjacent fields.
 */
public class Grid {

    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private final List<Field> fields;

    /**
     * Initializes a 5x5 grid of fields, each assigned a unique position.
     */
    public Grid() {
        this.fields = new ArrayList<>();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.fields.add(new Field(new Position(x, y)));
            }
        }
    }

    /**
     * Checks if a given position is valid within the grid.
     * @param position The position to validate.
     * @return True if the position exists in the grid, otherwise false.
     */
    public boolean isValidPosition(Position position) {
        for (Field field : fields) {
            if (position.equals(field.getPosition())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the field at a specified position.
     * @param position The position of the desired field.
     * @return The field at the given position.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public Field getFieldAt(Position position) {
        for (Field field : fields) {
            if (position.equals(field.getPosition())) {
                return field;
            }
        }
        throw new IllegalArgumentException("No field has this illegal position!");
    }

    /**
     * Retrieves a list of adjacent fields to a given field.
     * @param field The reference field.
     * @return A list of fields that are adjacent to the given field.
     */
    public List<Field> getAdjacentFields(Field field) {
        Position position = field.getPosition();

        List<Field> adjacentFields = new ArrayList<>();

        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {

                if (dx == 0 && dy == 0) {
                    continue;
                } 

                Position adjacentPosition = new Position(position.getX() + dx, position.getY() + dy);
                if (isValidPosition(adjacentPosition)) {
                    adjacentFields.add(getFieldAt(adjacentPosition));
                }
            }
        }

        return adjacentFields;
    }
}