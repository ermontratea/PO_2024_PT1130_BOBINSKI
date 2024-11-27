package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap{
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected MapVisualizer visualizer;
    @Override
    public boolean place(Animal animal) {
        if (objectAt(animal.getPosition()) instanceof Animal) {
            throw new IllegalArgumentException("Position " + animal.getPosition() + " is already occupied by an Animal.");
        }
        animals.put(animal.getPosition(), animal);
        return true;
    }
    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(direction, this);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            animals.remove(oldPosition);
            animals.put(newPosition, animal);
        }
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }
    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }
    @Override
    public Collection<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

}
