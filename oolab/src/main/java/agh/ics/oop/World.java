package agh.ics.oop;
import agh.ics.oop.model.Moves;
public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        Moves[] moves = OptionsParser.parse(args);
        run(moves);
        System.out.println("Stop");
    }
    public static void run(Moves[] moves)
    {
        for (Moves direction : moves) {
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
