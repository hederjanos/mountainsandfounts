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
            int rowIndex = RANDOM.nextInt(size + radius) - radius;
            int columnIndex = RANDOM.nextInt(size + radius) - radius;
            currentCell = new GridCell(rowIndex, columnIndex);
            if (hills.isEmpty()) {
                hills.add(currentCell);
                break;
            }
        } while (getClosestDistance(hills, currentCell) < radius * radius / Math.log(size));
        hills.add(currentCell);
        return currentCell;
    }

    private void updateTerrain(Terrain terrain, GridCell hill) {
        int rowMin;
        int rowMax;
        int colMin;
        int colMax;

        rowMin = Math.max(hill.getPosition().getRowIndex() - radius - 1, 0);

        if (hill.getPosition().getRowIndex() + radius + 1 >= size) {
            rowMax = size - 1;
        } else {
            rowMax = hill.getPosition().getRowIndex() + radius + 1;
        }

        colMin = Math.max(hill.getPosition().getColumnIndex() - radius - 1, 0);

        if (hill.getPosition().getColumnIndex() + radius + 1 >= size) {
            colMax = size - 1;
        } else {
            colMax = hill.getPosition().getColumnIndex() + radius + 1;
        }

        updateHillEnvironment(terrain, hill, new int[]{rowMin, rowMax, colMin, colMax});
    }

    private void updateHillEnvironment(Terrain terrain, GridCell hill, int[] bounds) {
        int radiusSquare = radius * radius;
        int height;
        for (int i = bounds[0]; i <= bounds[1]; i++) {
            for (int j = bounds[2]; j <= bounds[3]; j++) {
                GridCell currentCell = terrain.getCellAt(i, j);
                height = radiusSquare - hill.getPosition().calculateSquareDistance(currentCell.getPosition());
                if (height > 0) {
                    currentCell.setHeight(currentCell.getHeight() + height);
                }
            }
        }
    }

    private int getClosestDistance(List<GridCell> hills, GridCell hillCandidate) {
        int closestDistance = Integer.MAX_VALUE;
        for (GridCell hill : hills) {
            closestDistance = Math.min(hillCandidate.getPosition().calculateSquareDistance(hill.getPosition()), closestDistance);
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