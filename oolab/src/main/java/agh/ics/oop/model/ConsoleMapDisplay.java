package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int changes = 0;
    private static final Object LOCK = new Object();
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        synchronized (LOCK) {
            changes++;
            System.out.println("Aktualizacja nr " + changes + ": " + message);
            System.out.println("ID mapy:" + worldMap.getId());
            System.out.println(worldMap);
        }
    }
}
