package hu.hj.terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TerrainGenerator {
    private static final Random RANDOM = new Random();
    private final int size;
    private final Terrain terrain;

    public TerrainGenerator(int size) {
        this.size = size;
        this.terrain = new Terrain(size);
        generateRandomTerrain();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    private void generateRandomTerrain() {
        generateHills();
        GridLocation[] minMax = TerrainUtilities.getMinAndMaxLocationOfTerrain(terrain);
        normalize(minMax[0].getHeight(), minMax[1].getHeight());
    }

    private void generateHills() {
        List<GridLocation> hills = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            generateHill(hills);
        }
    }

    private void generateHill(List<GridLocation> hills) {
        int radius = getRadius();
        GridLocation hill = generateHill(radius, hills);
        updateMap(radius, hill);
    }

    private int getRadius() {
        int radius = (int) (size / Math.log(size));
        if (radius % 2 != 0 || radius <= 1) {
            if (radius - 1 > 1) {
                radius -= 1;
            } else {
                radius += 1;
            }
        }
        return radius;
    }

    private GridLocation generateHill(int radius, List<GridLocation> hills) {
        GridLocation currentLocation;
        do {
            int xCoordinate = RANDOM.nextInt(size + radius) - radius;
            int yCoordinate = RANDOM.nextInt(size + radius) - radius;
            currentLocation = new GridLocation(xCoordinate, yCoordinate);
            if (hills.isEmpty()) {
                hills.add(currentLocation);
                break;
            }
        } while (getClosestDistance(hills, currentLocation) < radius * radius / Math.log(size));
        hills.add(currentLocation);
        return currentLocation;
    }

    private void updateMap(int radius, GridLocation hill) {
        int xMin;
        int xMax;
        int yMin;
        int yMax;

        xMin = Math.max(hill.getXCoordinate() - radius - 1, 0);

        if (hill.getXCoordinate() + radius + 1 >= size) {
            xMax = size - 1;
        } else {
            xMax = hill.getXCoordinate() + radius + 1;
        }

        yMin = Math.max(hill.getYCoordinate() - radius - 1, 0);

        if (hill.getYCoordinate() + radius + 1 >= size) {
            yMax = size - 1;
        } else {
            yMax = hill.getYCoordinate() + radius + 1;
        }

        updateHillEnvironment(hill, radius, xMin, xMax, yMin, yMax);
    }

    private void updateHillEnvironment(GridLocation hill, int radius, int xMin, int xMax, int yMin, int yMax) {
        int radiusSquare = radius * radius;
        int height;
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                GridLocation currentLocation = terrain.getLocationAt(i, j);
                height = radiusSquare - hill.calculateSquareDistance(currentLocation);
                if (height > 0) {
                    currentLocation.setHeight(currentLocation.getHeight() + height);
                }
            }
        }
    }

    private int getClosestDistance(List<GridLocation> hills, GridLocation hillCandidate) {
        int closestDistance = Integer.MAX_VALUE;
        for (GridLocation hill : hills) {
            closestDistance = Math.min(hillCandidate.calculateSquareDistance(hill), closestDistance);
        }
        return closestDistance;
    }

    private void normalize(int min, int max) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridLocation currentLocation = terrain.getLocationAt(i, j);
                double norm = (double) (currentLocation.getHeight() - min) / (max - min);
                if (norm < (double) 1 / size) {
                    norm += (double) 1 / size;
                }
                currentLocation.setHeight((int) (norm * size));
            }
        }
    }
}