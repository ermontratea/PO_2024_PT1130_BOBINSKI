package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.IncorrectPositionException;
import javafx.geometry.BoundingBox;

import java.util.Collection;


public class RectangularMap extends AbstractWorldMap{
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Boundary mapBoundary;


    public RectangularMap(String id, int height, int width) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width-1, height-1);
        this.mapBoundary = new Boundary(lowerLeft, upperRight);
    }
    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if (!canMoveTo(animal.getPosition())) {
            throw new IncorrectPositionException(animal.getPosition());
        }
        animals.put(animal.getPosition(), animal);
        synchronized (ConsoleMapDisplay.class) {
            notifyObservers("Placed animal at position " + animal.getPosition());
        }

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight) && !isOccupied(position);
    }
    @Override
    public Collection<WorldElement> getElements() {
        return super.getElements();
    }
    @Override
    public Boundary getCurrentBounds(){
        return mapBoundary;
    };


}