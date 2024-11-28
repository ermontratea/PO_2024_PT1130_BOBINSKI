package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

    @Test
    void testPlaceAnimalOnFreePosition() throws IncorrectPositionException {

        GrassField grassfield = new GrassField(10);
        Animal animal = new Animal(new Vector2d(5, 5));


        boolean result = grassfield.place(animal);

        assertTrue(result, "Animal should be placed successfully on a free position.");
        assertEquals(animal, grassfield.objectAt(new Vector2d(5, 5)));
    }

    @Test
    void testPlaceAnimalOnOccupiedPosition() throws IncorrectPositionException {

        GrassField grassfield = new GrassField(10);
        Animal animal1 = new Animal(new Vector2d(5, 5));
        Animal animal2 = new Animal(new Vector2d(5, 5));
        grassfield.place(animal1);


        Exception exception = assertThrows(IncorrectPositionException.class,
                () -> grassfield.place(animal2),
                "Placing an animal on an occupied position should throw an exception."
        );
        assertEquals("Position (5, 5) is not correct.", exception.getMessage());
    }



    @Test
    void testIsOccupied() throws IncorrectPositionException {

        GrassField grassfield = new GrassField(10);
        Animal animal = new Animal(new Vector2d(5, 5));
        grassfield.place(animal);


        assertTrue(grassfield.isOccupied(new Vector2d(5, 5)), "Position (5, 5) should be occupied by an animal.");
        assertFalse(grassfield.isOccupied(new Vector2d(1, 1)), "Position (1, 1) should not be occupied.");
    }

    @Test
    void testObjectAt() throws IncorrectPositionException {

        GrassField grassfield = new GrassField(10);
        Animal animal = new Animal(new Vector2d(5, 5));
        grassfield.place(animal);


        WorldElement element = grassfield.objectAt(new Vector2d(5, 5));


        assertEquals(animal, element, "The object at position (5, 5) should be the placed animal.");
    }
}
