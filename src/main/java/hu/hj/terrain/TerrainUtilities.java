package hu.hj.terrain;

public class TerrainUtilities {

    private TerrainUtilities() {
    }

    public static Location[] getMinAndMaxLocationOfTerrain(Terrain terrain) {
        Location min = terrain.getLocationAt(0, 0);
        Location max = terrain.getLocationAt(0, 0);
        int i = 0;
        while (i < terrain.getSize()) {
            for (int j = 0; j < terrain.getSize(); j++) {
                Location currentLocation = terrain.getLocationAt(i, j);
                if (currentLocation.getHeight() < min.getHeight()) {
                    min = currentLocation;
                }
                if (currentLocation.getHeight() > max.getHeight()) {
                    max = currentLocation;
                }
            }
            i++;
        }
        return new Location[]{min, max};
    }

    public static String terrainToString(Terrain terrain) {
        StringBuilder terrainBuilder = new StringBuilder();
        for (Location[] locations : terrain.getLocations()) {
            for (Location location : locations) {
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