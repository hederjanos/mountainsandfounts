package hu.hj.terrain;

import java.util.ArrayList;
import java.util.List;

public class Terrain {

    private boolean fourWayDirection;
    private int size;
    private List<GridLocation> locations;

    public Terrain() {
    }

    public Terrain(int size) {
        this(size, true);
    }

    public Terrain(int size, boolean fourWayDirection) {
        this.size = size;
        this.fourWayDirection = fourWayDirection;
        this.locations = new ArrayList<>();
        initialize();
    }

    /**
     * For testing purposes with n*n array
     */
    public Terrain(int[][] myArray) {
        size = myArray.length;
        this.fourWayDirection = true;
        locations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
        return row * size + col;
    }

    private boolean areIndexesInBounds(int row, int col) {
        return row < size && row >= 0 && col < size && col >= 0;
    }

    public Terrain copyTerrain() {
        Terrain copyOfTerrain = new Terrain();
        copyOfTerrain.setSize(size);
        copyOfTerrain.setFourWayDirection(fourWayDirection);
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

    public GridLocation[] getMinAndMaxHeightLocation() {
        GridLocation min = getLocationAt(0, 0).copy();
        GridLocation max = getLocationAt(0, 0).copy();
        for (GridLocation location : locations) {
            if (location.getHeight() < min.getHeight()) {
                min = location.copy();
            }
            if (location.getHeight() > max.getHeight()) {
                max = location.copy();
            }
        }
        return new GridLocation[]{min, max};
    }

    public GridLocation getLocationAt(int row, int col) {
        return locations.get(getLocationIndexInLocations(row, col));
    }

    @Override
    public String toString() {
        StringBuilder terrainBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridLocation currentLocation = locations.get(getLocationIndexInLocations(i, j));
                if (currentLocation.isFlooded()) {
                    terrainBuilder.append("-");
                } else {
                    terrainBuilder.append(" ");
                }
                terrainBuilder.append(currentLocation.getHeight()).append("\t");
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

    public List<GridLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<GridLocation> locations) {
        this.locations = locations;
    }

}