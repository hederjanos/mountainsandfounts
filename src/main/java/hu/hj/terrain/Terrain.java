package hu.hj.terrain;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private boolean fourWayDirection;
    private int width;
    private int height;
    private List<GridLocation> locations;

    public Terrain() {
    }

    public Terrain(int size) {
        this(size, size, true);
    }

    public Terrain(int size, boolean fourWayDirection) {
        this(size, size, fourWayDirection);
    }

    public Terrain(int width, int height, boolean fourWayDirection) {
        this.width = width;
        this.height = height;
        this.fourWayDirection = fourWayDirection;
        this.locations = new ArrayList<>();
        initialize();
    }

    /**
     * For testing purposes with n*n array
     */
    public Terrain(int[][] myArray) {
        height = myArray.length;
        width = myArray[0].length;
        locations = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                GridLocation currentLocation = new GridLocation(i, j);
                currentLocation.setHeight(myArray[i][j]);
                locations.add(currentLocation);
            }
        }
        connectLocations();
    }

    private void initialize() {
        createLocations();
        connectLocations();
    }

    private void createLocations() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                locations.add(new GridLocation(i, j));
            }
        }
    }

    private void connectLocations() {
        for (GridLocation location : locations) {
            setNeighbours(location);
        }
    }

    private void setNeighbours(GridLocation location) {
        for (Direction direction : Direction.values()) {
            if (fourWayDirection && direction.ordinal() % 2 != 0) {
                continue;
            }
            int newRowIndex = location.getRowIndex() + direction.getX();
            int newColumnIndex = location.getColumnIndex() + direction.getY();
            if (areIndexesInBounds(newRowIndex, newColumnIndex)) {
                int indexOfNeighbourLocation = getLocationIndexInLocations(newRowIndex, newColumnIndex);
                GridLocation neighbourLocation = locations.get(indexOfNeighbourLocation);
                location.setNeighbour(neighbourLocation, direction);
            }
        }
    }

    private int getLocationIndexInLocations(int row, int col) {
        return row * width + col;
    }

    private boolean areIndexesInBounds(int row, int col) {
        return row < height && row >= 0 && col < width && col >= 0;
    }

    public Terrain copyTerrain() {
        Terrain copyOfTerrain = new Terrain();
        copyOfTerrain.setWidth(width);
        copyOfTerrain.setHeight(height);
        List<GridLocation> copyOfLocations = new ArrayList<>();
        for (GridLocation location : getLocations()) {
            GridLocation newLocation = new GridLocation(location.getRowIndex(), location.getColumnIndex());
            newLocation.setHeight(location.getHeight());
            if (location.isFlooded()) {
                newLocation.setFlooded(true);
            }
            copyOfLocations.add(newLocation);
        }
        copyOfTerrain.setLocations(copyOfLocations);
        copyOfTerrain.connectLocations();
        return copyOfTerrain;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GridLocation getLocationAt(int row, int col) {
        return locations.get(getLocationIndexInLocations(row, col));
    }

    public List<GridLocation> getLocations() {
        return locations;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLocations(List<GridLocation> locations) {
        this.locations = locations;
    }

    public boolean isFourWayDirection() {
        return fourWayDirection;
    }

    public void setFourWayDirection(boolean fourWayDirection) {
        this.fourWayDirection = fourWayDirection;
    }
}