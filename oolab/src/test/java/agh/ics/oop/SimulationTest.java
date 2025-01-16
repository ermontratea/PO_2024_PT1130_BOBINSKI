//package agh.ics.oop;
//
//import agh.ics.oop.model.*;
//import agh.ics.oop.model.exceptions.IncorrectPositionException;
//import agh.ics.oop.model.trash.MapDirection;
//import agh.ics.oop.model.trash.MoveDirection;
//import agh.ics.oop.model.trash.RectangularMap;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SimulationTest {
//    @Test
//    void placeAnimalsOnMap() throws IncorrectPositionException {
//
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal1 = new Animal(new Vector2d(2, 2));
//        Animal animal2 = new Animal(new Vector2d(3, 3));
//        try{
//            map.place(animal1);
//            map.place(animal2);
//        }
//        catch(IncorrectPositionException e){
//            System.out.println(e.getMessage());
//        }
//        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)), "Animal 1 should be at (2, 2)");
//        assertEquals(animal2, map.objectAt(new Vector2d(3, 3)), "Animal 2 should be at (3, 3)");
//    }
//
//    @Test
//    void preventPlacingAnimalsOnSamePosition() throws IncorrectPositionException {
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal1 = new Animal(new Vector2d(2, 2));
//        map.place(animal1);
//
//        Animal animal2 = new Animal(new Vector2d(2, 2));
//
//        Exception exception = assertThrows(
//                IncorrectPositionException.class,
//                () -> map.place(animal2),
//                "Placing an animal on an occupied position should throw an exception."
//        );
//
//        assertEquals("Position (2, 2) is not correct.", exception.getMessage());
//        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)), "Only Animal 1 should be at (2, 2)");
//    }
//
//
//    @Test
//    void animalMovement() throws IncorrectPositionException {
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal = new Animal(new Vector2d(2, 2));
//        map.place(animal);
//
//        map.move(animal, MoveDirection.FORWARD);
//        map.move(animal, MoveDirection.RIGHT);
//        map.move(animal, MoveDirection.FORWARD);
//
//        assertEquals(new Vector2d(3, 3), animal.getPosition(), "Animal should move to (3, 3)");
//        assertEquals(MapDirection.EAST, animal.getDirection(), "Animal should face EAST");
//    }
//
//    @Test
//    void preventMovementOutsideMap() throws IncorrectPositionException {
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal = new Animal(new Vector2d(2, 4)); // Pozycja przy górnej krawędzi
//        map.place(animal);
//
//        map.move(animal, MoveDirection.FORWARD);
//        map.move(animal, MoveDirection.RIGHT);
//        map.move(animal, MoveDirection.FORWARD);
//
//        assertEquals(new Vector2d(3, 4), animal.getPosition(), "Animal should not move outside the map");
//        assertEquals(MapDirection.EAST, animal.getDirection(), "Animal should face EAST");
//    }
//
//    @Test
//    void multipleAnimalsMovement() throws IncorrectPositionException {
//
//        RectangularMap map = new RectangularMap(5, 5);
//        Animal animal1 = new Animal(new Vector2d(2, 2));
//        Animal animal2 = new Animal(new Vector2d(4, 4));
//        map.place(animal1);
//        map.place(animal2);
//
//
//        map.move(animal1, MoveDirection.FORWARD);
//        map.move(animal2, MoveDirection.FORWARD);
//
//
//        assertEquals(new Vector2d(2, 3), animal1.getPosition(), "Animal 1 should move to (2, 3)");
//        assertEquals(new Vector2d(4, 4), animal2.getPosition(), "Animal 2 should move to (5, 4)");
//    }
//
//    @Test
//    void simulationRun() {
//
//        RectangularMap map = new RectangularMap(5, 5);
//        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 3));
//        List<MoveDirection> directions = List.of(
//                MoveDirection.FORWARD, MoveDirection.RIGHT,
//                MoveDirection.FORWARD, MoveDirection.LEFT
//        );
//        Simulation simulation = new Simulation(positions, directions, map);
//
//        simulation.run();
//
//        List<Animal> animals = simulation.getAnimals();
//        assertEquals(new Vector2d(2, 4), animals.get(0).getPosition(), "First animal should end at (2, 4)");
//        assertEquals(new Vector2d(3, 3), animals.get(1).getPosition(), "Second animal should not move");
//    }
//}
