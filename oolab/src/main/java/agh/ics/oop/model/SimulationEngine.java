package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> simulationThreads;
    private ExecutorService threadPool;
    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
        this.simulationThreads = new ArrayList<>();
        this.threadPool = Executors.newFixedThreadPool(4);
    }
    public void runSync(){
        for (Simulation simulation : simulations) {
            System.out.println("Starting simulation");
            simulation.run();
            System.out.println("Simulation completed");
            System.out.println();
        }
    }
    public void runAsync(){
        this.simulationThreads.clear();
        for (Simulation simulation : simulations) {
            System.out.println("Starting simulation");
            Thread thread = new Thread(simulation);
            simulationThreads.add(thread);
            thread.start();
        }
    }
//    public void awaitSimulationsEnd() {
//        for (Thread thread : simulationThreads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                System.err.println("Thread interrupted while waiting: " + e.getMessage());
//            }
//        }
//    }
    public void runAsyncInThreadPool() {
        for (Simulation simulation : simulations) {
            threadPool.submit(simulation);
        }
    }
    public void awaitSimulationsEnd() {
        if (threadPool != null) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
                System.err.println("Thread pool interrupted while waiting.");
            }
        }
    }

}
