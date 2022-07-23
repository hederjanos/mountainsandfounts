package hu.hj.terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TerrainGenerator {

    private static final Random RANDOM = new Random();
    private final int size;
    private final int radius;

    public TerrainGenerator(int size) {
        this.size = size;
        this.radius = calculateRadius();
    }

    public Terrain generateTerrain() {
        Terrain terrain = new Terrain(size);
        generateHills(terrain);
        GridLocation[] minMax = terrain.getMinAndMaxLocation();
        normalize(terrain, minMax);
        return terrain;
    }

    private void generateHills(Terrain terrain) {
        List<GridLocation> hills = new ArrayList<>();
        for (int i = 0; i < terrain.getSize(); i++) {
            generateHill(terrain, hills);
        }
    }

    private void generateHill(Terrain terrain, List<GridLocation> hills) {
        GridLocation hill = generateHill(radius, hills);
        updateTerrain(terrain, hill);
    }

    private int calculateRadius() {
        int heuristicRadius = (int) (size / Math.log(size));
        if (heuristicRadius % 2 != 0 || heuristicRadius <= 1) {
            if (heuristicRadius - 1 > 1) {
                heuristicRadius -= 1;
            } else {
                heuristicRadius += 1;
            }
        }
        return heuristicRadius;
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

    private void updateTerrain(Terrain terrain, GridLocation hill) {
        int xMin;
        int xMax;
        int yMin;
        int yMax;

        xMin = Math.max(hill.getRowIndex() - radius - 1, 0);

        if (hill.getRowIndex() + radius + 1 >= size) {
            xMax = size - 1;
        } else {
            xMax = hill.getRowIndex() + radius + 1;
        }

        yMin = Math.max(hill.getColumnIndex() - radius - 1, 0);

        if (hill.getColumnIndex() + radius + 1 >= size) {
            yMax = size - 1;
        } else {
            yMax = hill.getColumnIndex() + radius + 1;
        }

        updateHillEnvironment(terrain, hill, new int[]{xMin, xMax, yMin, yMax});
    }

    private void updateHillEnvironment(Terrain terrain, GridLocation hill, int[] bounds) {
        int radiusSquare = radius * radius;
        int height;
        for (int i = bounds[0]; i <= bounds[1]; i++) {
            for (int j = bounds[2]; j <= bounds[3]; j++) {
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

    private void normalize(Terrain terrain, GridLocation[] minMax) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridLocation currentLocation = terrain.getLocationAt(i, j);
                double norm = (double) (currentLocation.getHeight() - minMax[0].getHeight()) / (minMax[1].getHeight() - minMax[0].getHeight());
                if (norm < (double) 1 / size) {
                    norm += (double) 1 / size;
                }
                currentLocation.setHeight((int) (norm * size));
            }
        }
    }

}