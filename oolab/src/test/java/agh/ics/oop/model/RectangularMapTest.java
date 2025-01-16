//package agh.ics.oop.model;
//
//import agh.ics.oop.model.exceptions.IncorrectPositionException;
//import agh.ics.oop.model.trash.MoveDirection;
//import agh.ics.oop.model.trash.RectangularMap;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RectangularMapTest {
//
//    @Test
//    void placeAnimalOnFreePosition() throws IncorrectPositionException {
//
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal = new Animal(new Vector2d(2, 2));
//
//        map.place(animal);
//
//        assertEquals(animal, map.objectAt(new Vector2d(2, 2)));
//    }
//    @Test
//    void testPlaceAnimalOnOccupiedPosition() throws IncorrectPositionException {
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal1 = new Animal(new Vector2d(2, 2));
//        Animal animal2 = new Animal(new Vector2d(2, 2));
//        map.place(animal1);
//
//        Exception exception = assertThrows(
//                IncorrectPositionException.class,
//                () -> map.place(animal2),
//                "Placing an animal on an occupied position should throw an exception."
//        );
//        assertEquals("Position (2, 2) is not correct.", exception.getMessage());
//    }
//
//
//    @Test
//    void moveAnimalWithinBounds() throws IncorrectPositionException {
//
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal = new Animal(new Vector2d(2, 2));
//        map.place(animal);
//
//        map.move(animal, MoveDirection.FORWARD);
//        map.move(animal, MoveDirection.RIGHT);
//        map.move(animal, MoveDirection.FORWARD);
//
//        assertEquals(new Vector2d(3, 3), animal.getPosition(), "Animal should end up at position (3, 3).");
//    }
//
//    @Test
//    void testMoveAnimalOutOfBounds() throws IncorrectPositionException {
//
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal = new Animal(new Vector2d(4, 4));
//        map.place(animal);
//
//
//        map.move(animal, MoveDirection.FORWARD);
//        map.move(animal, MoveDirection.FORWARD);
//        assertEquals(new Vector2d(4, 4), animal.getPosition(), "Animal should not move out of bounds.");
//    }
//
//    @Test
//    void isOccupied() throws IncorrectPositionException {
//
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal = new Animal(new Vector2d(2, 2));
//        map.place(animal);
//
//
//        assertTrue(map.isOccupied(new Vector2d(2, 2)), "Position (2, 2) should be occupied.");
//        assertFalse(map.isOccupied(new Vector2d(1, 1)), "Position (1, 1) should not be occupied.");
//    }
//
//    @Test
//    void objectAt() throws IncorrectPositionException {
//
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal = new Animal(new Vector2d(3, 3));
//        map.place(animal);
//
//        WorldElement element = map.objectAt(new Vector2d(3, 3));
//
//        assertEquals(animal, element, "The object at position (3, 3) should be the placed animal.");
//    }
//}
