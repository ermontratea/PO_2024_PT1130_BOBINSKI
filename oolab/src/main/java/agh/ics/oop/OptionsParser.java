package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        List<MoveDirection> directions = new ArrayList<>();

        for (String arg : args) {
            switch (arg) {
                case "f":
                    directions.add(MoveDirection.FORWARD);
                    break;
                case "b":
                    directions.add(MoveDirection.BACKWARD);
                    break;
                case "r":
                    directions.add(MoveDirection.RIGHT);
                    break;
                case "l":
                    directions.add(MoveDirection.LEFT);
                    break;
                default:
                    // Ignorujemy niepoprawne wartości
                    break;
            }
        }

        return directions.toArray(new MoveDirection[0]);
    }
}