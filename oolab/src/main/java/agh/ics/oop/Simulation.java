package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.exceptions.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Simulation implements Runnable {
    private final Earth map;
    private final List<Animal> animals;

    public Simulation(int width, int height, int plantAmount, int energyFromPlant, int plantPerDay, boolean deadBody, int animalAmount, int energyToBreed, int energyToBirth, int minMutation, int maxMutation, boolean swap, int genLength) {
        Boundary boundary = new Boundary(new Vector2d(0,0),new Vector2d(width-1,height-1));
        this.map = new Earth(boundary, plantPerDay);
        this.animals = new ArrayList<>();

    }

    @Override
    public void run() {

    }
}