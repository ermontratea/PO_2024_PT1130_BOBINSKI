package agh.ics.oop.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    @Test
    public void nextTest() {
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
    }
    @Test
    public void previousTest() {
        assertEquals(MapDirection.WEST, MapDirection.NORTH.next());
        assertEquals(MapDirection.NORTH, MapDirection.EAST.next());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.next());
    }

}
