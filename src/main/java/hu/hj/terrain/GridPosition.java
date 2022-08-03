package hu.hj.terrain;

import java.util.Objects;

public class GridPosition {

    private int rowIndex;
    private int columnIndex;

    public GridPosition(int row, int col) {
        this.rowIndex = row;
        this.columnIndex = col;
    }

    public GridPosition(GridPosition position) {
        setRowIndex(position.getRowIndex());
        setColumnIndex(position.getColumnIndex());
    }

    public GridPosition copy() {
        return new GridPosition(this);
    }

    public int calculateSquareDistance(GridPosition position) {
        return (rowIndex - position.getRowIndex()) * (rowIndex - position.getRowIndex()) +
                (columnIndex - position.getColumnIndex()) * (columnIndex - position.getColumnIndex());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridPosition that = (GridPosition) o;
        return rowIndex == that.rowIndex && columnIndex == that.columnIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }

    @Override
    public String toString() {
        return "GridPosition{" +
                "row=" + rowIndex +
                ", col=" + columnIndex +
                "}";
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int row) {
        this.rowIndex = row;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int col) {
        this.columnIndex = col;
    }

}
