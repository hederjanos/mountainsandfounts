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
                GridCell currentCell = new GridCell(i, j);
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
                GridCell cell = new GridCell(i, j);
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
            int newRow = cell.getPosition().getRowIndex() + direction.getX();
            int newCol = cell.getPosition().getColumnIndex() + direction.getY();
            if (areIndexesInBounds(newRow, newCol)) {
                int indexOfNeighbourCell = getCellIndexInCells(newRow, newCol);
                GridCell neighbourCell = findCellByIndex(indexOfNeighbourCell);
                if (neighbourCell != null) {
                    cell.setNeighbour(neighbourCell, direction);
                    neighbourCell.setNeighbour(cell, direction.getOppositeDirection());
                }
            }
        }
    }

    private int getCellIndexInCells(int row, int col) {
        return row * size + col;
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

    private boolean areIndexesInBounds(int row, int col) {
        return row < size && row >= 0 && col < size && col >= 0;
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
                GridCell currentCell = findCellByIndex(getCellIndexInCells(i, j));
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