//package agh.ics.oop.model;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class Vector2dTest {
//    @Test
//    void equals(){
//        Vector2d vector1 = new Vector2d(1, 2);
//        Vector2d vector2 = new Vector2d(1, 2);
//        Vector2d vector3 = new Vector2d(2, 3);
//
//        assertEquals(vector1, vector2);
//        assertNotEquals(vector1, vector3);
//    }
//   @Test
//    void testToString(){
//        Vector2d vector1 = new Vector2d(3, 7);
//        assertEquals("(3, 7)", vector1.toString());
//   }
//   @Test
//   void precedes(){
//        Vector2d vector1 = new Vector2d(1, 2);
//        Vector2d vector2 = new Vector2d(3, 4);
//
//        assertTrue(vector1.precedes(vector2));
//        assertFalse(vector2.precedes(vector1));
//   }
//   @Test
//    void follows(){
//        Vector2d vector1 = new Vector2d(1, 2);
//       Vector2d vector2 = new Vector2d(3, 4);
//
//       assertFalse(vector1.follows(vector2));
//       assertTrue(vector2.follows(vector1));
//   }
//   @Test
//    void add(){
//        Vector2d vector1 = new Vector2d(1, 2);
//        Vector2d vector2 = new Vector2d(3, 4);
//
//        assertEquals(new Vector2d(4,6), vector1.add(vector2));
//        assertNotEquals(new Vector2d(3,6), vector1.add(vector2));
//   }
//    @Test
//    void subtract(){
//        Vector2d vector1 = new Vector2d(1, 2);
//        Vector2d vector2 = new Vector2d(3, 4);
//
//        assertEquals(new Vector2d(2,2), vector2.subtract(vector1));
//        assertNotEquals(new Vector2d(4,2), vector2.subtract(vector1));
//    }
//    @Test
//    void upperRight(){
//        Vector2d vector1 = new Vector2d(5, 2);
//        Vector2d vector2 = new Vector2d(3, 4);
//
//        assertEquals(new Vector2d(5,4), vector1.upperRight(vector2));
//        assertNotEquals(new Vector2d(3,4), vector1.upperRight(vector2));
//    }
//    @Test
//    void lowerLeft(){
//        Vector2d vector1 = new Vector2d(5, 2);
//        Vector2d vector2 = new Vector2d(3, 4);
//
//        assertEquals(new Vector2d(3,2), vector1.lowerLeft(vector2));
//        assertNotEquals(new Vector2d(3,4), vector1.lowerLeft(vector2));
//    }
//    @Test
//    void opposite(){
//        Vector2d vector1 = new Vector2d(1, 2);
//
//        assertEquals(new Vector2d(-1,-2), vector1.opposite());
//    }
//}