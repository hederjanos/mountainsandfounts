package hu.hj.terrain;

import java.util.Objects;

public class GridLocation {
    private int rowIndex;
    private int columnIndex;
    private int height;
    private boolean isFlooded;
    private GridLocation[] neighbours;

    public GridLocation(int x, int y) {
        this.rowIndex = x;
        this.columnIndex = y;
        this.neighbours = new GridLocation[Direction.values().length];
    }

    protected GridLocation(GridLocation gridLocation) {
        setRowIndex(gridLocation.getRowIndex());
        setColumnIndex(gridLocation.getColumnIndex());
        setHeight(gridLocation.getHeight());
        setFlooded(gridLocation.isFlooded());
        setNeighbours(gridLocation.getNeighbours());
    }

    public GridLocation copy() {
        return new GridLocation(this);
    }

    public int calculateSquareDistance(GridLocation location) {
        return (rowIndex - location.getRowIndex()) * (rowIndex - location.getRowIndex()) +
                (columnIndex - location.getColumnIndex()) * (columnIndex - location.getColumnIndex());
    }

    public void setNeighbour(GridLocation neighbourLocation, Direction direction) {
        this.neighbours[direction.ordinal()] = neighbourLocation;
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
        return "GridLocation{" +
                "row=" + rowIndex +
                ", col=" + columnIndex +
                ", height=" + height +
                ", flooded" + isFlooded +
                "}";
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
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

    public GridLocation[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(GridLocation[] neighbours) {
        this.neighbours = neighbours;
    }
}