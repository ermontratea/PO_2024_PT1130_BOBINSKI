package agh.ics.oop.model;

import agh.ics.oop.trash.MoveDirection;

import java.util.*;

public class Earth implements WorldMap {
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final List<Animal> animals = new ArrayList<Animal>;
    protected final List<MapChangeListener> observers = new ArrayList<>();

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public void place(Animal animal) {
        animals.add(animal);
        notifyObservers("Placed animal at position " + animal.getPosition());
    }

    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(direction, this);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            animals.remove(oldPosition);
            animals.put(newPosition, animal);
            notifyObservers("Moved an animal to position " + newPosition);


        }
    }

}
