package agh.ics.oop.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Animal implements WorldElement{
    public final static Vector2d DEATH_VECTOR = new Vector2d(-1, -1);
    Random random = new Random();
    private int direction;
    private Vector2d position;
    private int age = 0;
    private List<Animal> children = new ArrayList<>();
    private int[] genes;
    private int energy;
    private int currentGene;

    public void addEnergy(int energy) {
        this.energy += energy;
    }
    public int[] getGenes() {
        return genes;
    }
    public Animal(Vector2d position, int geneLength, int startingEnergy) {
        this.direction = random.nextInt(8);
        this.position = position;
        genes = new int[geneLength];
        for (int i = 0; i < geneLength; i++) {
            genes[i] = random.nextInt(8);
        }
        energy = startingEnergy;
        currentGene = random.nextInt(geneLength);
    }


    public Animal(Vector2d position, int geneLength, int startingEnergy, int[] genes) {
        this.direction = random.nextInt(8);
        this.position = position;
        this.genes = genes;
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
    public int getCurrentGene() {
        return currentGene;
    }
    public int getGene(){
        return genes[currentGene];
    }

    public int getAge() {
        return age;
    }
    public List<Animal> getChildren() {
        return children;
    }
    public int getDirection() {
        return this.direction;
    }

    public Vector2d move(Earth map) {
        if (energy == 0) {
            return DEATH_VECTOR;
        }
        int direction = genes[currentGene];
        Vector2d potentialNewPosition;
        this.direction = (this.direction + direction) % 8;
        potentialNewPosition = this.getPosition().add(geneToMapDirection(this.direction).toUnitVector());
        potentialNewPosition = map.moveAroundEarth(potentialNewPosition);
        if (map.canMoveTo(potentialNewPosition)) {
            this.position = potentialNewPosition;
        }else{this.direction = (this.direction+4)%8;}
        energy--;
        age++;
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