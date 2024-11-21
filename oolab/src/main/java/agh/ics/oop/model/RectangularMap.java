package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.Collection;

public class RectangularMap extends AbstractWorldMap{
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final MapVisualizer visualizer;


    public RectangularMap(int height, int width) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width-1, height-1);
        this.visualizer = new MapVisualizer(this);
    }

    @Override
    public boolean place(Animal animal) {
        return super.place(animal);
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        super.move(animal,direction);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return super.objectAt(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight) && !isOccupied(position);
    }
    public String toString() {
        return visualizer.draw(lowerLeft, upperRight);
    }
    @Override
    public Collection<WorldElement> getElements() {
        return super.getElements();
    }

}