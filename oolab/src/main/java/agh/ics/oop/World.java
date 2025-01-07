package agh.ics.oop;
import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.*;

public class World {
    public static void main(String[] args) {
        try {
            String[] inputArgs = {"f","f","l","r","f","b","f","f","b","l","l","b","r","f","l","r","f","b","b","l","f","f"};
            List<MoveDirection> directions = OptionsParser.parse(inputArgs);

            List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
            ArrayList<Simulation> simulations = new ArrayList<>();

            for (int i = 0; i < 1000; i++) {
                GrassField grassFieldMap = new GrassField(10);
                grassFieldMap.addObserver(new ConsoleMapDisplay());
                Simulation simulation = new Simulation(positions, directions, grassFieldMap);
                simulations.add(simulation);
            }
            SimulationEngine engine = new SimulationEngine(simulations);
            System.out.println("Running simulations asynchronously:");
            engine.runAsyncInThreadPool();
            engine.awaitSimulationsEnd();
            System.out.println("All simulations completed.");
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
