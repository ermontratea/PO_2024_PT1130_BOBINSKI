package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.IncorrectPositionException;

import java.util.Collection;


public class RectangularMap extends AbstractWorldMap{
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;


    public RectangularMap(int height, int width) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width-1, height-1);
    }
    @Override
    public boolean place(Animal animal) throws IncorrectPositionException {
        if (!canMoveTo(animal.getPosition())) {
            throw new IncorrectPositionException(animal.getPosition());
        }
        animals.put(animal.getPosition(), animal);
        return true;
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
        return new Boundary(lowerLeft,upperRight);
    };


}