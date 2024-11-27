package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final MapVisualizer visualizer;
    public GrassField(int grassCount){
        this.visualizer = new MapVisualizer(this);
        //podstawowa wersja labu
//        Random generator = new Random();
//        while(grass.size() < grassCount){
//            int x = generator.nextInt((int)(Math.sqrt(10 * grassCount)));
//            int y = generator.nextInt((int)(Math.sqrt(10 * grassCount)));
//            Vector2d potentialPosition = new Vector2d(x,y);
//            if (objectAt(potentialPosition)==null){
//                grass.put(potentialPosition, new Grass(potentialPosition));
//            }
//        }
        //wersja bonusowa
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator((int)(Math.sqrt(10 * grassCount)), (int)(Math.sqrt(10 * grassCount)), grassCount);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grass.put(grassPosition, new Grass(grassPosition));
        }

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position)!=null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.get(position)==null) {
            return grass.get(position);
        }
        else return animals.get(position);
    }

    public String toString() {
        if (animals.size() > 0) {

            Vector2d lowerLeft = null;
            Vector2d upperRight = null;

            for (Vector2d position : animals.keySet()) {
                if (lowerLeft == null || position.lowerLeft(lowerLeft).equals(position)) {
                    lowerLeft = position;
                }
                if (upperRight == null || position.upperRight(upperRight).equals(position)) {
                    upperRight = position;
                }
            }

            return visualizer.draw(lowerLeft, upperRight);
        }
        else return visualizer.draw(new Vector2d(0,0),new Vector2d((int)Math.sqrt(10 * grass.size()),(int)Math.sqrt(10 * grass.size())));
    }
    @Override
    public Collection<WorldElement> getElements() {
        Collection<WorldElement> elements = super.getElements();
        elements.addAll(grass.values());
        return elements;
    }
}
