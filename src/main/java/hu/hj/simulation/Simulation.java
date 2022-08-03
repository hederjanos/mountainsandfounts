package hu.hj.simulation;

import hu.hj.io.Printer;
import hu.hj.terrain.GridCell;
import hu.hj.terrain.Terrain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class Simulation {

    protected final Terrain terrain;
    protected Set<GridCell> floodedArea;
    protected Set<GridCell> neighbourhood;
    protected List<Terrain> simulationSteps;

    protected Simulation(Terrain terrain) {
        this.terrain = terrain;
        this.simulationSteps = new LinkedList<>();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    protected void initializeSimulation() {
        GridCell minCell = terrain.getMinAndMaxHeightCell()[0];
        GridCell burstCell = terrain.getCellAt(minCell.getPosition().getRowIndex(), minCell.getPosition().getColumnIndex());

        floodedArea = new HashSet<>();
        burstCell.setFlooded(true);
        floodedArea.add(burstCell);

        neighbourhood = new HashSet<>();
        for (GridCell cell : floodedArea) {
            for (GridCell neighbourCell : cell.getNeighbours()) {
                if (neighbourCell != null) {
                    neighbourhood.add(neighbourCell);
                }
            }
        }
    }

    public void flood() {
        simulationSteps.add(terrain.copyTerrain());
        initializeSimulation();
        simulationSteps.add(terrain.copyTerrain());
        int minHeightOfFloodedArea = getMinHeightOfCellSet(floodedArea);
        int minHeightOfNeighbourhood = getMinHeightOfCellSet(neighbourhood);

        while (!isTerrainFullyFlooded()) {
            if (isRaised(minHeightOfNeighbourhood)) {
                minHeightOfFloodedArea = getMinHeightOfCellSet(floodedArea);
                simulationSteps.add(terrain.copyTerrain());
            }
            mergeFloodedAreaAndNeighbourhood(minHeightOfFloodedArea, minHeightOfNeighbourhood);
            minHeightOfNeighbourhood = getMinHeightOfCellSet(neighbourhood);
            simulationSteps.add(terrain.copyTerrain());
        }
    }

    /**
     * For testing purposes to control simulation steps
     */
    public void flood(int numberOfSimulations) {
        initializeSimulation();
        int minHeightOfFloodedArea = getMinHeightOfCellSet(floodedArea);
        int minHeightOfNeighbourhood = getMinHeightOfCellSet(neighbourhood);
        int counter = 0;
        while (true) {
            if (isRaised(minHeightOfNeighbourhood)) {
                minHeightOfFloodedArea = getMinHeightOfCellSet(floodedArea);
                counter++;
                if (counter == numberOfSimulations) {
                    break;
                }
            }
            mergeFloodedAreaAndNeighbourhood(minHeightOfFloodedArea, minHeightOfNeighbourhood);
            minHeightOfNeighbourhood = getMinHeightOfCellSet(neighbourhood);
            counter++;
            if (counter == numberOfSimulations) {
                break;
            }
        }
    }

    protected boolean isRaised(int minHeightOfNeighbourhood) {
        boolean isRaised = false;
        for (GridCell cell : floodedArea) {
            if (cell.isFlooded() && cell.getHeight() < minHeightOfNeighbourhood) {
                cell.setHeight(cell.getHeight() + 1);
                isRaised = true;
            }
        }
        return isRaised;
    }

    protected void mergeFloodedAreaAndNeighbourhood(int minHeightOfFloodedArea, int minHeightOfNeighbourhood) {
        Set<GridCell> addToNeighbourhood = new HashSet<>();
        Set<GridCell> addToFloodedArea = new HashSet<>();
        for (GridCell cell : neighbourhood) {
            if (!cell.isFlooded() && ((cell.getHeight() < minHeightOfFloodedArea) ||
                    (cell.getHeight() == minHeightOfFloodedArea && minHeightOfFloodedArea == minHeightOfNeighbourhood))) {
                cell.setFlooded(true);
                addToFloodedArea.add(cell);
                for (GridCell neighbourCell : cell.getNeighbours()) {
                    if (neighbourCell != null && !neighbourCell.isFlooded()) {
                        addToNeighbourhood.add(neighbourCell);
                    }
                }
            }
        }
        floodedArea.addAll(addToFloodedArea);
        neighbourhood.removeAll(addToFloodedArea);
        // if a recently flooded Cell has any non-flooded neighbours which currently floodable, that neighbour
        // is added to addToFloodedArea and addToNeighbourhood too, the intersection must be removed from
        // addToNeighbourhood before add it to neighbourhood
        addToFloodedArea.retainAll(addToNeighbourhood);
        addToNeighbourhood.removeAll(addToFloodedArea);

        neighbourhood.addAll(addToNeighbourhood);
    }

    protected int getMinHeightOfCellSet(Set<GridCell> cells) {
        int minHeight = Integer.MAX_VALUE;
        for (GridCell cell : cells) {
            minHeight = Math.min(cell.getHeight(), minHeight);
        }
        return minHeight;
    }

    public boolean isTerrainFullyFlooded() {
        boolean isFlooded = true;
        if (floodedArea.size() != Math.pow(terrain.getSize(), 2)) {
            return false;
        }
        for (GridCell cell : floodedArea) {
            if (cell.getHeight() != terrain.getSize()) {
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