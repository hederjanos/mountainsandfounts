package hu.hj.simulation;

import hu.hj.io.Printer;
import hu.hj.terrain.Terrain;
import hu.hj.terrain.TerrainUtilities;

public class SimulationConsole extends Simulation {

    public SimulationConsole(Terrain terrain) {
        super(terrain);
    }

    @Override
    public void visualize(Printer printer) {
        for (Terrain simulationStep : simulationSteps) {
            printer.println(TerrainUtilities.terrainToString(simulationStep));
        }
    }
}