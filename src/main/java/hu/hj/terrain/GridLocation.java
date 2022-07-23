package hu.hj.terrain;

import java.util.Objects;

public class GridLocation {
    private final int rowIndex;
    private final int columnIndex;
    private int height;
    private boolean isFlooded;
    private final GridLocation[] neighbours;

    public GridLocation(int x, int y) {
        this.rowIndex = x;
        this.columnIndex = y;
        this.neighbours = new GridLocation[Direction.values().length];
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isFlooded() {
        return isFlooded;
    }

    public void setFlooded(boolean flooded) {
        isFlooded = flooded;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public GridLocation[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbour(GridLocation neighbourLocation, Direction direction) {
        this.neighbours[direction.ordinal()] = neighbourLocation;
    }

    public int calculateSquareDistance(GridLocation location) {
        return (rowIndex - location.getRowIndex()) * (rowIndex - location.getRowIndex()) +
                (columnIndex - location.getColumnIndex()) * (columnIndex - location.getColumnIndex());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridLocation location = (GridLocation) o;
        return rowIndex == location.getRowIndex() && columnIndex == location.getColumnIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + rowIndex +
                ", y=" + columnIndex +
                '}';
    }

}