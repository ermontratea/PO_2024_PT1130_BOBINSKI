package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int changes = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        synchronized (System.out) {
            changes++;
            System.out.println("Aktualizacja nr " + changes + ": " + message);
            System.out.println(worldMap);
        }
    }
}
