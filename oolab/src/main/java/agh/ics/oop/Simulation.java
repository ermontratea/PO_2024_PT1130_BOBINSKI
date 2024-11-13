package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Simulation<T,P> {
    private final WorldMap<T,P> map;
    private final List<T> objects = new ArrayList<>();
    private final List<MoveDirection> directions  = new ArrayList<>();

    public Simulation(List<T> objects, List<MoveDirection> directions, WorldMap<T,P> map) {
        this.directions.addAll(directions);
        this.map = map;
        for(T object : objects){
            this.objects.add(object);
            map.place(object);
        }
    }


    public void run() {
        for (int i = 0; i < directions.size(); i++) {
            T object = objects.get(i % objects.size());
            MoveDirection direction = directions.get(i);
            map.move(object, direction);
        }
    }
}
