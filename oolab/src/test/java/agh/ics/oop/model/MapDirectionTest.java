package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void toUnitVectorCorrect() {
        MapDirection direction = MapDirection.NORTH;

        Vector2d result = direction.toUnitVector();

        assertEquals(new Vector2d(0, 1), result, "NORTH should return (0, 1)");
    }
    @Test
    void toUnitVectorIncorrect() {
        MapDirection direction = MapDirection.SOUTH_EAST;

        Vector2d result = direction.toUnitVector();

        assertNotEquals(new Vector2d(0, 1), result, "SOUTH_EAST should return (1, -1)");
    }
}