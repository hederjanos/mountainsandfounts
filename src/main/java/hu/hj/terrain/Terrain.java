package hu.hj.terrain;

import java.util.ArrayList;
import java.util.List;

public class Terrain {

    private boolean fourWayDirection;
    private int size;
    private List<GridCell> cells;

    public Terrain() {
    }

    public Terrain(int size) {
        this(size, true);
    }

    public Terrain(int size, boolean fourWayDirection) {
        this.size = size;
        this.fourWayDirection = fourWayDirection;
        this.cells = new ArrayList<>();
        initialize();
    }

    /**
     * For testing purposes with n*n array
     */
    public Terrain(int[][] myArray) {
        size = myArray.length;
        this.fourWayDirection = true;
        cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridCell currentCell = new GridCell(j, i);
                currentCell.setHeight(myArray[i][j]);
                setNeighbours(currentCell);
                cells.add(currentCell);
            }
        }
    }

    private void initialize() {
        createCells();
    }

    private void createCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridCell cell = new GridCell(j, i);
                setNeighbours(cell);
                cells.add(cell);
            }
        }
    }

    private void setNeighbours(GridCell cell) {
        for (Direction direction : Direction.values()) {
            if (fourWayDirection && direction.ordinal() % 2 != 0) {
                continue;
            }
            int newX = cell.getPosition().getX() + direction.getX();
            int newY = cell.getPosition().getY() + direction.getY();
            if (areCoordinatesInBounds(newX, newY)) {
                int indexOfNeighbourCell = getCellIndexInCells(newX, newY);
                GridCell neighbourCell = findCellByIndex(indexOfNeighbourCell);
                if (neighbourCell != null) {
                    cell.setNeighbour(neighbourCell, direction);
                    neighbourCell.setNeighbour(cell, direction.getOppositeDirection());
                }
            }
        }
    }

    private int getCellIndexInCells(int x, int y) {
        return x + y * size;
    }

    private GridCell findCellByIndex(int indexOfNeighbourCell) {
        GridCell cell;
        try {
            cell = cells.get(indexOfNeighbourCell);
        } catch (IndexOutOfBoundsException exception) {
            cell = null;
        }
        return cell;
    }

    private boolean areCoordinatesInBounds(int x, int y) {
        return x < size && x >= 0 && y < size && y >= 0;
    }

    public Terrain copyTerrain() {
        Terrain copyOfTerrain = new Terrain();
        copyOfTerrain.setSize(size);
        copyOfTerrain.setFourWayDirection(fourWayDirection);
        List<GridCell> copyOfCells = new ArrayList<>();
        copyOfTerrain.setCells(copyOfCells);
        for (GridCell cell : cells) {
            GridCell newCell = cell.copy(false);
            copyOfCells.add(newCell);
            copyOfTerrain.setNeighbours(newCell);
        }
        return copyOfTerrain;
    }

    public GridCell[] getMinAndMaxHeightCell() {
        GridCell min = getCellAt(0, 0).copy(false);
        GridCell max = getCellAt(0, 0).copy(false);
        for (GridCell cell : cells) {
            if (cell.getHeight() < min.getHeight()) {
                min = cell.copy(false);
            }
            if (cell.getHeight() > max.getHeight()) {
                max = cell.copy(false);
            }
        }
        return new GridCell[]{min, max};
    }

    public GridCell getCellAt(int row, int col) {
        return cells.get(getCellIndexInCells(row, col));
    }

    @Override
    public String toString() {
        StringBuilder terrainBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridCell currentCell = findCellByIndex(getCellIndexInCells(j, i));
                if (currentCell != null) {
                    if (currentCell.isFlooded()) {
                        terrainBuilder.append("-");
                    } else {
                        terrainBuilder.append(" ");
                    }
                    terrainBuilder.append(currentCell.getHeight()).append("\t");
                }
            }
            terrainBuilder.deleteCharAt(terrainBuilder.length() - 1);
            terrainBuilder.append("\n");
        }
        return terrainBuilder.toString();
    }

    public void setFourWayDirection(boolean fourWayDirection) {
        this.fourWayDirection = fourWayDirection;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<GridCell> getCells() {
        return cells;
    }

    public void setCells(List<GridCell> cells) {
        this.cells = cells;
    }

}