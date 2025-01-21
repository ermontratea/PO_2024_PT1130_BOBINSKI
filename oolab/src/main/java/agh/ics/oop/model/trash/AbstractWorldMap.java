//package agh.ics.oop.model;
//
//import agh.ics.oop.model.exceptions.IncorrectPositionException;
//import agh.ics.oop.model.trash.MoveDirection;
//import agh.ics.oop.model.util.MapVisualizer;
//
//import java.util.*;
//
//public abstract class AbstractWorldMap implements WorldMap{
//    protected final Map<Vector2d, Animal> animals = new HashMap<>();
//    protected MapVisualizer visualizer = new MapVisualizer(this);
//    protected final List<MapChangeListener> observers = new ArrayList<>();
//
//
//    public void addObserver(MapChangeListener observer) {
//        observers.add(observer);
//    }
//
//    public void removeObserver(MapChangeListener observer) {
//        observers.remove(observer);
//    }
//
//    protected void notifyObservers(String message) {
//        for (MapChangeListener observer : observers) {
//            observer.mapChanged(this, message);
//        }
//    }
//    @Override
//    public void place(Animal animal) throws IncorrectPositionException {
//        if (!canMoveTo(animal.getPosition())) {
//            throw new IncorrectPositionException(animal.getPosition());
//        }
//        animals.put(animal.getPosition(), animal);
//        notifyObservers("Placed animal at position " + animal.getPosition());
//
//    }
//    @Override
//    public void move(Animal animal, MoveDirection direction) {
//        Vector2d oldPosition = animal.getPosition();
//        animal.move(direction, this);
//        Vector2d newPosition = animal.getPosition();
//
//        if (!oldPosition.equals(newPosition)) {
//            animals.remove(oldPosition);
//            animals.put(newPosition, animal);
//            notifyObservers("Moved an animal to position " + newPosition);
//
//
//        }
//    }
//    @Override
//    public boolean isOccupied(Vector2d position) {
//        return objectAt(position) != null;
//    }
//    @Override
//    public WorldElement objectAt(Vector2d position) {
//        return animals.get(position);
//    }
//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return !(objectAt(position) instanceof Animal);
//    }
//    @Override
//    public Collection<WorldElement> getElements() {
//        return new ArrayList<>(animals.values());
//    }
//    @Override
//    public abstract Boundary getCurrentBounds();
//
//    public String toString() {
//        Boundary mapBoundary = getCurrentBounds();
//        return visualizer.draw(mapBoundary.lowerLeft(), mapBoundary.upperRight());
//    }
//}
