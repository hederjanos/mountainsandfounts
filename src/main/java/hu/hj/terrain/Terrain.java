package hu.hj.terrain;

public class Terrain {
    private Location[][] locations;
    private int size;

    public Terrain() {
    }

    public Terrain(int size) {
        this.size = size;
        this.locations = new Location[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                locations[i][j] = new Location(i, j);
                locations[i][j].setNeighbourDirections(size);
            }
        }
    }

    /**
     * For testing purposes
     */
    public Terrain(int[][] myArray) {
        size = myArray.length;
        locations = new Location[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Location currentLocation = new Location(i, j);
                currentLocation.setHeight(myArray[i][j]);
                currentLocation.setNeighbourDirections(size);
                locations[i][j] = currentLocation;
            }
        }
    }

    public Terrain copyTerrain() {
        Terrain copyOfTerrain = new Terrain();
        copyOfTerrain.size = size;
        copyOfTerrain.locations = new Location[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Location currentLocation = new Location(i, j);
                currentLocation.setHeight(this.getLocationAt(i, j).getHeight());
                currentLocation.setNeighbourDirections(size);
                if (this.locations[i][j].isFlooded()) {
                    currentLocation.setFlooded(true);
                }
                copyOfTerrain.locations[i][j] = currentLocation;
            }
        }
        return copyOfTerrain;
    }

    public int getSize() {
        return size;
    }

    public Location getLocationAt(int x, int y) {
        return locations[x][y];
    }

    public Location[][] getLocations() {
        return locations;
    }
}