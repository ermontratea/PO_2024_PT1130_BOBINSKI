package agh.ics.oop.model;


public class Animal {
    private Vector2d position;
    private MapDirection direction;

    public Vector2d getPosition() {
        return position;
    }
    public MapDirection getDirection() {
        return direction;
    }


    public Animal() {
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2,2);
    }
    public Animal(Vector2d position) {
        this.direction = MapDirection.NORTH;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Animal [direction=" + direction + ", position=" + position + "]";
    }
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }
    public boolean canMoveOnMap(Vector2d position) {
        if (position.getY() <= 4 && position.getX() <= 4 &&
                position.getY() >= 0 && position.getX() >= 0) {
            return true;
        }
        else return false;
    }
    public void move(MoveDirection direction){
        Vector2d potential_new_position;
        switch(direction) {
            case LEFT :  this.direction = this.direction.previous();
            break;
            case RIGHT : this.direction = this.direction.next();
            break;
            case FORWARD :
                potential_new_position = this.position.add(this.direction.toUnitVector());
                if (canMoveOnMap(potential_new_position)) {
                    this.position = potential_new_position;
                }
                break;

            case BACKWARD:
                potential_new_position = this.position.subtract(this.direction.toUnitVector());
                if (canMoveOnMap(potential_new_position)) {
                    this.position = potential_new_position;
                }
                break;
        };
    }
}
