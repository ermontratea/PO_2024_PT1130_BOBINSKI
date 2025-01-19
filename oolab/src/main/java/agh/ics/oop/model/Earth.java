package agh.ics.oop.model;

import java.util.*;

public class Earth {
    private final Boundary boundary;
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final Map<Vector2d, Animal> eventGrass = new HashMap<>();
    private final Map<Vector2d, Animal> activeAnimals = new HashMap<>();
    private final Map<Vector2d, Animal> weakerActiveAnimals = new HashMap<>();
    private final HashSet<Vector2d> graves = new HashSet<>();
    private final HashSet<Animal> animals = new HashSet<>();
    protected final List<MapChangeListener> observers = new ArrayList<>();
    private final HashSet<Vector2d> fertileLand= new HashSet<>();
    private final HashSet<Vector2d> unfruitfulLand= new HashSet<>();
    private final int energyFromPlant;
    Random random = new Random();

    public Earth(int width, int height, int plantAmount, int animalAmount, int geneLength, int startingEnergy, int energyToBreed, int energyToBirth, int energyFromPlant, boolean deadBody) {
        this.boundary = new Boundary(new Vector2d(0,0), new Vector2d(width-1,height-1));
        if (!deadBody) {
            for (int i = 0; i < width; i++) {
                fertileLand.add(new Vector2d(i, height / 2));
            }
            for (int j = 0; j < height; j++) {
                if (j != height / 2) {
                    for (int i = 0; i < width; i++) {
                        unfruitfulLand.add(new Vector2d(i, j));
                    }
                }
            }
        }else{
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    unfruitfulLand.add(new Vector2d(i, j));
                }

            }
        }
        this.energyFromPlant = energyFromPlant;
        fillEarthWithPlants(plantAmount);
        for (int i=0; i<animalAmount; i++) {
            animals.add(new Animal(new Vector2d(random.nextInt(width), random.nextInt(height)),geneLength,startingEnergy,energyToBreed,energyToBirth));
        }
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
    //sprawdza czy zwierzę jest martwe
    public boolean isDead(Animal animal) {
        return !animals.contains(animal);
    }
    //rusza wszystkie zwierzęta na mapie
    public void moveAllAnimals(){
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            this.move(animal);
            if (this.isDead(animal)) {  // Jeśli zwierzę umarło
                graves.add(animal.getPosition());
                iterator.remove();  // Bezpieczne usunięcie elementu
            }
        }
    }
    //jeśli animals jest ArrayListem to to jest liniowe, jesli będzie HashSetem to będzie lepiej
    public void die(Animal animal) {
        animals.remove(animal);
    }
    //  vvv  my chyba chcemy clearować te listy po każdym dniu symulacji cn?  vvv
    public void move(Animal animal) {
        Vector2d where = animal.move(this);
        if (!isDead(animal)) {
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
    }
    //metoda co zasadza określoną liczbą roślinek na mapie (jeśli się mieszczą)
    public void fillEarthWithPlants(int plantAmount) {
        List<Vector2d> fertileLand = new ArrayList<>(this.fertileLand);
        List<Vector2d> unfruitfulLand = new ArrayList<>(this.unfruitfulLand);
        int plantsOnFertile = 0 ;
        int plantsOnUnfruitful = 0 ;
        for (int i = 1; i< plantAmount; i++){
            int value=random.nextInt(5);
            if (value != 0){
                plantsOnFertile++;
            }else plantsOnUnfruitful++;
        }
        Collections.shuffle(fertileLand);
        Collections.shuffle(unfruitfulLand);
        int[] result = fillLandWithPlants(fertileLand, 0, plantsOnFertile);
        int currentFertileIndex = result[0];
        int remainingPlants = result[1]+plantsOnUnfruitful;
        int[] result1 = fillLandWithPlants(unfruitfulLand, 0, remainingPlants);
        int stillRemainingPlants = result1[1];
        int[] result2 = fillLandWithPlants(fertileLand, currentFertileIndex, stillRemainingPlants);
    }
    //metoda co próbuje zasadzić określoną liczbę roślinek na określonej ziemii (żyznej lub jałowej)
    private int[] fillLandWithPlants(List<Vector2d> land, int startingIndex, int remainingPlants){
        int currentIndex = startingIndex;
        int grownPlants=0;
        while (currentIndex < land.size() && grownPlants < remainingPlants){
            if (grass.containsKey(land.get(currentIndex))){
                currentIndex++;
            }
            else {
                grass.put(land.get(currentIndex), new Grass(land.get(currentIndex)));
                grownPlants++;
                currentIndex++;
            }
        }
        remainingPlants-=grownPlants;
        return new int[] {currentIndex,remainingPlants};
    }
    public void dinner(){
        for ( var eatingEvent : eventGrass.entrySet()){
            eatPlant(eatingEvent.getValue(),eatingEvent.getKey());
        }
    }
    public void eatPlant(Animal animal, Vector2d location) {
        animal.addEnergy(energyFromPlant);
        grass.remove(location);
    }
    //metoda co sprawdza czy dana pozycja jest nadal w granicach mapy (można wyjść za boczne granice, ale nie za górną i dolną)
    public boolean canMoveTo(Vector2d place) {
        return  (place.getY()<=boundary.upperRight().getY() && place.getY()>=0);
    }

    public void clearLists(){
        eventGrass.clear();
        graves.clear();
        activeAnimals.clear();
        weakerActiveAnimals.clear();
        ///DO NAPISANIA jeśli deadBody : unfruitful znów wszystkie pola a fertile żadne
    }
    ///DO NAPISANIA - może jednak zrobić enuma mapdirections

    public void fertileNearBodies(){
        for (Vector2d vector : graves){
            for (MapDirection direction : MapDirection.values()){
                Vector2d position = vector.add(direction.toUnitVector());
                if(canMoveTo(position)){
                    if(!fertileLand.contains(position)){

                    }
                }
            }
//            if (unfruitfulLand.contains(vector.add(NORTH))){
//            unfruitfulLand.remove(vector.add(NORTH));}
//            // itp itd
        }
    }

}