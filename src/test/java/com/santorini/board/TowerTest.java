package com.santorini.board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

// CHECKSTYLE:OFF MagicNumber
class TowerTest {

    @Test
    public void testBuildIncrementsLevel() {
        Tower tower = new Tower();
        assertEquals(0, tower.getHeight(), "A new tower should start at level 1");

        tower.build();
        tower.build();
        assertEquals(2, tower.getHeight(), "Building once should increase the level to 2");

        tower.build();
        assertEquals(3, tower.getHeight(), "Building three times should increase the level to 3 (dome)");
        tower.build();
        assertEquals(3, tower.getHeight(), "Building four times should add a dome, but keep the total levels at 3");

        assertThrows(IllegalStateException.class, () -> tower.build(), "Building beyond a dome should throw an exception.");
    }

    @Test
    public void testHasDome() {
        Tower tower = new Tower();
        assertFalse(tower.hasDome(), "A new tower should not have a dome.");

        tower.build();
        assertFalse(tower.hasDome(), "A tower at level 1 should not have a dome.");
        tower.build();
        tower.build();
        assertFalse(tower.hasDome(), "A tower at level 3 should not have a dome.");
        tower.build();
        assertTrue(tower.hasDome(), "A tower at level 4 should have a dome.");
    }

}
