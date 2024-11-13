package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> directions;

    public Simulation(List<Vector2d> positions, List<MoveDirection> directions) {
        this.animals = new ArrayList<Animal>();
        for (Vector2d position : positions) {
            Animal animal = new Animal(position);
            this.animals.add(animal);
        }
        this.directions = directions;
    }


    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(this.animals);
    }
    public void run(){

        int numberOfAnimals = this.animals.size();
        for (int i = 0; i < directions.size(); i++) {
            Animal currentAnimal = animals.get(i % numberOfAnimals);
            MoveDirection currentDirection = directions.get(i);
            currentAnimal.move(currentDirection);


            System.out.println("Zwierzę " + (i % numberOfAnimals) + ": " + currentAnimal);
        }

    }
}
