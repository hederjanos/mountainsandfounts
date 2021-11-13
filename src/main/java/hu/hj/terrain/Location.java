package hu.hj.terrain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Location {
    private final int xCoordinate;
    private final int yCoordinate;
    private int height;
    private boolean isFlooded;
    private final Set<Direction> neighbours;

    public Location(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.neighbours = new HashSet<>();
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

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public Set<Direction> getNeighbourDirections() {
        return new HashSet<>(neighbours);
    }

    public void setNeighbourDirections(int size) {
        for (Direction direction : Direction.values()) {
            int newX = xCoordinate + direction.getX();
            int newY = yCoordinate + direction.getY();
            if (isValidCoordinate(newX, newY, size)) {
                this.neighbours.add(direction);
            }
            if (direction.equals(Direction.LEFT)) {
                break;
            }
        }
    }

    private static boolean isValidCoordinate(int newX, int newY, int size) {
        return newX < size && newX >= 0 && newY < size && newY >= 0;
    }

    public int calculateSquareDistance(Location location) {
        return (this.xCoordinate - location.getXCoordinate()) *
                (this.xCoordinate - location.getXCoordinate()) +
                (this.yCoordinate - location.getYCoordinate()) *
                        (this.yCoordinate - location.getYCoordinate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location cell = (Location) o;
        return xCoordinate == cell.getXCoordinate() && yCoordinate == cell.getYCoordinate();
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + xCoordinate +
                ", y=" + yCoordinate +
                '}';
    }
}