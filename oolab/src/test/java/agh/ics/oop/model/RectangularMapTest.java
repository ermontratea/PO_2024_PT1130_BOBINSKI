package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {

    @Test
    void testPlaceAnimalOnFreePosition() {

        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));


        boolean result = map.place(animal);


        assertTrue(result, "Animal should be placed successfully on an empty position.");
        assertEquals(animal, map.objectAt(new Vector2d(2, 2)));
    }

    @Test
    void testPlaceAnimalOnOccupiedPosition() {

        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 2));
        map.place(animal1);


        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> map.place(animal2),
                "Placing an animal on an occupied position should throw an exception."
        );
        assertEquals("Position (2, 2) is already occupied by an Animal.", exception.getMessage());
    }

    @Test
    void testMoveAnimalWithinBounds() {

        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));
        map.place(animal);

        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.RIGHT);
        map.move(animal, MoveDirection.FORWARD);

        assertEquals(new Vector2d(3, 3), animal.getPosition(), "Animal should end up at position (3, 3).");
    }

    @Test
    void testMoveAnimalOutOfBounds() {

        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(4, 4));
        map.place(animal);


        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 4), animal.getPosition(), "Animal should not move out of bounds.");
    }

    @Test
    void testIsOccupied() {

        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));
        map.place(animal);


        assertTrue(map.isOccupied(new Vector2d(2, 2)), "Position (2, 2) should be occupied.");
        assertFalse(map.isOccupied(new Vector2d(1, 1)), "Position (1, 1) should not be occupied.");
    }

    @Test
    void testObjectAt() {

        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(3, 3));
        map.place(animal);

        WorldElement element = map.objectAt(new Vector2d(3, 3));

        assertEquals(animal, element, "The object at position (3, 3) should be the placed animal.");
    }
}
