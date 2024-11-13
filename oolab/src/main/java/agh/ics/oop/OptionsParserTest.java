package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import agh.ics.oop.model.MoveDirection;

public class OptionsParserTest {

    @Test
    public void testParseMoves() {
        OptionsParser parser = new OptionsParser();

        String[] args = {"f", "b", "r", "l"};

        MoveDirection[] expectedMoves = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT
        };

        MoveDirection[] moves = parser.parse(args);

        assertArrayEquals(expectedMoves, moves, "Błąd");
    }
}
