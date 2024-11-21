package agh.ics.oop.model;

import java.util.function.Function;

public class Animal {
    private MapDirection direction;
    private Vector2d position;

    public Animal(){
        this(new Vector2d(2,2));
    }

    public Animal(Vector2d position){
        this.direction = MapDirection.NORTH;
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    @Override
    public String toString() {

        return switch (direction) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, RectangularMap map){
        Vector2d potentialNewPosition;

        switch (direction){
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case FORWARD:
                potentialNewPosition = this.position.add(this.direction.toUnitVector());
                if (map.canMoveTo(potentialNewPosition)) {
                    this.position = potentialNewPosition;
                }
                break;
            case BACKWARD:
                potentialNewPosition = this.position.subtract(this.direction.toUnitVector());
                if (map.canMoveTo(potentialNewPosition)) {
                    this.position= potentialNewPosition;
                }
                break;
            default:
                break;
        }
    }
}
