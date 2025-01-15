package agh.ics.oop.model;
import java.util.Random;


public class Animal implements WorldElement{
    Random random = new Random();
    private int direction;
    private Vector2d position;
    private int children = 0;
    private int age = 0;
    private int[] genes;
    private int energy;
    private int currentGene;
    private int energyToBreed;
    private int energyToBirth;


    public Animal(Vector2d position, int geneLength, int startingEnergy, int energyToBreed, int energyToBirth){
        this.energyToBreed = energyToBreed;
        this.energyToBirth = energyToBirth;
        this.direction = random.nextInt(8);
        this.position = position;
        genes = new int[geneLength];
        for (int i = 0; i < geneLength; i++) {
            genes[i] = random.nextInt(8);
        }
        energy = startingEnergy;
        currentGene = random.nextInt(geneLength);
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public int getDirection() {
        return this.direction;
    }

//    @Override
//    public String toString() {
//
//        return switch (direction) {
//            case NORTH -> "N";
//            case EAST -> "E";
//            case SOUTH -> "S";
//            case WEST -> "W";
//        };
//    }

//    public boolean isAt(Vector2d position){
//        return this.position.equals(position);
//    }

    public void move(int direction, WorldMap map) {
        if (energy == 0) {
            // map.die(this);
        }
        Vector2d potentialNewPosition;
        this.direction = (this.direction + direction) % 8;
        potentialNewPosition = this.getPosition().add(directionToVector(this.direction));
        if (map.canMoveTo(potentialNewPosition)) {
            this.position = potentialNewPosition;
        }
        energy--;
    }

    public Vector2d directionToVector(int direction) {
        return new Vector2d(0,0);
    }
}