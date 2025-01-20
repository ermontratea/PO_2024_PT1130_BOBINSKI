package agh.ics.oop.model;

import java.util.*;

public class Earth {
    private final Boundary boundary;
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final Map<Vector2d, Animal> eventGrass = new HashMap<>();
    private final Map<Vector2d, Animal> activeAnimals = new HashMap<>();
    private final Map<Vector2d, Animal> weakerActiveAnimals = new HashMap<>();
    private final Set<Animal> animals = new HashSet<>();
    private final Set<Vector2d> graves = new HashSet<>();
    private final HashSet<Vector2d> fertileLand= new HashSet<>();
    private final HashSet<Vector2d> unfruitfulLand= new HashSet<>();
    private int geneLength;
    private List<Integer> allGenes;
    private List<Integer> allGenesSecondary;
    private int energyToHealthy;
    private int energyToBirth;
    private int energyFromPlant;
    private int fertileArea;
    private List<Vector2d> graveArea = new ArrayList<>();
    private final boolean swap;

    protected final List<MapChangeListener> observers = new ArrayList<>();
    Random random = new Random();

    public Boundary getBoundary() {
        return boundary;
    }

    public Earth(int width, int height, int plantAmount, int animalAmount, int geneLength, int startingEnergy, int energyToHealthy, int energyToBirth, int energyFromPlant, boolean deadBody, boolean swap) {
        this.boundary = new Boundary(new Vector2d(0,0), new Vector2d(width-1,height-1));
        if (!deadBody) {
            int equatorSizeHalf = height / 10;
            int center = height / 2;
            int equatorStart = center - equatorSizeHalf;
            int equatorEnd = center + equatorSizeHalf;
            for (int j = 0; j < equatorStart; j++) {
                for (int i = 0; i < width; i++) {
                    unfruitfulLand.add(new Vector2d(i, j));
                }
            }
            for (int j = equatorStart; j < equatorEnd; j++) {
                for (int i = 0; i < width; i++) {
                    fertileLand.add(new Vector2d(i, j));
                }
            }
            for (int j = equatorEnd; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    unfruitfulLand.add(new Vector2d(i, j));
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
            animals.add(new Animal(new Vector2d(random.nextInt(width), random.nextInt(height)),geneLength,startingEnergy));
        }
        this.energyToHealthy = energyToHealthy;
        this.energyToBirth = energyToBirth;
        allGenes = new ArrayList<>();
        for (int i = 0; i < geneLength; i++) {
            allGenes.add(i);
        }
        allGenesSecondary = new ArrayList<>();
        for (int i = 0; i < geneLength; i++) {
            allGenesSecondary.add(i);
        }
        this.geneLength = geneLength;
        fertileArea = (width * height) / 5;
        for (MapDirection direction : MapDirection.values()) {
            graveArea.add(direction.toUnitVector());
        }
        this.swap = swap;
    }
    public int[] sex(Animal father, Animal mother) {
        if (mother.getEnergy() >= energyToHealthy) {
            int genesFromFather = (geneLength * father.getEnergy()) / (father.getEnergy() + mother.getEnergy());
            int[] newbornGenes = new int[geneLength];
            int[] fatherGenes = father.getGenes();
            int[] motherGenes = mother.getGenes();
            if (random.nextBoolean()) {
                for (int i = 0; i < genesFromFather; i++) {
                    newbornGenes[i] = fatherGenes[i];
                }
                for (int i = genesFromFather; i < geneLength; i++) {
                    newbornGenes[i] = motherGenes[i];
                }
            } else {
                for (int i = geneLength - 1; i > (geneLength - genesFromFather - 1); i--) {
                    newbornGenes[i] = fatherGenes[i];
                }
                for (int i = (geneLength - genesFromFather - 1); i > -1; i--) {
                    newbornGenes[i] = motherGenes[i];
                }
            }
            return newbornGenes;
        }
        return null;
    }

    // sex
    public void sexRandomMutation(Animal father, Animal mother) {
        int[] newbornGenes = sex(father, mother);
        if (newbornGenes != null) {
            int amountOfMutation = random.nextInt(geneLength + 1);
            Collections.shuffle(allGenes);
            for (int i = 0; i < amountOfMutation; i++) {
                newbornGenes[allGenes.get(i)] = random.nextInt(10);
            }
            Animal newborn = new Animal(father.getPosition(), geneLength, 2*energyToBirth, newbornGenes);
            animals.add(newborn);
            mother.addEnergy( -energyToBirth );
            father.addEnergy( -energyToBirth );
        }
    }

    // sex
    public void sexSwap(Animal father, Animal mother) {
        int[] newbornGenes = sex(father, mother);
        if (newbornGenes != null) {
            int amountOfMutation = random.nextInt(geneLength + 1);
            Collections.shuffle(allGenes);
            Collections.shuffle(allGenesSecondary);
            for (int i = 0; i < amountOfMutation; i++) {
                int firstGene = newbornGenes[allGenes.get(i)];
                newbornGenes[allGenes.get(i)] = newbornGenes[allGenesSecondary.get(i)];
                newbornGenes[allGenesSecondary.get(i)] = firstGene;
            }
            Animal newborn = new Animal(father.getPosition(), geneLength, 2*energyToBirth, newbornGenes);
            animals.add(newborn);
            mother.addEnergy( -energyToBirth );
            father.addEnergy( -energyToBirth );
        }
    }
    public void sexyTime() {
        Set<Vector2d> sexPlaces = weakerActiveAnimals.keySet();
        if (swap) {
            for (Vector2d place : sexPlaces) {
                this.sexSwap(activeAnimals.get(place), weakerActiveAnimals.get(place));
            }
        } else {
            for (Vector2d place : sexPlaces) {
                this.sexRandomMutation(activeAnimals.get(place), weakerActiveAnimals.get(place));
            }
        }
    }

    //rusza wszystkie zwierzęta na mapie
    public void moveAllAnimals(){
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            this.move(animal);
            if (animal.getEnergy()==0) {
                graves.add(animal.getPosition());// Jeśli zwierzę umarło
                iterator.remove();  // Bezpieczne usunięcie elementu
            }
        }
        notifyObservers("Zwierzaki się ruszyły" + animals.size());
    }

