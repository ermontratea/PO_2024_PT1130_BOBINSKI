package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void animalOrientation() {
        List<Vector2d> positions = List.of(new Vector2d(2, 2));
        List<MoveDirection> directions = List.of(
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT
        );
        Simulation simulation = new Simulation(positions, directions);

        simulation.run();

        Animal animal = simulation.getAnimals().get(0);
        assertEquals(MapDirection.NORTH, animal.getDirection(),
                "Animal should face NORTH");
    }

    @Test
    void animalMove() {

        List<Vector2d> positions = List.of(new Vector2d(2, 2));
        List<MoveDirection> directions = List.of(
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.BACKWARD
        );
        Simulation simulation = new Simulation(positions, directions);

        simulation.run();

        Animal animal = simulation.getAnimals().get(0);
        assertEquals(new Vector2d(3, 3), animal.getPosition(),
                "Animal should end at position (3,3)");
    }

    @Test
    void animaWithinBounds() {
        List<Vector2d> positions = List.of(new Vector2d(0, 0));
        List<MoveDirection> directions = List.of(
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT
        );
        Simulation simulation = new Simulation(positions, directions);

        simulation.run();

        Animal animal = simulation.getAnimals().get(0);
        assertTrue(animal.isAt(new Vector2d(0, 0)),
                "Animal should stay within map bounds");
    }

    @Test
    void parse() {
        String[] input = {"f", "b", "r", "l", "x"};

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> OptionsParser.parse(input),
                "Parsing should throw an exception for invalid input"
        );
        assertEquals("x is not legal", exception.getMessage());
    }

    @Test
    void multipleAnimals() {
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(0, 0));
        List<MoveDirection> directions = List.of(
                MoveDirection.FORWARD, MoveDirection.RIGHT,
                MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.LEFT, MoveDirection.LEFT
        );
        Simulation simulation = new Simulation(positions, directions);

        simulation.run();

        Animal animal1 = simulation.getAnimals().get(0);
        Animal animal2 = simulation.getAnimals().get(1);

        assertEquals(new Vector2d(2, 4), animal1.getPosition(),
                "Animal 1 should end at (2,4)");
        assertEquals(new Vector2d(1, 0), animal2.getPosition(),
                "Animal 2 should end at (1,0)");
    }
}
