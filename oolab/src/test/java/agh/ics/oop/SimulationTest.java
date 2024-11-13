package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    @Test
    void placeAnimalsOnMap() {

        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(3, 3));


        boolean placed1 = map.place(animal1);
        boolean placed2 = map.place(animal2);


        assertTrue(placed1, "Animal 1 placed correctly" );
        assertTrue(placed2, "Animal 2 placed correctly");
        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)), "Animal 1 should be at (2, 2)");
        assertEquals(animal2, map.objectAt(new Vector2d(3, 3)), "Animal 2 should be at (3, 3)");
    }

    @Test
    void preventPlacingAnimalsOnSamePosition() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 2));

        boolean placed1 = map.place(animal1);
        boolean placed2 = false;
        try {
            map.place(animal2);
        } catch (IllegalArgumentException e) {
            placed2 = false;
        }

        assertTrue(placed1, "Animal 1 placed correctly" );
        assertFalse(placed2, "Animal 2 should not be placed at the same position");
        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)), "Only Animal 1 should be at (2, 2)");
    }

    @Test
    void animalMovement() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));
        map.place(animal);

        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.RIGHT);
        map.move(animal, MoveDirection.FORWARD);

        assertEquals(new Vector2d(3, 3), animal.getPosition(), "Animal should move to (3, 3)");
        assertEquals(MapDirection.EAST, animal.getDirection(), "Animal should face EAST");
    }

    @Test
    void preventMovementOutsideMap() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 5)); // Pozycja przy górnej krawędzi
        map.place(animal);

        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.RIGHT);
        map.move(animal, MoveDirection.FORWARD);

        assertEquals(new Vector2d(3, 5), animal.getPosition(), "Animal should not move outside the map");
        assertEquals(MapDirection.EAST, animal.getDirection(), "Animal should face EAST");
    }

    @Test
    void multipleAnimalsMovement() {

        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(4, 4));
        map.place(animal1);
        map.place(animal2);


        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal2, MoveDirection.FORWARD);


        assertEquals(new Vector2d(2, 3), animal1.getPosition(), "Animal 1 should move to (2, 3)");
        assertEquals(new Vector2d(5, 4), animal2.getPosition(), "Animal 2 should move to (5, 4)");
    }

    @Test
    void simulationRun() {

        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 3));
        List<MoveDirection> directions = List.of(
                MoveDirection.FORWARD, MoveDirection.RIGHT,
                MoveDirection.FORWARD, MoveDirection.LEFT
        );
        Simulation simulation = new Simulation(positions, directions, map);

        simulation.run();

        List<Animal> animals = simulation.getAnimals();
        assertEquals(new Vector2d(2, 4), animals.get(0).getPosition(), "First animal should end at (2, 4)");
        assertEquals(new Vector2d(3, 3), animals.get(1).getPosition(), "Second animal should not move");
    }
}

