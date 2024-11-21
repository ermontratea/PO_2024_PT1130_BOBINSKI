package agh.ics.oop.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final List<Vector2d> positions;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        List<Vector2d> allPositions = new ArrayList<>();
        for (int x = 0; x <= maxWidth; x++) {
            for (int y = 0; y <= maxHeight; y++) {
                allPositions.add(new Vector2d(x, y));
            }
        }

        Collections.shuffle(allPositions, new Random());

        this.positions = allPositions.subList(0, grassCount);
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return positions.iterator();
    }
}

