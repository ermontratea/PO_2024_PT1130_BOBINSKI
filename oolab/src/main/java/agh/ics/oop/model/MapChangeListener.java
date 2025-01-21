package agh.ics.oop.model;

public interface MapChangeListener {
    void mapChanged(Earth worldMap, int animalAmount, int grassAmount, int freeSquare, int averageEnergy, int averageLifetime, int averageChildAmount);
}