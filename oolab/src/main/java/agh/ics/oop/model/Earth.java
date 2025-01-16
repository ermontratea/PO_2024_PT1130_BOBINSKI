package agh.ics.oop.model;

import java.util.*;

public class Earth {
    private final Boundary boundary;
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final Map<Vector2d, Animal> eventGrass = new HashMap<>();
    private final Map<Vector2d, Animal> activeAnimals = new HashMap<>();
    private final Map<Vector2d, Animal> weakerActiveAnimals = new HashMap<>();
    private final List<Vector2d> graves = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();
    protected final List<MapChangeListener> observers = new ArrayList<>();

    public Earth(Boundary boundary) {
        this.boundary = boundary;
    }

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

    public void move(Animal animal) {
        Vector2d where = animal.move(this);
        if (grass.containsKey(where)) {
            if (eventGrass.containsKey(where)) {
                Animal opponent = eventGrass.get(where);
                if (animal.isBetterAnimal(opponent)) {
                    eventGrass.replace(where, animal);
                }
            } else {
                eventGrass.put(where, animal);
            }
        }
        if (activeAnimals.containsKey(where)) {
            Animal active = activeAnimals.get(where);
            if (weakerActiveAnimals.containsKey(where)) {
                Animal weaker = weakerActiveAnimals.get(where);
                if (animal.isBetterAnimal(weaker)) {
                    if (animal.isBetterAnimal(active)) {
                        weakerActiveAnimals.replace(where, active);
                        activeAnimals.replace(where, animal);
                    } else {
                        weakerActiveAnimals.replace(where, animal);
                    }
                }
            } else if (animal.isBetterAnimal(active)) {
                weakerActiveAnimals.put(where, active);
                activeAnimals.replace(where, animal);
            } else {
                weakerActiveAnimals.put(where, animal);
            }
        } else {
            activeAnimals.put(where, animal);
        }
    }

    public boolean canMoveTo(Vector2d place) {
        return  (place.follows(boundary.lowerLeft()) && place.precedes(boundary.upperRight()));
    }
}