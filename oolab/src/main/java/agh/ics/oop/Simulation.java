package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.exceptions.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Simulation implements Runnable {
    private final Earth map;
    private final List<Animal> animals;
    private int plantPerDay;

    public Simulation(int plantPerDay, Earth map) {
        this.map = map;
        this.animals = new ArrayList<>();
        this.plantPerDay = plantPerDay;

    }

    @Override
    public void run() {
        int day =1;
        while (day<101) {
            try {
                //1. umieranie i ruszanie zwierzaków (tworzenie się list eventGrass i activeAnimals, weakerActiveAnimals)
                map.moveAllAnimals();
                ///2. (ew) zmienianie się żyznych pól przy martwych zwierzakach

                //3. jedzenie roślin
                map.dinner();
                ///4. rozmnażanie się zwierząt
                map.sexyTime();

                // 5. rośnięcie roślin
                map.fillEarthWithPlants(plantPerDay);
                // 6. clearowanie wszystkich list z eventami, a także chyba grobów (chyba że zostają na kilka dni)
                Thread.sleep(500);
                map.clearLists();

                //koniec dnia
                day++;
            }catch (InterruptedException e){
                System.err.print(e.getMessage());
            }
        }
    }
}