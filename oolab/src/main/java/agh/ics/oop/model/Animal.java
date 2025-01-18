package agh.ics.oop.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Animal implements WorldElement{
    private final static Vector2d NORTH_VECTOR = new Vector2d(0,1);
    private final static Vector2d EAST_VECTOR = new Vector2d(1,0);
    private final static Vector2d SOUTH_VECTOR = new Vector2d(0,-1);
    private final static Vector2d WEST_VECTOR = new Vector2d(-1,0);
    private final static Vector2d NORTH_EAST_VECTOR = new Vector2d(1,1);
    private final static Vector2d SOUTH_EAST_VECTOR = new Vector2d(1,-1);
    private final static Vector2d SOUTH_WEST_VECTOR = new Vector2d(-1,-1);
    private final static Vector2d NORTH_WEST_VECTOR = new Vector2d(-1,1);

    Random random = new Random();
    private int direction;
    private Vector2d position;
    private int age = 0;
    private int[] genes;
    private List<Animal> children = new ArrayList<>();
        private int energy;
    private int currentGene;
    private int energyToBreed;
    private int energyToBirth;

    public void addEnergy(int energy) {
        this.energy += energy;
    }
    public List<Animal> getKids() {
        return this.children;
    }

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
    public int getEnergy(){
        return this.energy;
    }
    public int getAge() {
        return age;
    }
    public int getChildren() {
        return children;
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

    public Vector2d move(Earth map) {
        if (energy == 0) {
            map.die(this);
        }
        int direction = genes[currentGene];
        Vector2d potentialNewPosition;
        this.direction = (this.direction + direction) % 8;
        potentialNewPosition = this.getPosition().add(directionToVector(this.direction));
        if (map.canMoveTo(potentialNewPosition)) {
            this.position = potentialNewPosition;
        }
        energy--;
        currentGene=(currentGene+1)%genes.length;
        return this.position;
    }
    public boolean isBetterAnimal(Animal animal) {
        if (this.energy > animal.getEnergy()) {
            return true;
        } else if (this.energy == animal.getEnergy()){
            if (this.age>animal.getAge())
                {return true;}
            else if (this.age==animal.getAge())
                {if(this.children>animal.getChildren())
                    {return true;}
                else if (this.children==animal.getChildren())
                    {return random.nextBoolean();}
                else return false;}
            else return false;
        }else return false;
    }

    public Vector2d directionToVector(int direction) {
        return switch(direction){
            case 0 -> NORTH_VECTOR;
            case 1 -> NORTH_EAST_VECTOR;
            case 2 -> EAST_VECTOR;
            case 3 -> SOUTH_EAST_VECTOR;
            case 4 -> SOUTH_VECTOR;
            case 5 -> SOUTH_WEST_VECTOR;
            case 6 -> WEST_VECTOR;
            case 7 -> NORTH_WEST_VECTOR;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }
}