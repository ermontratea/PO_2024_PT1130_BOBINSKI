package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.exceptions.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Simulation {
    private final WorldMap map;
    private final List<Animal> animals;
    private final List<MoveDirection> directions;

    public Simulation(List<Vector2d> positions, List<MoveDirection> directions, WorldMap map) {
        this.animals = new ArrayList<Animal>();

        for (Vector2d position : positions) {
            try {
                Animal animal = new Animal(position);
                if (map.place(animal)) {
                    this.animals.add(animal);
                }
            } catch (IncorrectPositionException e){
                System.err.println(e.getMessage());
            }
        }
        this.map = map;
        this.directions = directions;

    }


    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(this.animals);
    }
    public void run() {
        System.out.println(map);
        for(int i = 0; i < directions.size(); i++){
           map.move(animals.get(i % animals.size()), directions.get(i));

        }
   }
}