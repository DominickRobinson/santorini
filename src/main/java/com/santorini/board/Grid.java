package com.santorini.board;

import java.util.List;
import java.util.ArrayList;


public class Grid {

    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private final List<Field> fields;

    public Grid() {
        this.fields = new ArrayList<>();

        for (int x = 0; x < this.WIDTH; x++) {
            for (int y = 0; y < this.HEIGHT; y++) {
                this.fields.add(new Field(new Position(x, y)));
            }
        }
    }

    public boolean isValidPosition(Position position) {
        for (Field field : fields) {
            if (position.equals(field.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public Field getFieldAt(Position position) {
        for (Field field : fields) {
            if (position.equals(field.getPosition())) {
                return field;
            }
        }
        throw new IllegalArgumentException("No field has this illegal position!");
    }

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