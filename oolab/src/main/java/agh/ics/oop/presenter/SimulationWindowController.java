package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.Earth;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.List;

public class SimulationWindowController implements MapChangeListener {
    private Earth map;
    @FXML
    private Label simulationLabel;
    @FXML
    private GridPane mapGrid;

    @FXML
    private Label movementDescriptionLabel;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int mapWidth;
    private int mapHeight;

    private int width;
    private int height;

    // Metoda do uruchamiania symulacji
    public void startSimulation(Simulation simulation) {
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        // Tutaj dodaj logikę uruchamiania animacji lub symulacji
        new Thread(() -> {
            engine.runSync();
        }).start();
    }

    @FXML
    private void onCloseButtonClicked() {
        // Zamknij okno symulacji
        Stage stage = (Stage) simulationLabel.getScene().getWindow();
        stage.close();
    }

    public void setWorldMap(Earth map) {
        this.map = map;
        this.width=map.getBoundary().upperRight().getX()+1;
        this.height=map.getBoundary().upperRight().getY()+1;
        xMin=0;
        yMin=0;
        xMax=width-1;
        yMax=height-1;
        mapWidth=this.width;
        mapHeight=this.height;

    }

    public void labelConstruction(){
        mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
        mapGrid.getRowConstraints().add(new RowConstraints(height));
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public void columnsConstruction(){
        for(int i=0; i<mapWidth; i++){
            Label label = new Label(Integer.toString(i+xMin));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            mapGrid.add(label, i+1, 0);
        }
    }

    public void rowsConstruction(){
        for(int i=0; i<mapHeight; i++){
            Label label = new Label(Integer.toString(yMax-i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(height));
            mapGrid.add(label, 0, i+1);
        }
    }

    public void addElements(){
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                ///zmienić tworzenei wektorów
                Vector2d pos = new Vector2d(i, j);
                if (map.isOccupied(pos)) {
                    mapGrid.add(new Label(map.objectAt(pos).toString()), i - xMin + 1, yMax - j + 1);
                }
                else {
                    mapGrid.add(new Label(" "), i - xMin + 1, yMax - j + 1);
                }

                mapGrid.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size() - 1), HPos.CENTER);
            }
        }
    }

    private void drawMap() {
        labelConstruction();
        columnsConstruction();
        rowsConstruction();
        addElements();
        mapGrid.setGridLinesVisible(true);
    }

    private void clearGrid(){
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(Earth worldMap, String message) {
        setWorldMap(worldMap);
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            movementDescriptionLabel.setText(message);
        });
    }
}