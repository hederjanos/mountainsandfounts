package hu.hj.simulation;

import hu.hj.terrain.Terrain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationConsoleTest {
    private final int[][] initArray = new int[][]{
            {1, 2, 3, 4},
            {2, 3, 4, 4},
            {3, 3, 3, 3},
            {2, 1, 2, 3}};

    private SimulationConsole simulationConsole;

    @Test
    void testConstructor() {
        String arrayToString = getArrayToString(initArray);

        simulationConsole = new SimulationConsole(new Terrain(initArray));
        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testInitialize() {
        int[][] myArray = new int[][]{
                {-1, 2, 3, 4},
                {2, 3, 4, 4},
                {3, 3, 3, 3},
                {2, 1, 2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.initializeSimulation();
        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testRaise() {
        int[][] myArray = new int[][]{
                {-2, 2, 3, 4},
                {2, 3, 4, 4},
                {3, 3, 3, 3},
                {2, 1, 2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(1);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testMerge() {
        int[][] myArray = new int[][]{
                {-2, -2, 3, 4},
                {-2, 3, 4, 4},
                {3, 3, 3, 3},
                {2, 1, 2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(2);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testRaise2() {
        int[][] myArray = new int[][]{
                {-3, -3, 3, 4},
                {-3, 3, 4, 4},
                {3, 3, 3, 3},
                {2, 1, 2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(3);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testMerge2() {
        int[][] myArray = new int[][]{
                {-3, -3, -3, 4},
                {-3, -3, 4, 4},
                {-3, 3, 3, 3},
                {2, 1, 2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(4);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testMerge3() {
        int[][] myArray = new int[][]{
                {-3, -3, -3, 4},
                {-3, -3, 4, 4},
                {-3, 3, 3, 3},
                {-2, 1, 2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(5);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testMerge4() {
        int[][] myArray = new int[][]{
                {-3, -3, -3, 4},
                {-3, -3, 4, 4},
                {-3, 3, 3, 3},
                {-2, -1, 2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(6);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testRaise3() {
        int[][] myArray = new int[][]{
                {-3, -3, -3, 4},
                {-3, -3, 4, 4},
                {-3, 3, 3, 3},
                {-2, -2, 2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(7);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testMerge5() {
        int[][] myArray = new int[][]{
                {-3, -3, -3, 4},
                {-3, -3, 4, 4},
                {-3, 3, 3, 3},
                {-2, -2, -2, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(8);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testRaise4() {
        int[][] myArray = new int[][]{
                {-3, -3, -3, 4},
                {-3, -3, 4, 4},
                {-3, 3, 3, 3},
                {-3, -3, -3, 3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(9);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testMerge6() {
        int[][] myArray = new int[][]{
                {-3, -3, -3, 4},
                {-3, -3, 4, 4},
                {-3, -3, -3, 3},
                {-3, -3, -3, -3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(10);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testMerge7() {
        int[][] myArray = new int[][]{
                {-3, -3, -3, 4},
                {-3, -3, 4, 4},
                {-3, -3, -3, -3},
                {-3, -3, -3, -3}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(11);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testRaise5() {
        int[][] myArray = new int[][]{
                {-4, -4, -4, 4},
                {-4, -4, 4, 4},
                {-4, -4, -4, -4},
                {-4, -4, -4, -4}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(12);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testMerge8() {
        int[][] myArray = new int[][]{
                {-4, -4, -4, -4},
                {-4, -4, -4, -4},
                {-4, -4, -4, -4},
                {-4, -4, -4, -4}};
        String arrayToString = getArrayToString(myArray);

        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(13);

        String terrainToString = simulationConsole.getTerrain().toString();

        assertEquals(arrayToString, terrainToString);
    }

    @Test
    void testIsFullyFlooded() {
        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(12);
        assertFalse(simulationConsole.isTerrainFullyFlooded());
    }

    @Test
    void testIsFullyFlooded2() {
        SimulationConsole simulationConsole = new SimulationConsole(new Terrain(initArray));
        simulationConsole.flood(13);
        assertTrue(simulationConsole.isTerrainFullyFlooded());
    }


    private static String getArrayToString(int[][] myArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] ints : myArray) {
            for (int j = 0; j < myArray.length; j++) {
                if (ints[j] > 0) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(ints[j]).append("\t");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}