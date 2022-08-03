package hu.hj.terrain;

import java.util.Objects;

public class GridCell {

    private int x;
    private int y;
    private int height;
    private boolean isFlooded;
    private GridCell[] neighbours;

    public GridCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighbours = new GridCell[Direction.values().length];
    }

    public GridCell(GridCell cell, boolean withNeighbours) {
        setAttributes(cell);
        if (withNeighbours) {
            setNeighbours(cell.getNeighbours());
        } else {
            setNeighbours(new GridCell[Direction.values().length]);
        }
    }

    private void setAttributes(GridCell cell) {
        setX(cell.getX());
        setY(cell.getY());
        setHeight(cell.getHeight());
        setFlooded(cell.isFlooded());
    }

    public int calculateSquareDistance(GridCell cell) {
        return (x - cell.getX()) * (x - cell.getX()) +
                (y - cell.getY()) * (y - cell.getY());
    }

    protected GridCell copy(boolean withNeighbours) {
        return new GridCell(this, withNeighbours);
    }

    public void setNeighbour(GridCell cell, Direction direction) {
        this.neighbours[direction.ordinal()] = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridCell cell = (GridCell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "GridCell{" +
                "x=" + x +
                ", y=" + y +
                ", height=" + height +
                ", isFlooded=" + isFlooded +
                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public GridCell[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(GridCell[] neighbours) {
        this.neighbours = neighbours;
    }
}