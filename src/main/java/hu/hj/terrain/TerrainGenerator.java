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
        GridCell[] minMax = terrain.getMinAndMaxHeightCell();
        normalize(terrain, minMax);
        return terrain;
    }

    private void generateHills(Terrain terrain) {
        List<GridCell> hills = new ArrayList<>();
        for (int i = 0; i < terrain.getSize(); i++) {
            generateHill(terrain, hills);
        }
    }

    private void generateHill(Terrain terrain, List<GridCell> hills) {
        GridCell hill = generateHill(radius, hills);
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

    private GridCell generateHill(int radius, List<GridCell> hills) {
        GridCell currentCell;
        do {
            int x = RANDOM.nextInt(size + radius) - radius;
            int y = RANDOM.nextInt(size + radius) - radius;
            currentCell = new GridCell(x, y);
            if (hills.isEmpty()) {
                hills.add(currentCell);
                break;
            }
        } while (getClosestDistance(hills, currentCell) < radius * radius / Math.log(size));
        hills.add(currentCell);
        return currentCell;
    }

    private void updateTerrain(Terrain terrain, GridCell hill) {
        int xMin;
        int xMax;
        int yMin;
        int yMax;

        xMin = Math.max(hill.getX() - radius - 1, 0);

        if (hill.getX() + radius + 1 >= size) {
            xMax = size - 1;
        } else {
            xMax = hill.getX() + radius + 1;
        }

        yMin = Math.max(hill.getY() - radius - 1, 0);

        if (hill.getY() + radius + 1 >= size) {
            yMax = size - 1;
        } else {
            yMax = hill.getY() + radius + 1;
        }

        updateHillEnvironment(terrain, hill, new int[]{xMin, xMax, yMin, yMax});
    }

    private void updateHillEnvironment(Terrain terrain, GridCell hill, int[] bounds) {
        int radiusSquare = radius * radius;
        int height;
        for (int i = bounds[0]; i <= bounds[1]; i++) {
            for (int j = bounds[2]; j <= bounds[3]; j++) {
                GridCell currentCell = terrain.getCellAt(i, j);
                height = radiusSquare - hill.calculateSquareDistance(currentCell);
                if (height > 0) {
                    currentCell.setHeight(currentCell.getHeight() + height);
                }
            }
        }
    }

    private int getClosestDistance(List<GridCell> hills, GridCell hillCandidate) {
        int closestDistance = Integer.MAX_VALUE;
        for (GridCell hill : hills) {
            closestDistance = Math.min(hillCandidate.calculateSquareDistance(hill), closestDistance);
        }
        return closestDistance;
    }

    private void normalize(Terrain terrain, GridCell[] minMax) {
        for (GridCell cell : terrain.getCells()) {
            double norm = (double) (cell.getHeight() - minMax[0].getHeight()) / (minMax[1].getHeight() - minMax[0].getHeight());
            if (norm < (double) 1 / size) {
                norm += (double) 1 / size;
            }
            cell.setHeight((int) (norm * size));
        }
    }

}