package agh.ics.oop.model;

import java.util.*;


public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    public GrassField(int grassCount){

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

    @Override
    public Collection<WorldElement> getElements() {
        Collection<WorldElement> elements = super.getElements();
        elements.addAll(grass.values());
        return elements;
    }
    @Override
    public Boundary getCurrentBounds(){

        Vector2d lowerLeft = null;
        Vector2d upperRight =null;
        Collection<WorldElement> elements = getElements();
        for (WorldElement element: elements) {
            if (lowerLeft != null && upperRight != null) {
                lowerLeft = lowerLeft.lowerLeft(element.getPosition());
                upperRight = upperRight.upperRight(element.getPosition());
            }
            else{
                lowerLeft = element.getPosition();
                upperRight = element.getPosition();
            }
        }
        return new Boundary(lowerLeft, upperRight);
    };
}
