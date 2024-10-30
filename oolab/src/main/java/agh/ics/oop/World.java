package agh.ics.oop;
import agh.ics.oop.model.Moves;
import agh.ics.oop.model.Vector2d;
public class World {
    public static void main(String[] args) {

        System.out.println("Start");
        Moves[] moves = OptionsParser.parse(args);
        run(moves);
        System.out.println("Stop");

        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));


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
