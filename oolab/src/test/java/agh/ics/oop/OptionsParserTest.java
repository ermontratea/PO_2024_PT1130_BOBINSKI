//package agh.ics.oop;
//import agh.ics.oop.model.trash.MoveDirection;
//import agh.ics.oop.model.trash.OptionsParser;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class OptionsParserTest {
//
//    @Test
//    void oneDirectionForward() {
//        String[] args = {"f"};
//        List<MoveDirection> expected = List.of(MoveDirection.FORWARD);
//        assertEquals(expected, OptionsParser.parse(args));
//    }
//    @Test
//    void multipleDirections() {
//        String[] args = {"f", "b", "r", "l"};
//        List<MoveDirection> expected = List.of(
//                MoveDirection.FORWARD,
//                MoveDirection.BACKWARD,
//                MoveDirection.RIGHT,
//                MoveDirection.LEFT
//        );
//        assertEquals(expected, OptionsParser.parse(args));
//    }
//    @Test
//    public void invalidCommand() {
//        OptionsParser parser = new OptionsParser();
//        String[] commands = {"invalid", "f", "b"};
//
//
//        assertThrows(IllegalArgumentException.class, () -> parser.parse(commands));
//    }
//}