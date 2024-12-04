package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.model.exceptions.IncorrectPositionException;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class World {
    public static void main(String[] args) {
        try {
            String[] inputArgs = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
            List<MoveDirection> directions = OptionsParser.parse(inputArgs);
            List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
            GrassField map = new GrassField(10);
            map.addObserver(new ConsoleMapDisplay());
            Simulation simulation0 = new Simulation(positions, directions, map);

            simulation0.run();
            System.out.println("  ");
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println("Unexpected error");
            e.printStackTrace();
        }
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
