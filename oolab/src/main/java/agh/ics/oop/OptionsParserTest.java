package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import agh.ics.oop.model.Moves;

public class OptionsParserTest {

    @Test
    public void testParseMoves() {
        OptionsParser parser = new OptionsParser();

        String[] args = {"f", "b", "r", "l"};

        Moves[] expectedMoves = {
                Moves.FORWARD,
                Moves.BACKWARD,
                Moves.RIGHT,
                Moves.LEFT
        };

        Moves[] moves = parser.parse(args);

        assertArrayEquals(expectedMoves, moves, "Błąd");
    }
}
