package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;
    private final static Vector2d NORTH_VECTOR = new Vector2d(0,1);
    private final static Vector2d NORTH_EAST_VECTOR = new Vector2d(1,1);
    private final static Vector2d EAST_VECTOR = new Vector2d(1,0);
    private final static Vector2d SOUTH_EAST_VECTOR = new Vector2d(1,-1);
    private final static Vector2d SOUTH_VECTOR = new Vector2d(0,-1);
    private final static Vector2d SOUTH_WEST_VECTOR = new Vector2d(-1,-1);
    private final static Vector2d WEST_VECTOR = new Vector2d(-1,0);
    private final static Vector2d NORTH_WEST_VECTOR = new Vector2d(-1,1);
    public Vector2d toUnitVector(){
        return switch(this){
            case NORTH -> NORTH_VECTOR;
            case NORTH_EAST -> NORTH_EAST_VECTOR;
            case EAST -> EAST_VECTOR;
            case SOUTH_EAST -> SOUTH_EAST_VECTOR;
            case SOUTH -> SOUTH_VECTOR;
            case SOUTH_WEST -> SOUTH_WEST_VECTOR;
            case WEST -> WEST_VECTOR;
            case NORTH_WEST -> NORTH_WEST_VECTOR;
        };
    }
}
