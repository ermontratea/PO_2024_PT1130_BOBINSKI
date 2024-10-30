package agh.ics.oop;

import agh.ics.oop.model.Moves;
import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static Moves[] parse(String[] args) {
        List<Moves> directions = new ArrayList<>();

        for (String arg : args) {
            switch (arg) {
                case "f":
                    directions.add(Moves.FORWARD);
                    break;
                case "b":
                    directions.add(Moves.BACKWARD);
                    break;
                case "r":
                    directions.add(Moves.RIGHT);
                    break;
                case "l":
                    directions.add(Moves.LEFT);
                    break;
                default:
                    // Ignorujemy niepoprawne wartości
                    break;
            }
        }

        return directions.toArray(new Moves[0]);
    }
}