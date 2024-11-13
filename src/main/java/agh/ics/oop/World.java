package agh.ics.oop;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class World {
    public static void main(String[] args) {
        List<MoveDirection> directions0 = OptionsParser.parse(args);
        List<Vector2d> positions0 = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation0 = new Simulation(positions0, directions0);
        simulation0.run();

        System.out.println(" ");

        String[] inputArgs = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parse(inputArgs);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));

        // Symulacja
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();
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
