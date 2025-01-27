package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;

import agh.ics.oop.model.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SimulationPresenter {
    private Earth map;
    @FXML
    private ChoiceBox<String> configChoiceBox;

    @FXML
    private TextField mapWidthField, mapHeightField, plantAmountField, animalAmountField,
            geneLengthField, startingEnergyField, energyToHealthyField,
            energyToBirthField, energyFromPlantField, plantPerDayField;
    @FXML
    private ChoiceBox<Boolean> deadBodyField;
    @FXML
    private ChoiceBox<Boolean> swapField;
    private Map<String, Config> configMap = new HashMap<>();

    // uwaga
    @FXML
    private void onConfigSelected() {
        String selectedConfig = configChoiceBox.getValue();
        if (selectedConfig != null && configMap.containsKey(selectedConfig)) {
            Config config = configMap.get(selectedConfig);
            setFields(config);
        }
    }

    private void setFields(Config config) {
        mapWidthField.setText(String.valueOf(config.mapWidth));
        mapHeightField.setText(String.valueOf(config.mapHeight));
        plantAmountField.setText(String.valueOf(config.plantAmount));
        animalAmountField.setText(String.valueOf(config.animalAmount));
        geneLengthField.setText(String.valueOf(config.geneLength));
        startingEnergyField.setText(String.valueOf(config.startingEnergy));
        energyToHealthyField.setText(String.valueOf(config.energyToHealthy));
        energyToBirthField.setText(String.valueOf(config.energyToBirth));
        energyFromPlantField.setText(String.valueOf(config.energyFromPlant));
        plantPerDayField.setText(String.valueOf(config.plantPerDay));
        deadBodyField.setValue(config.deadBody);
        swapField.setValue(config.swap);
    }

    @FXML
    public void initialize() {
        deadBodyField.setValue(false);
        swapField.setValue(false);
        Config config1 = new Config(10, 10, 5, 5, 4, 10, 5, 4, 3, 5, false, false);
        Config config2 = new Config(5, 5, 3, 3, 10, 6, 5, 3, 2, 8, true, true);
        Config config3 = new Config(25, 25, 40, 12, 6, 10, 8, 6, 4, 30, false, true);
        Config config4 = new Config(50, 50, 80, 24, 8, 15, 10, 8, 5, 60, true, false);
        Config config5 = new Config(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, false, false);
        configMap.put("Konfiguracja 1", config1);
        configMap.put("Konfiguracja 2", config2);
        configMap.put("Konfiguracja 3", config3);
        configMap.put("Konfiguracja 4", config4);
        configMap.put("Konfiguracja 5", config5);
    }

    @FXML
    private void onSaveConfig() {
        String configName = configChoiceBox.getValue();
        Config newConfig = new Config(
                Integer.parseInt(mapWidthField.getText()),
                Integer.parseInt(mapHeightField.getText()),
                Integer.parseInt(plantAmountField.getText()),
                Integer.parseInt(animalAmountField.getText()),
                Integer.parseInt(geneLengthField.getText()),
                Integer.parseInt(startingEnergyField.getText()),
                Integer.parseInt(energyToHealthyField.getText()),
                Integer.parseInt(energyToBirthField.getText()),
                Integer.parseInt(energyFromPlantField.getText()),
                Integer.parseInt(plantPerDayField.getText()),
                deadBodyField.getValue(),
                swapField.getValue()
        );
        configMap.put(configName, newConfig);
    }

    @FXML
    private void onSimulationStartClicked() throws Exception { // throws co?
        int width = Integer.parseInt(mapWidthField.getText());
        int height = Integer.parseInt(mapHeightField.getText());
        int plantAmount = Integer.parseInt(plantAmountField.getText());
        int animalAmount = Integer.parseInt(animalAmountField.getText());
        int geneLength = Integer.parseInt(geneLengthField.getText());
        int startingEnergy = Integer.parseInt(startingEnergyField.getText());
        int energyToHealthy = Integer.parseInt(energyToHealthyField.getText());
        int energyToBirth = Integer.parseInt(energyToBirthField.getText());
        int energyFromPlant = Integer.parseInt(energyFromPlantField.getText());
        boolean deadBody = deadBodyField.getValue();
        boolean swap = swapField.getValue();
        int plantPerDay = Integer.parseInt(plantPerDayField.getText());
        Earth map = new Earth(width, height, plantAmount, animalAmount, geneLength, startingEnergy, energyToHealthy, energyToBirth, energyFromPlant, deadBody, swap);

//        movementDescriptionLabel.setText("Simulation started with moves: ");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("mapView.fxml"));
            BorderPane viewRoot = loader.load();

            SimulationWindowController controller = loader.getController();
            map.addObserver(controller);


            Simulation simulation = new Simulation(plantPerDay, map);
            controller.startSimulation(simulation);
            Stage primaryStage = new Stage();
            var scene = new Scene(viewRoot);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Simulation");
            primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
            primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // czy to dobry wybór?
        }
    }

    private static class Config { // czy to nie powinien być rekord?
        int mapWidth, mapHeight, plantAmount, animalAmount, geneLength;
        int startingEnergy, energyToHealthy, energyToBirth, energyFromPlant, plantPerDay;
        boolean deadBody, swap;

        Config(int mapWidth, int mapHeight, int plantAmount, int animalAmount, int geneLength,
               int startingEnergy, int energyToHealthy, int energyToBirth, int energyFromPlant, int plantPerDay,
               boolean deadBody, boolean swap) {
            this.mapWidth = mapWidth;
            this.mapHeight = mapHeight;
            this.plantAmount = plantAmount;
            this.animalAmount = animalAmount;
            this.geneLength = geneLength;
            this.startingEnergy = startingEnergy;
            this.energyToHealthy = energyToHealthy;
            this.energyToBirth = energyToBirth;
            this.energyFromPlant = energyFromPlant;
            this.plantPerDay = plantPerDay;
            this.deadBody = deadBody;
            this.swap = swap;
        }
    }
}