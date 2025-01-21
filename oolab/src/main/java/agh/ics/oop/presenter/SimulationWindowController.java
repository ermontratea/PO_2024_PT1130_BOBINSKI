package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;
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
    private double cellHeight;
    private int width;
    private int height;
    private boolean isInitialised=false;
    private int healthyEnergy;
    @FXML
    private Button pauseResumeButton;
    private boolean isPaused = false;
    private SimulationEngine engine;
    @FXML
    private Label animalAmountField;
    @FXML
    private Label grassAmountField;
    @FXML
    private Label freeSquareField;
    @FXML
    private Label mostPopularGeneField;
    @FXML
    private Label averageEnergyField;
    @FXML
    private Label averageLifetimeField;
    @FXML
    private Label averageChildAmountField;
    @FXML
    private TextField makingSlave;
    @FXML
    private Label slaveGenom;
    @FXML
    private Label slaveActiveGen;
    @FXML
    private Label slaveEnergy;
    @FXML
    private Label slaveEatedPlants;
    @FXML
    private Label slaveChildrens;
    @FXML
    private Label slaveDescendants;
    @FXML
    private Label slaveAge;
    @FXML
    private Label slaveDayOfDeath;
    @FXML
    private Label dayField;


    private Animal slave = null;



    // Metoda do uruchamiania symulacji
    public void startSimulation(Simulation simulation) {
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        new Thread(() -> {engine.runSync();}).start();
    }
    @FXML
    private void onCloseButtonClicked() {
        Stage stage = (Stage) simulationLabel.getScene().getWindow();
        stage.close();
    }
    public void setWorldMap(Earth map) {
        this.cellHeight=calculateCellSize(mapGrid.getWidth(),mapGrid.getHeight());
        this.map = map;
        this.width=map.getBoundary().upperRight().getX()+1;
        this.height=map.getBoundary().upperRight().getY()+1;
        xMin=0;
        yMin=0;
        xMax=width-1;
        yMax=height-1;
        mapWidth=this.width;
        mapHeight=this.height;
        healthyEnergy = this.map.getEnergyToHealthy();

    }
    public void labelConstruction(){
        mapGrid.getColumnConstraints().add(new ColumnConstraints(this.cellHeight));
        mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }
    public void columnsConstruction(){
        for(int i=0; i<mapWidth; i++){
            Label label = new Label(Integer.toString(i+xMin));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(this.cellHeight ));
            mapGrid.add(label, i+1, 0);
        }
    }
    public void rowsConstruction(){
        for(int i=0; i<mapHeight; i++){
            Label label = new Label(Integer.toString(yMax-i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(this.cellHeight));
            mapGrid.add(label, 0, i+1);
        }
    }
    public void addElements(){
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i, j);

//
                StackPane cellPane = new StackPane();
                cellPane.setPrefSize(cellHeight, cellHeight);

                Rectangle background = new Rectangle(cellHeight, cellHeight);
                if (map.isOccupied(pos) && map.isGrassAt(pos)) {
                    background.setFill(Color.rgb(34, 139, 34));
                } else {
                    background.setFill(Color.rgb(144, 160, 70));
                }
                Circle animalCircle = null;
                if (map.isOccupied(pos) && map.objectAt(pos) instanceof Animal) {
                    Animal animal = (Animal) map.objectAt(pos);
                    if (slave!=null && slave.getPosition().equals(pos)){
                        updateSlave(slave);
                        if (slave.getEnergy()==0){
                            slaveDayOfDeath.setText("Dzień śmierci: "+ map.getDay());
                            slave.addEnergy(-1);
                        }
                        animalCircle = new Circle(cellHeight / 3);
                        animalCircle.setFill(Color.rgb(255, 0, 0));
                    }else{
                    int energy = animal.getEnergy();
                    if (energy<=healthyEnergy/2){
                    animalCircle = new Circle(cellHeight / 3);
                    animalCircle.setFill(Color.rgb(205, 175, 149));}
                    else if(energy<=healthyEnergy){
                        animalCircle = new Circle(cellHeight / 3);
                        animalCircle.setFill(Color.rgb(181, 101, 70));}
                    else if(energy<=healthyEnergy*3/2){animalCircle = new Circle(cellHeight / 3);
                        animalCircle.setFill(Color.rgb(150, 75, 0));}
                    else if(energy<=healthyEnergy*2){animalCircle = new Circle(cellHeight / 3);
                    animalCircle.setFill(Color.rgb(120, 60, 20));}
                    else{animalCircle = new Circle(cellHeight / 3);
                    animalCircle.setFill(Color.rgb(80, 40, 20));}}
                }
                cellPane.getChildren().add(background);
                if (animalCircle != null) {
                    cellPane.getChildren().add(animalCircle);
                }
                mapGrid.add(cellPane, i - xMin + 1, yMax - j + 1);

            }
        }
    }
    private void drawMap() {
        labelConstruction();
        columnsConstruction();
        rowsConstruction();
        addElements();
        mapGrid.setGridLinesVisible(false);
    }
    private double calculateCellSize(double gridWidth, double gridHeight) {
        double cellWidth = gridWidth / (mapWidth+2);
        double cellHeight = gridHeight / (mapHeight+2);
        return Math.min(cellWidth, cellHeight); // Pola kwadratowe
    }

    private void clearGrid(){
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(Earth worldMap, int animalAmount, int grassAmount, int freeSquare, int averageEnergy, int averageLifetime, int averageChildAmount) {
        setWorldMap(worldMap);
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            dayField.setText("Dzień:"+map.getDay());
            animalAmountField.setText("Ilość żywych zwierząt: " + animalAmount);
            grassAmountField.setText("Ilość trawy: " + grassAmount);
            freeSquareField.setText("Ilość wolnych pól: " + freeSquare);
            averageEnergyField.setText("Średnia ilość energi: " + averageEnergy + " /1000");
            averageLifetimeField.setText("Średni wiek życia: " + averageLifetime + " /1000");
            averageChildAmountField.setText("Średnia ilość dzieci: " + averageChildAmount + " /1000");
        });
    }
    @FXML
    public void onStopSimulation(){
        map.stopRunning();
    }

    @FXML
    public void onStartSimulation(){
        map.startRunning();
    }
    @FXML
    public void onShowFertile() {
        if (!map.isRunning()) {
            Platform.runLater(() -> {
                clearGrid();
                labelConstruction();
                columnsConstruction();
                rowsConstruction();
                addElementsRedFertile();
                mapGrid.setGridLinesVisible(false);
            });
        }

    }
    public void addElementsRedFertile(){
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i, j);
//
                StackPane cellPane = new StackPane();
                cellPane.setPrefSize(cellHeight, cellHeight);

                Rectangle background = new Rectangle(cellHeight, cellHeight);
                if (map.getFertileLand().contains(pos)){
                    if (map.isOccupied(pos) && map.isGrassAt(pos)) {
                        background.setFill(Color.rgb(139, 0, 0));
                }   else {
                        background.setFill(Color.rgb(255, 0, 0));
                }}else{
                    if (map.isOccupied(pos) && map.isGrassAt(pos)) {
                        background.setFill(Color.rgb(34, 139, 34));
                }   else {
                        background.setFill(Color.rgb(144, 160, 70));
                }}
                Circle animalCircle = null;
                if (map.isOccupied(pos) && map.objectAt(pos) instanceof Animal) {
                    Animal animal = (Animal) map.objectAt(pos);

                    int energy = animal.getEnergy();
                    if (energy<=healthyEnergy/2){
                        animalCircle = new Circle(cellHeight / 3);
                        animalCircle.setFill(Color.rgb(205, 175, 149));}
                    else if(energy<=healthyEnergy){
                        animalCircle = new Circle(cellHeight / 3);
                        animalCircle.setFill(Color.rgb(181, 101, 70));}
                    else if(energy<=healthyEnergy*3/2){animalCircle = new Circle(cellHeight / 3);
                        animalCircle.setFill(Color.rgb(150, 75, 0));}
                    else if(energy<=healthyEnergy*2){animalCircle = new Circle(cellHeight / 3);
                        animalCircle.setFill(Color.rgb(120, 60, 20));}
                    else{animalCircle = new Circle(cellHeight / 3);
                        animalCircle.setFill(Color.rgb(80, 40, 20));}
                }
                cellPane.getChildren().add(background);
                if (animalCircle != null) {
                    cellPane.getChildren().add(animalCircle);
                }
                mapGrid.add(cellPane, i - xMin + 1, yMax - j + 1);

            }
        }
    }
    @FXML
    public void onMakeSlave() {

        if (!map.isRunning() && 0 < Integer.parseInt(makingSlave.getText()) && Integer.parseInt(makingSlave.getText()) < map.getAnimalsAmount() ) {
            slave = (Animal) map.getAnimals().toArray()[Integer.parseInt(makingSlave.getText())];
            updateSlave(slave);
            Platform.runLater(() -> {
                clearGrid();
                labelConstruction();
                columnsConstruction();
                rowsConstruction();
                addElements();
                mapGrid.setGridLinesVisible(false);
            });
        }
    }

    private void updateSlave(Animal animal) {
        slaveGenom.setText("Genom zwierzaka: " + Arrays.toString(animal.getGenes()));
        slaveActiveGen.setText("Aktywowana część genomu: " + animal.getCurrentGene());
        slaveEnergy.setText("Energia zwierzaka: " + animal.getEnergy());
        slaveEatedPlants.setText("Zjedzone rośliny: "+animal.getEatenGrass());
        slaveChildrens.setText("Ilość dzieci: " + animal.getChildren().size());
        slaveDescendants.setText("Ilość potomków: "+ animal.getDescendants());
        slaveAge.setText("Wiek: " + animal.getAge());

    }

    @FXML
    public void onReleaseSlave() {
        slave = null;
        slaveGenom.setText("Genom zwierzaka: ");
        slaveActiveGen.setText("Aktywowana część genomu: ");
        slaveEnergy.setText("Energia zwierzaka: ");
        slaveEatedPlants.setText("Zjedzone rośliny: ");
        slaveChildrens.setText("Ilość dzieci: ");
        slaveDescendants.setText("Ilość potomków: ");
        slaveAge.setText("Wiek: ");
        slaveDayOfDeath.setText("Dzień śmierci: ");
        Platform.runLater(() -> {
            clearGrid();
            labelConstruction();
            columnsConstruction();
            rowsConstruction();
            addElements();
            mapGrid.setGridLinesVisible(false);
        });
    }

}