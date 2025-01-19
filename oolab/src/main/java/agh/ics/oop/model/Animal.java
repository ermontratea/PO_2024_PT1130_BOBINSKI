package agh.ics.oop.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Animal implements WorldElement{
    ///na pewno tutaj przechowywać randoma?
    Random random = new Random();
    private int direction;
    private Vector2d position;
    private int age = 0;
    private int[] genes;
    private List<Animal> children = new ArrayList<>();
        private int energy;
    private int currentGene;
    ///te rzeczy też lepiej przechowywać gdzie indziej
    private int energyToBreed;
    private int energyToBirth;

    public void addEnergy(int energy) {
        this.energy += energy;
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
    public List<Animal> getChildren() {
        return this.children;
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
        potentialNewPosition = this.getPosition().add(geneToMapDirection(this.direction).toUnitVector());
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
                {if(this.children.size()>animal.getChildren().size())
                    {return true;}
                else if (this.children.size()==animal.getChildren().size())
                    {return random.nextBoolean();}
                else return false;}
            else return false;
        }else return false;
    }

    public MapDirection geneToMapDirection(int direction) {
        return switch(direction){
            case 0 -> MapDirection.NORTH;
            case 1 -> MapDirection.NORTH_EAST;
            case 2 -> MapDirection.EAST;
            case 3 -> MapDirection.SOUTH_EAST;
            case 4 -> MapDirection.SOUTH;
            case 5 -> MapDirection.SOUTH_WEST;
            case 6 -> MapDirection.WEST;
            case 7 -> MapDirection.NORTH_WEST;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }
}