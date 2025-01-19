package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EarthTest {
    @Test
    void constructorInitializesFertileAndUnfruitfulLand() {
        int width = 10;
        int height = 10;

        Earth earth = new Earth(width, height, 0, 0, 8, 10, 5, 3, 2, false);

        assertFalse(earth.getFertileLand().isEmpty());
        assertFalse(earth.getFertileLand().isEmpty());
        assertEquals(width*height, earth.getUnfruitfulLand().size()+earth.getFertileLand().size());
    }
    @Test
    void fillEarthWithPlantsAddsGrass() {
        Earth earth = new Earth(10, 10, 0, 0, 8, 10, 5, 3, 2, false);

        earth.fillEarthWithPlants(20);

        assertEquals(20, earth.getGrass().size());
    }

    @Test
    void moveAroundEarthWrapsHorizontally() {
        Earth earth = new Earth(10, 10, 0, 0, 8, 10, 5, 3, 2, false);

        Vector2d newPosition = earth.moveAroundEarth(new Vector2d(-1, 5));

        assertEquals(new Vector2d(9, 5), newPosition);
    }

    @Test
    void canMoveToValidPosition() {
        Earth earth = new Earth(10, 10, 0, 0, 8, 10, 5, 3, 2, false);
        Vector2d validPosition = new Vector2d(5, 5);

        boolean result = earth.canMoveTo(validPosition);

        assertTrue(result);
    }

    @Test
    void canMoveToInvalidPosition() {
        Earth earth = new Earth(10, 10, 0, 0, 8, 10, 5, 3, 2, false);
        Vector2d invalidPosition = new Vector2d(5, 11);

        boolean result = earth.canMoveTo(invalidPosition);

        assertFalse(result);
    }

    @Test
    void moveAnimalRemovesDeadAnimal() {
        Earth earth = new Earth(10, 10, 0, 1, 8, 0, 5, 3, 2, false);
        Animal animal = earth.getAnimals().iterator().next();

        earth.moveAllAnimals();

        assertTrue(earth.getAnimals().isEmpty());
        assertTrue(earth.getGraves().contains(animal.getPosition()));
    }

    @Test
    void dinnerFeedsAnimalAndRemovesGrass() {
        Earth earth = new Earth(5, 5, 25, 1, 8, 10, 5, 3, 2, false);
        Animal animal = earth.getAnimals().iterator().next();
        earth.move(animal);
        Vector2d grassPosition = animal.getPosition();

        earth.dinner();

        assertFalse(earth.getGrass().containsKey(grassPosition));
        assertTrue(animal.getEnergy() > 10);
    }

    @Test
    void clearListsRemovesAllTemporaryData() {
        Earth earth = new Earth(10, 10, 0, 1, 8, 10, 5, 3, 2, false);
        earth.moveAllAnimals();

        earth.clearLists();

        assertTrue(earth.getEventGrass().isEmpty());
        assertTrue(earth.getActiveAnimals().isEmpty());
    }


    @Test
    void moveAllAnimals() {
    }

    @Test
    void move() {
    }

    @Test
    void canMoveTo() {
    }

    @Test
    void addObserver() {
    }

    @Test
    void removeObserver() {
    }

    @Test
    void notifyObservers() {
    }

    @Test
    void fertileNearBodies() {
    }
}