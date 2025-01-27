package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {
    @Test
    void equals() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(1, 2);
        Vector2d vector3 = new Vector2d(2, 3);

        assertEquals(vector1, vector2);
        assertNotEquals(vector1, vector3);
    }

    @Test
    void testToString() {
        Vector2d vector1 = new Vector2d(3, 7);

        String result = vector1.toString();
        assertEquals("(3, 7)", result);
    }

    @Test
    void precedes() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);

        boolean result1 = vector1.precedes(vector2);
        boolean result2 = vector2.precedes(vector1);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    void follows() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);

        boolean result1 = vector1.follows(vector2);
        boolean result2 = vector2.follows(vector1);

        assertFalse(result1);
        assertTrue(result2);
    }

    @Test
    void add() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);

        Vector2d result = vector1.add(vector2);

        assertEquals(new Vector2d(4, 6), result);
        assertNotEquals(new Vector2d(3, 6), result);
    }

    @Test
    void subtract() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);

        Vector2d result = vector2.subtract(vector1);

        assertEquals(new Vector2d(2, 2), result);
        assertNotEquals(new Vector2d(4, 2), result);
    }

    @Test
    void upperRight() {
        Vector2d vector1 = new Vector2d(5, 2);
        Vector2d vector2 = new Vector2d(3, 4);

        Vector2d result = vector1.upperRight(vector2);

        assertEquals(new Vector2d(5, 4), result);
        assertNotEquals(new Vector2d(3, 4), result);
    }

    @Test
    void lowerLeft() {
        Vector2d vector1 = new Vector2d(5, 2);
        Vector2d vector2 = new Vector2d(3, 4);

        Vector2d result = vector1.lowerLeft(vector2);

        assertEquals(new Vector2d(3, 2), result);
        assertNotEquals(new Vector2d(3, 4), result);
    }

    @Test
    void opposite() {
        Vector2d vector1 = new Vector2d(1, 2);

        Vector2d result = vector1.opposite();

        assertEquals(new Vector2d(-1, -2), result);
    }

    @Test
    void getX() {
        Vector2d vector1 = new Vector2d(3, 8);

        int result = vector1.getX();

        assertEquals(3, result);
    }

    @Test
    void getY() {
        Vector2d vector1 = new Vector2d(2, 4);

        int result = vector1.getY();

        assertEquals(4, result);
    }
}