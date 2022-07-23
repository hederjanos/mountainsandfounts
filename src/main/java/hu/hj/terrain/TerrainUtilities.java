package hu.hj.terrain;

public class TerrainUtilities {

    private TerrainUtilities() {
    }

    public static GridLocation[] getMinAndMaxLocationOfTerrain(Terrain terrain) {
        GridLocation min = terrain.getLocationAt(0, 0);
        GridLocation max = terrain.getLocationAt(0, 0);
        int i = 0;
        while (i < terrain.getSize()) {
            for (int j = 0; j < terrain.getSize(); j++) {
                GridLocation currentLocation = terrain.getLocationAt(i, j);
                if (currentLocation.getHeight() < min.getHeight()) {
                    min = currentLocation;
                }
                if (currentLocation.getHeight() > max.getHeight()) {
                    max = currentLocation;
                }
            }
            i++;
        }
        return new GridLocation[]{min, max};
    }

    public static String terrainToString(Terrain terrain) {
        StringBuilder terrainBuilder = new StringBuilder();
        for (GridLocation[] locations : terrain.getLocations()) {
            for (GridLocation location : locations) {
                if (location.isFlooded()) {
                    terrainBuilder.append("-");
                } else {
                    terrainBuilder.append(" ");
                }
                terrainBuilder.append(location.getHeight()).append("\t");
            }
            terrainBuilder.deleteCharAt(terrainBuilder.length() - 1);
            terrainBuilder.append("\n");
        }
        return terrainBuilder.toString();
    }
}