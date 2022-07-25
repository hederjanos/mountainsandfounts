package hu.hj.simulation;

import hu.hj.io.Printer;
import hu.hj.terrain.GridLocation;
import hu.hj.terrain.Terrain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class Simulation {

    protected final Terrain terrain;
    protected Set<GridLocation> floodedArea;
    protected Set<GridLocation> neighbourhood;
    protected List<Terrain> simulationSteps;

    protected Simulation(Terrain terrain) {
        this.terrain = terrain;
        this.simulationSteps = new LinkedList<>();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    protected void initializeSimulation() {
        GridLocation minLocation = terrain.getMinAndMaxHeightLocation()[0];
        GridLocation burstLocation = terrain.getLocationAt(minLocation.getRowIndex(), minLocation.getColumnIndex());

        floodedArea = new HashSet<>();
        burstLocation.setFlooded(true);
        floodedArea.add(burstLocation);

        neighbourhood = new HashSet<>();
        for (GridLocation location : floodedArea) {
            for (GridLocation neighbour : location.getNeighbours()) {
                if (neighbour != null) {
                    neighbourhood.add(neighbour);
                }
            }
        }
    }

    public void flood() {
        simulationSteps.add(terrain.copyTerrain());
        initializeSimulation();
        simulationSteps.add(terrain.copyTerrain());
        int minHeightOfFloodedArea = getMinHeightOfLocationSet(floodedArea);
        int minHeightOfNeighbourhood = getMinHeightOfLocationSet(neighbourhood);

        while (!isTerrainFullyFlooded()) {
            if (isRaised(minHeightOfNeighbourhood)) {
                minHeightOfFloodedArea = getMinHeightOfLocationSet(floodedArea);
                simulationSteps.add(terrain.copyTerrain());
            }
            mergeFloodedAreaAndNeighbourhood(minHeightOfFloodedArea, minHeightOfNeighbourhood);
            minHeightOfNeighbourhood = getMinHeightOfLocationSet(neighbourhood);
            simulationSteps.add(terrain.copyTerrain());
        }
    }

    /**
     * For testing purposes to control simulation steps
     */
    public void flood(int numberOfSimulations) {
        initializeSimulation();
        int minHeightOfFloodedArea = getMinHeightOfLocationSet(floodedArea);
        int minHeightOfNeighbourhood = getMinHeightOfLocationSet(neighbourhood);
        int counter = 0;
        while (true) {
            if (isRaised(minHeightOfNeighbourhood)) {
                minHeightOfFloodedArea = getMinHeightOfLocationSet(floodedArea);
                counter++;
                if (counter == numberOfSimulations) {
                    break;
                }
            }
            mergeFloodedAreaAndNeighbourhood(minHeightOfFloodedArea, minHeightOfNeighbourhood);
            minHeightOfNeighbourhood = getMinHeightOfLocationSet(neighbourhood);
            counter++;
            if (counter == numberOfSimulations) {
                break;
            }
        }
    }

    protected boolean isRaised(int minHeightOfNeighbourhood) {
        boolean isRaised = false;
        for (GridLocation location : floodedArea) {
            if (location.isFlooded() && location.getHeight() < minHeightOfNeighbourhood) {
                location.setHeight(location.getHeight() + 1);
                isRaised = true;
            }
        }
        return isRaised;
    }

    protected void mergeFloodedAreaAndNeighbourhood(int minHeightOfFloodedArea, int minHeightOfNeighbourhood) {
        Set<GridLocation> addToNeighbourhood = new HashSet<>();
        Set<GridLocation> addToFloodedArea = new HashSet<>();
        for (GridLocation location : neighbourhood) {
            if (!location.isFlooded() && ((location.getHeight() < minHeightOfFloodedArea) ||
                    (location.getHeight() == minHeightOfFloodedArea && minHeightOfFloodedArea == minHeightOfNeighbourhood))) {
                location.setFlooded(true);
                addToFloodedArea.add(location);
                GridLocation[] neighbours = location.getNeighbours();
                for (GridLocation neighbourLocation : neighbours) {
                    if (neighbourLocation != null && !neighbourLocation.isFlooded()) {
                        addToNeighbourhood.add(neighbourLocation);
                    }
                }
            }
        }
        floodedArea.addAll(addToFloodedArea);
        neighbourhood.removeAll(addToFloodedArea);
        // if a recently flooded Location has any non-flooded neighbours which currently floodable, that neighbour
        // is added to addToFloodedArea and addToNeighbourhood too, the intersection must be removed from
        // addToNeighbourhood before add it to neighbourhood
        addToFloodedArea.retainAll(addToNeighbourhood);
        addToNeighbourhood.removeAll(addToFloodedArea);

        neighbourhood.addAll(addToNeighbourhood);
    }

    protected int getMinHeightOfLocationSet(Set<GridLocation> locations) {
        int minHeight = Integer.MAX_VALUE;
        for (GridLocation location : locations) {
            minHeight = Math.min(location.getHeight(), minHeight);
        }
        return minHeight;
    }

    public boolean isTerrainFullyFlooded() {
        boolean isFlooded = true;
        if (floodedArea.size() != Math.pow(terrain.getSize(), 2)) {
            return false;
        }
        for (GridLocation location : floodedArea) {
            if (location.getHeight() != terrain.getSize()) {
                isFlooded = false;
                break;
            }
        }
        return isFlooded;
    }

    public int getNumberOfSimulationSteps() {
        return simulationSteps.size();
    }

    public abstract void visualize(Printer printer);

}