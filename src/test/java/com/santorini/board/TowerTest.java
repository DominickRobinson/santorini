package com.santorini.board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TowerTest {

    @Test
    public void testBuildIncrementsLevel() {
        Tower tower = new Tower();
        assertEquals(0, tower.getLevels(), "A new tower should start at level 1");

        tower.build();
        tower.build();
        assertEquals(2, tower.getLevels(), "Building once should increase the level to 2");

        tower.build();
        tower.build();
        assertEquals(4, tower.getLevels(), "Building three times should increase the level to 4 (dome)");

        assertThrows(IllegalStateException.class, () -> tower.build(), "Building beyond level 4 should throw an exception.");
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
