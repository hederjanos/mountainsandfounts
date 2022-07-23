package hu.hj.simulation;

import hu.hj.gif.AnimatedGIF;
import hu.hj.gif.Frame;
import hu.hj.io.Printer;
import hu.hj.terrain.GridLocation;
import hu.hj.terrain.Terrain;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SimulationGIF extends Simulation {

    private static final double PROGRESS_INCREMENT = 0.1;
    private static final int RGB_MAX = 255;
    private static final int RED = 150;
    private static final int GREEN = 225;

    private final Map<Integer, Color> colorMapOfTerrain = new HashMap<>();
    private final Map<Integer, Color> colorMapOfFloodedArea = new HashMap<>();
    private final AnimatedGIF animatedGIF;

    public SimulationGIF(Terrain terrain) {
        super(terrain);
        this.animatedGIF = new AnimatedGIF();
        initializeColorMaps();
    }

    private void initializeColorMaps() {
        int rgbMax = RGB_MAX;
        int decrement = (int) Math.floor((double) rgbMax / terrain.getSize());

        for (int i = 1; i <= terrain.getSize(); i++) {
            colorMapOfTerrain.put(i, new Color(rgbMax, rgbMax, rgbMax));
            rgbMax -= decrement;
        }

        rgbMax = RGB_MAX;
        int redComponent = RED;
        int greenComponent = GREEN;
        int blueComponent = rgbMax;
        int redDecrement = (int) Math.floor((double) redComponent / terrain.getSize());
        int greenDecrement = (int) Math.floor((double) greenComponent / terrain.getSize());
        for (int i = 1; i <= terrain.getSize(); i++) {
            colorMapOfFloodedArea.put(i, new Color(redComponent, greenComponent, blueComponent));
            redComponent -= redDecrement;
            greenComponent -= greenDecrement;
        }
    }

    @Override
    public void visualize(Printer printer) {
        double specificIncrement = simulationSteps.size() * PROGRESS_INCREMENT;
        double origIncrement = specificIncrement;
        for (int i = 0; i < simulationSteps.size(); i++) {
            createOneFrame(simulationSteps.get(i));
            if (i - PROGRESS_INCREMENT * 100 < origIncrement && i + PROGRESS_INCREMENT * 100 > origIncrement) {
                printer.print(String.format("%.0f%%----", i / specificIncrement * PROGRESS_INCREMENT * 100));
                origIncrement += specificIncrement;
            }
        }
        closeVisualization();
    }

    private void createOneFrame(Terrain terrain) {
        int size = terrain.getSize();
        Frame frame = new Frame(size, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Color color;
                GridLocation location = terrain.getLocationAt(i, j);
                if (location.isFlooded()) {
                    color = colorMapOfFloodedArea.get(location.getHeight());
                } else {
                    color = colorMapOfTerrain.get(location.getHeight());
                }
                frame.printSquare(j, i, color);
            }
        }
        this.animatedGIF.addFrame(frame);
    }

    private void closeVisualization() {
        animatedGIF.finish();
    }

    public AnimatedGIF getAnimatedGIF() {
        return animatedGIF;
    }
}