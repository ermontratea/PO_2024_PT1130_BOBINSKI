package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @Test
    void animalInitialization() {
        Vector2d position = new Vector2d(2, 3);
        int geneLength = 8;
        int startingEnergy = 50;
        int energyToBreed = 20;
        int energyToBirth = 10;

        Animal animal = new Animal(position, geneLength, startingEnergy, energyToBreed, energyToBirth);

        assertEquals(position, animal.getPosition());
        assertEquals(startingEnergy, animal.getEnergy());
        assertEquals(0, animal.getAge());
        assertEquals(0, animal.getChildren().size());

    }
    @Test
    void addEnergy() {
        Vector2d position = new Vector2d(0, 0);
        Animal animal = new Animal(position, 8, 10, 5, 3);

        animal.addEnergy(5);

        assertEquals(15, animal.getEnergy());
    }
    @Test
    void animalMoveReducesEnergy() {
        Vector2d position = new Vector2d(2, 3);
        int geneLength = 8;
        int startingEnergy = 50;
        int energyToBreed = 20;
        int energyToBirth = 10;
        Earth earth = new Earth(10, 10, 0, 0, geneLength, startingEnergy, energyToBreed, energyToBirth, 5, false);
        Animal animal = new Animal(position, geneLength, startingEnergy, energyToBreed, energyToBirth);

        animal.move(earth);

        assertEquals(startingEnergy - 1, animal.getEnergy());
    }
    @Test
    void animalDiesWhenEnergyZero() {
        Vector2d position = new Vector2d(2, 3);
        int geneLength = 8;
        int startingEnergy = 0;
        int energyToBreed = 20;
        int energyToBirth = 10;
        Earth earth = new Earth(10, 10, 0, 0, geneLength, startingEnergy, energyToBreed, energyToBirth, 5, false);
        Animal animal = new Animal(position, geneLength, startingEnergy, energyToBreed, energyToBirth);

        Vector2d result = animal.move(earth);

        assertEquals(Animal.DEATH_VECTOR, result);
        assertEquals(0, animal.getEnergy());
    }
    @Test
    void animalCanMoveOnLastEnergy() {
        Vector2d position = new Vector2d(2, 3);
        int geneLength = 8;
        int startingEnergy = 1;
        int energyToBreed = 20;
        int energyToBirth = 10;
        Earth earth = new Earth(10, 10, 0, 0, geneLength, startingEnergy, energyToBreed, energyToBirth, 5, false);
        Animal animal = new Animal(position, geneLength, startingEnergy, energyToBreed, energyToBirth);

        Vector2d result = animal.move(earth);

        assertNotEquals(Animal.DEATH_VECTOR, result);
        assertEquals(0, animal.getEnergy());
    }
    @Test
    void getPosition() {
        Vector2d position = new Vector2d(2, 3);
        Animal animal = new Animal(position, 5, 10, 5, 4);

        Vector2d result = animal.getPosition();

        assertEquals(position, result);
    }
    @Test
    void getEnergy() {
        Vector2d position = new Vector2d(2, 3);
        int startingEnergy = 50;
        Animal animal = new Animal(position, 5, startingEnergy, 5, 4);

        int result = animal.getEnergy();

        assertEquals(startingEnergy, result);
    }
    ///napisać jak napiszamy rozmnażanie, wtedy ilość dzieci się zmienia (testowanie dzieci)
    @Test
    void getChildren() {
        Vector2d position = new Vector2d(2, 3);
        Animal animal = new Animal(position, 5, 50, 5, 4);

        int result = animal.getChildren().size();

        assertEquals(0, result);
    }

    @Test
    void animalMovementUpdatesPosition() {
        Vector2d position = new Vector2d(2, 3);
        Earth earth = new Earth(10, 10, 0, 0, 8, 50, 20, 10, 5, false);
        Animal animal = new Animal(position, 8, 50, 20, 10);

        Vector2d newPosition = animal.move(earth);

        assertNotEquals(position, newPosition);
    }
    ///fajne by były testy, które testują wchodzenie w bieguny i przechodzenie na drugi bok mapy, ale nie da się zbytnio tego zrobić
    /// gdy początkowy kierunek zwierzaka jest losowy, trzeba by było zrobić specjalny setter/nowy konstruktor dla tych testów

    @Test
    void isBetterAnimalEnergyComparison() {
        Vector2d position = new Vector2d(2, 3);
        Animal animal1 = new Animal(position, 8, 50, 20, 10);
        Animal animal2 = new Animal(position, 8, 30, 20, 10);

        boolean result = animal1.isBetterAnimal(animal2);

        assertTrue(result);
    }
    @Test
    void isBetterAnimalEqualEnergyComparesAge() {
        Vector2d position = new Vector2d(2, 3);
        Animal animal1 = new Animal(position, 8, 51, 20, 10);
        animal1.move(new Earth(10, 10, 0, 0, 8, 50, 20, 10, 5, false));
        Animal animal2 = new Animal(position, 8, 50, 20, 10);

        boolean result = animal1.isBetterAnimal(animal2);

        assertTrue(result);
    }
    ///trzeba dodać rozmnażanie żeby działało
    @Test
    void isBetterAnimalEqualEnergyEqualAgeComparesChildren() {
        Vector2d position = new Vector2d(2, 3);
        Animal animal1 = new Animal(position, 8, 50, 20, 10);
        Animal animal2 = new Animal(position, 8, 50, 20, 10);

        boolean result = animal1.isBetterAnimal(animal2);

        assertTrue(result);
    }
    @Test
    void geneToMapDirection() {
        Animal animal = new Animal(new Vector2d(2, 3), 8, 50, 20, 10);

        assertEquals(MapDirection.NORTH, animal.geneToMapDirection(0));
        assertEquals(MapDirection.NORTH_EAST, animal.geneToMapDirection(1));
        assertEquals(MapDirection.EAST, animal.geneToMapDirection(2));
        assertEquals(MapDirection.SOUTH_EAST, animal.geneToMapDirection(3));
        assertEquals(MapDirection.SOUTH, animal.geneToMapDirection(4));
        assertEquals(MapDirection.SOUTH_WEST, animal.geneToMapDirection(5));
        assertEquals(MapDirection.WEST, animal.geneToMapDirection(6));
        assertEquals(MapDirection.NORTH_WEST, animal.geneToMapDirection(7));
    }
    @Test
    void currentGeneChanges() {
        Vector2d position = new Vector2d(3, 5);
        int geneLength = 6;
        int startingEnergy = 70;
        int energyToBreed = 30;
        int energyToBirth = 5;
        Earth earth = new Earth(10, 10, 0, 0, geneLength, startingEnergy, energyToBreed, energyToBirth, 5, false);
        Animal animal = new Animal(position, geneLength, startingEnergy, energyToBreed, energyToBirth);
        int startingGene = animal.getCurrentGene();

        animal.move(earth);

        assertNotEquals(startingGene, animal.getCurrentGene());
    }
    @Test
    void currentGeneCycles() {
        Vector2d position = new Vector2d(2, 3);
        int geneLength = 8;
        int startingEnergy = 50;
        int energyToBreed = 20;
        int energyToBirth = 10;
        Earth earth = new Earth(10, 10, 0, 0, geneLength, startingEnergy, energyToBreed, energyToBirth, 5, false);
        Animal animal = new Animal(position, geneLength, startingEnergy, energyToBreed, energyToBirth);
        int startingGene = animal.getCurrentGene();

        for (int i = 0; i < geneLength; i++) {
            animal.move(earth);
        }

        assertEquals(startingGene, animal.getCurrentGene());
    }
}