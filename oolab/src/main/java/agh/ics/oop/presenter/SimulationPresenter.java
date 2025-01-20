package agh.ics.oop.presenter;

import agh.ics.oop.model.trash.MoveDirection;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static agh.ics.oop.model.trash.OptionsParser.parse;

public class SimulationPresenter implements MapChangeListener {
    private Earth map;
    @FXML
    private GridPane mapGrid;
    @FXML
    private TextField mapWidthField;
    @FXML
    private TextField mapHeightField;
    @FXML
    private TextField plantAmountField;
    @FXML
    private TextField animalAmountField;
    @FXML
    private TextField geneLengthField;
    @FXML
    private TextField startingEnergyField;
    @FXML
    private TextField energyToHealthyField;
    @FXML
    private TextField energyToBirthField;
    @FXML
    private TextField energyFromPlantField;
    @FXML
    private TextField deadBodyField;
    @FXML
    private TextField swapField;
    @FXML
    private TextField plantPerDayField;

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

    @FXML
    private void onSimulationStartClicked() throws Exception {
        int width = Integer.parseInt(mapWidthField.getText());
        int height = Integer.parseInt(mapHeightField.getText());
        int plantAmount = Integer.parseInt(plantAmountField.getText());
        int animalAmount = Integer.parseInt(animalAmountField.getText());
        int geneLength = Integer.parseInt(geneLengthField.getText());
        int startingEnergy = Integer.parseInt(startingEnergyField.getText());
        int energyToHealthy = Integer.parseInt(energyToHealthyField.getText());
        int energyToBirth = Integer.parseInt(energyToBirthField.getText());
        int energyFromPlant = Integer.parseInt(energyFromPlantField.getText());
        int deadBodyInt = Integer.parseInt(deadBodyField.getText());
        int swapInt = Integer.parseInt(swapField.getText());
        int plantPerDay = Integer.parseInt(plantPerDayField.getText());
        boolean deadBody;
        boolean swap;
        if (deadBodyInt == 0) {deadBody = false;}
        else if (deadBodyInt == 1) {deadBody = true;}
        else{throw new IllegalArgumentException("Wartość powinna być 0 albo 1");}
        if (swapInt == 0) {swap = false;}
        else if (swapInt == 1) {swap = true;}
        else{throw new IllegalArgumentException("Wartość powinna być 0 albo 1");}

        Earth map = new Earth(width, height, plantAmount, animalAmount, geneLength, startingEnergy, energyToHealthy, energyToBirth, energyFromPlant, deadBody, swap);
        map.addObserver(this);
        Simulation simulation = new Simulation(plantPerDay,map);

//        movementDescriptionLabel.setText("Simulation started with moves: ");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("mapView.fxml"));
            BorderPane viewRoot = loader.load();

            SimulationWindowController controller = loader.getController();
            controller.startSimulation(simulation);
            Stage primaryStage = new Stage();
            var scene = new Scene(viewRoot);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Simulation");
            primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
            primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    @FXML
    private void newGame(){
        SimulationApp simulationApp = new SimulationApp();
        try {
            simulationApp.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}