package hu.hj;

import hu.hj.io.InputManager;
import hu.hj.io.Printer;
import hu.hj.menu.enums.MainStates;
import hu.hj.menu.enums.SimulationStates;
import hu.hj.menu.view.MainMenu;
import hu.hj.menu.view.SimulationMenu;
import hu.hj.simulation.Simulation;
import hu.hj.simulation.SimulationConsole;
import hu.hj.simulation.SimulationGIF;
import hu.hj.terrain.Terrain;
import hu.hj.terrain.TerrainGenerator;
import hu.hj.terrain.TerrainUtilities;

public class Controller {

    private Terrain previousTerrain;
    private final Printer printer = new Printer();
    private final InputManager inputManager = new InputManager();
    private final MainMenu mainMenu = new MainMenu(printer);
    private final SimulationMenu simulationMenu = new SimulationMenu(printer);

    public void run() {
        mainMenu.displayGreeting();
        boolean quit = false;
        mainMenu.displayMenu();
        while (!quit) {
            MainStates option = inputManager.handleMainMenu();
            switch (option) {
                case QUIT:
                    quit = true;
                    inputManager.close();
                    printer.close();
                    mainMenu.displayGoodbye();
                    break;
                case NEW_SIMULATION:
                    createTerrain();
                    break;
                case RERUN_SIMULATION:
                    if (previousTerrain != null) {
                        simulationMenu.displayMenu();
                        setSimulation(previousTerrain);
                    } else {
                        mainMenu.displayIfTerrainIsNull();
                        mainMenu.displayMenu();
                    }
                    break;
                case DISPLAY_MENU:
                    mainMenu.displayMenu();
                    break;
                default:
                    mainMenu.displayValidNumberInfo();
                    mainMenu.displayMenu();
            }
        }
    }

    private void createTerrain() {
        simulationMenu.displayVisualizationInfo();
        while (true) {
            try {
                simulationMenu.askUserToEnterSize();
                int size = inputManager.setDimension();
                mainMenu.displayTerrainHeader();
                Terrain terrain = new TerrainGenerator(size).getTerrain();
                previousTerrain = terrain.copyTerrain();
                printer.println(TerrainUtilities.terrainToString(terrain));
                simulationMenu.displayMenu();
                setSimulation(terrain);
                break;
            } catch (IllegalArgumentException e) {
                printer.println(e.getMessage());
            }
        }
    }

    private void setSimulation(Terrain terrain) {
        boolean quit = false;
        while (!quit) {
            SimulationStates option = inputManager.handleSimulationMenu();
            switch (option) {
                case QUIT_TO_MAIN:
                    mainMenu.displayMenu();
                    quit = true;
                    break;
                case SIMULATION_CONSOLE:
                    Simulation simulation = new SimulationConsole(terrain);
                    runSimulation(simulation);
                    quit = true;
                    break;
                case SIMULATION_GIF:
                    simulation = new SimulationGIF(terrain);
                    runSimulation(simulation);
                    quit = true;
                    break;
                case DISPLAY_MENU:
                    simulationMenu.displayMenu();
                    break;
                default:
                    simulationMenu.displayValidNumberInfo();
                    simulationMenu.displayMenu();
            }
        }
    }

    private void runSimulation(Simulation simulation) {
        simulation.flood();
        simulationMenu.displayVisualizationInProgress(simulation);
        simulation.visualize(printer);
        simulationMenu.displayVisualizationDone(simulation);
        mainMenu.displayMenu();
    }
}