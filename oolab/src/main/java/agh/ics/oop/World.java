package agh.ics.oop;
import agh.ics.oop.model.*;

import java.util.List;
import java.util.Arrays;

public class World {
    public static void main(String[] args) {
        List<String> objects = Arrays.asList("Ala", "ma", "kota", "sowoniedźwiedzia");
        List<MoveDirection> directions = Arrays.asList(MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.LEFT);

        WorldMap<String, Integer> map = new TextMap();
        Simulation<String, Integer> simulation = new Simulation<>(objects, directions, map);

        simulation.run();

        System.out.println(map.toString());
    }

    public static void run(MoveDirection[] moves)
    {
        for (MoveDirection direction : moves) {
            switch (direction) {
                case FORWARD:
                    System.out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    System.out.println("Zwierzak idzie do tyłu");
                    break;
                case RIGHT:
                    System.out.println("Zwierzak skręca w prawo");
                    break;
                case LEFT:
                    System.out.println("Zwierzak skręca w lewo");
                    break;
            }
        }
    }
}
