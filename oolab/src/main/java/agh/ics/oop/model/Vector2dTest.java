package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    public void testEquals() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(1, 2);
        Vector2d vector3 = new Vector2d(2, 3);

        // Given-When-Then
        assertEquals(vector1, vector2);
        assertNotEquals(vector1, vector3);
    }

    @Test
    public void testToString() {
        Vector2d vector = new Vector2d(1, 2);
        assertEquals("(1,2)", vector.toString());
    }

    @Test
    public void testPrecedes() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);

        // Given-When-Then
        assertTrue(vector1.precedes(vector2));
        assertFalse(vector2.precedes(vector1));
    }

    @Test
    public void testFollows() {
        Vector2d vector1 = new Vector2d(3, 4);
        Vector2d vector2 = new Vector2d(1, 2);

        // Given-When-Then
        assertTrue(vector1.follows(vector2));
        assertFalse(vector2.follows(vector1));
    }

    @Test
    public void testUpperRight() {
        Vector2d vector1 = new Vector2d(1, 5);
        Vector2d vector2 = new Vector2d(4, 3);
        assertEquals(new Vector2d(4, 5), vector1.upperRight(vector2));
    }

    @Test
    public void testLowerLeft() {
        Vector2d vector1 = new Vector2d(1, 5);
        Vector2d vector2 = new Vector2d(4, 3);
        assertEquals(new Vector2d(1, 3), vector1.lowerLeft(vector2));
    }

    @Test
    public void testAdd() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);
        assertEquals(new Vector2d(4, 6), vector1.add(vector2));
    }

    @Test
    public void testSubtract() {
        Vector2d vector1 = new Vector2d(5, 7);
        Vector2d vector2 = new Vector2d(3, 4);
        assertEquals(new Vector2d(2, 3), vector1.subtract(vector2));
    }

    @Test
    public void testOpposite() {
        Vector2d vector = new Vector2d(3, -4);
        assertEquals(new Vector2d(-3, 4), vector.opposite());
    }
}
