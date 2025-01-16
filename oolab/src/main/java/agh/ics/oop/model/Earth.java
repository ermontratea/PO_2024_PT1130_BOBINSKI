package agh.ics.oop.model;

import java.util.*;

public class Earth {
    private final Boundary boundary;
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final Map<Vector2d, Animal> eventGrass = new HashMap<>();
    private final Map<Vector2d, Animal> activeAnimals = new HashMap<>();
    private final Map<Vector2d, Animal> weakerActiveAnimals = new HashMap<>();
    private final List<Vector2d> graves = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();
    protected final List<MapChangeListener> observers = new ArrayList<>();
    private HashSet<Vector2d> fertileLand= new HashSet<>();
    private HashSet<Vector2d> unfruitfulLand= new HashSet<>();
    private int amountOfGrass;
    Random random = new Random();

    public Earth(Boundary boundary, int amountOfGrass) {
        this.boundary = boundary;
        this.amountOfGrass = amountOfGrass;
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    public void move(Animal animal) {
        Vector2d where = animal.move(this);
        if (grass.containsKey(where)) {
            if (eventGrass.containsKey(where)) {
                Animal opponent = eventGrass.get(where);
                if (animal.isBetterAnimal(opponent)) {
                    eventGrass.replace(where, animal);
                }
            } else {
                eventGrass.put(where, animal);
            }
        }
        if (activeAnimals.containsKey(where)) {
            Animal active = activeAnimals.get(where);
            if (weakerActiveAnimals.containsKey(where)) {
                Animal weaker = weakerActiveAnimals.get(where);
                if (animal.isBetterAnimal(weaker)) {
                    if (animal.isBetterAnimal(active)) {
                        weakerActiveAnimals.replace(where, active);
                        activeAnimals.replace(where, animal);
                    } else {
                        weakerActiveAnimals.replace(where, animal);
                    }
                }
            } else if (animal.isBetterAnimal(active)) {
                weakerActiveAnimals.put(where, active);
                activeAnimals.replace(where, animal);
            } else {
                weakerActiveAnimals.put(where, animal);
            }
        } else {
            activeAnimals.put(where, animal);
        }
    }
    public void plantGrass() {
        List<Vector2d> fertileLand = new ArrayList<>(this.fertileLand);
        List<Vector2d> unfruitfulLand = new ArrayList<>(this.unfruitfulLand);
        int grassesOnFertile = 0 ;
        int grassesOnUnfruitful = 0 ;
        for (int i=1; i<amountOfGrass;i++){
            int value=random.nextInt(5);
            if (value != 0){
                grassesOnFertile++;
            }else grassesOnUnfruitful++;
        }
        Collections.shuffle(fertileLand);
        Collections.shuffle(unfruitfulLand);
        int[] result = fillWithGrass(fertileLand, 0, grassesOnFertile);
        int currentFertileIndex = result[0];
        int remainingGrass = result[1]+grassesOnUnfruitful;
        int[] result1 = fillWithGrass(unfruitfulLand, 0, remainingGrass);
        int stillRemainingGrass = result1[1];
        int[] result2 = fillWithGrass(fertileLand, currentFertileIndex, stillRemainingGrass);
    }
    private int[] fillWithGrass(List<Vector2d> land,int startingIndex,int remainingGrass){
        int currentIndex = startingIndex;
        int grownGrasses=0;
        while (currentIndex < land.size() && grownGrasses < remainingGrass){
            if (grass.containsKey(land.get(currentIndex))){
                currentIndex++;
            }
            else {
                grass.put(land.get(currentIndex), new Grass(land.get(currentIndex)));
                grownGrasses++;
                currentIndex++;
            }
        }
        remainingGrass-=grownGrasses;
        return new int[] {currentIndex,remainingGrass};
    }



    public boolean canMoveTo(Vector2d place) {
        return  (place.getY()<=boundary.upperRight().getY() && place.getY()>=0);
    }
}