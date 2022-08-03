package hu.hj.terrain;

import java.util.Objects;

public class GridCell {

    private GridPosition position;
    private int height;
    private boolean isFlooded;
    private GridCell[] neighbours;

    public GridCell(int row, int col) {
        this.position = new GridPosition(row, col);
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
        setPosition(cell.getPosition().copy());
        setHeight(cell.getHeight());
        setFlooded(cell.isFlooded());
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
        return cell.getPosition().equals(((GridCell) o).getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "GridCell{" +
                "position=" + position +
                ", height=" + height +
                ", isFlooded=" + isFlooded +
                '}';
    }

    public GridPosition getPosition() {
        return position;
    }

    public void setPosition(GridPosition position) {
        this.position = position;
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