    public void move(Animal animal) {
        Vector2d where = animal.move(this);
        if (!where.equals(Animal.DEATH_VECTOR)) {
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

    //metoda co sprawdza czy dana pozycja jest nadal w granicach mapy (można wyjść za boczne granice, ale nie za górną i dolną)
    public boolean canMoveTo(Vector2d place) {
        return  (place.getY()<=boundary.upperRight().getY() && place.getY()>=0);
    }
    public Vector2d moveAroundEarth(Vector2d potentialPosition) {
        if (potentialPosition.getX() == -1) {
            return new Vector2d(boundary.upperRight().getX(), potentialPosition.getY());
        }
        if (potentialPosition.getX() > boundary.upperRight().getX()) {
            return new Vector2d(boundary.lowerLeft().getX(), potentialPosition.getY());
        }
        return potentialPosition;
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

    //metoda co zasadza określoną liczbą roślinek na mapie (jeśli się mieszczą)
    public void fillEarthWithPlants(int plantAmount) {
        List<Vector2d> fertileLand = new ArrayList<>(this.fertileLand);
        List<Vector2d> unfruitfulLand = new ArrayList<>(this.unfruitfulLand);
        int plantsOnFertile = 0 ;
        int plantsOnUnfruitful = 0 ;
        for (int i = 1; i<= plantAmount; i++){
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

    public void clearLists(){
        eventGrass.clear();
        graves.clear();
        activeAnimals.clear();
        weakerActiveAnimals.clear();
        ///DO NAPISANIA jeśli deadBody : unfruitful znów wszystkie pola a fertile żadne
    }
    ///DO NAPISANIA - może jednak zrobić enuma mapdirections

    public void fertileNearBodies() {
        unfruitfulLand.addAll(fertileLand);
        fertileLand.clear();
        // zaokrąglanie w góre
        int landPerGrave = (fertileArea + graves.size() - 1) / graves.size();
        landPerGrave = Math.min(9, landPerGrave);
        for (Vector2d grave : graves) {
            Collections.shuffle(graveArea);
            for (int i = 0; i < landPerGrave; i++) {
                fertileLand.add(grave.add(graveArea.get(i)));
            }
        }
        unfruitfulLand.removeAll(fertileLand);
    }
    public boolean isOccupied(Vector2d position) {
        return grass.containsKey(position) || activeAnimals.containsKey(position);
    }

    public WorldElement objectAt(Vector2d position) {
        if (activeAnimals.containsKey(position)) {
            return activeAnimals.get(position);
        }
        return grass.get(position);
    }

    public Set<Vector2d> getFertileLand(){
        return fertileLand;
    }
    public Set<Vector2d> getUnfruitfulLand(){
        return unfruitfulLand;
    }
    public Map<Vector2d,Grass> getGrass(){
        return grass;
    }
    public Set<Animal> getAnimals(){
        return animals;
    }
    public Set<Vector2d> getGraves(){
        return graves;
    }
    public Map<Vector2d,Animal> getEventGrass(){
        return eventGrass;
    }
    public Map<Vector2d,Animal> getActiveAnimals(){
        return activeAnimals;
    }

}