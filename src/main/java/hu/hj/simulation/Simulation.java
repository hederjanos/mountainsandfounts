package hu.hj.simulation;

import hu.hj.io.Printer;
import hu.hj.terrain.Direction;
import hu.hj.terrain.Location;
import hu.hj.terrain.Terrain;
import hu.hj.terrain.TerrainUtilities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class Simulation {

    protected final Terrain terrain;
    protected final int size;
    protected Set<Location> floodedArea;
    protected Set<Location> neighbourhood;
    protected List<Terrain> simulationSteps;

    protected Simulation(Terrain terrain) {
        this.terrain = terrain;
        this.size = terrain.getSize();
        this.simulationSteps = new LinkedList<>();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    protected void initializeSimulation() {
        Location minLocation = TerrainUtilities.getMinAndMaxLocationOfTerrain(terrain)[0];

        floodedArea = new HashSet<>();
        minLocation.setFlooded(true);
        floodedArea.add(minLocation);

        neighbourhood = new HashSet<>();
        for (Location location : floodedArea) {
            Set<Direction> directions = location.getNeighbourDirections();
            for (Direction direction : directions) {
                int x = location.getXCoordinate() + direction.getX();
                int y = location.getYCoordinate() + direction.getY();
                neighbourhood.add(terrain.getLocationAt(x, y));
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
        for (Location location : floodedArea) {
            if (location.isFlooded() && location.getHeight() < minHeightOfNeighbourhood) {
                location.setHeight(location.getHeight() + 1);
                isRaised = true;
            }
        }
        return isRaised;
    }

    protected void mergeFloodedAreaAndNeighbourhood(int minHeightOfFloodedArea, int minHeightOfNeighbourhood) {
        Set<Location> addToNeighbourhood = new HashSet<>();
        Set<Location> addToFloodedArea = new HashSet<>();
        for (Location location : neighbourhood) {
            if (!location.isFlooded() && ((location.getHeight() < minHeightOfFloodedArea) ||
                                          (location.getHeight() == minHeightOfFloodedArea && minHeightOfFloodedArea == minHeightOfNeighbourhood))) {
                location.setFlooded(true);
                addToFloodedArea.add(location);
                Set<Direction> directions = location.getNeighbourDirections();
                for (Direction direction : directions) {
                    int x = location.getXCoordinate() + direction.getX();
                    int y = location.getYCoordinate() + direction.getY();
                    Location neighbourLocation = terrain.getLocationAt(x, y);
                    if (!neighbourLocation.isFlooded()) {
                        addToNeighbourhood.add(terrain.getLocationAt(x, y));
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

    protected int getMinHeightOfLocationSet(Set<Location> locations) {
        int minHeight = Integer.MAX_VALUE;
        for (Location location : locations) {
            minHeight = Math.min(location.getHeight(), minHeight);
        }
        return minHeight;
    }

    public boolean isTerrainFullyFlooded() {
        boolean isFlooded = true;
        if (floodedArea.size() != Math.pow(terrain.getSize(), 2)) {
            return false;
        }
        for (Location location : floodedArea) {
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