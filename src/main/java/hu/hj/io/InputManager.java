package hu.hj.io;

import hu.hj.menu.enums.MainStates;
import hu.hj.menu.enums.SimulationStates;

import java.util.Scanner;

public class InputManager {

    private final Scanner scanner = new Scanner(System.in);

    public MainStates handleMainMenu() {
        MainStates state;
        String option = scanner.nextLine();
        state = switch (option) {
            case "0" -> MainStates.QUIT;
            case "1" -> MainStates.NEW_SIMULATION;
            case "2" -> MainStates.RERUN_SIMULATION;
            case "3" -> MainStates.DISPLAY_MENU;
            default -> MainStates.INVALID;
        };
        return state;
    }

    public SimulationStates handleSimulationMenu() {
        SimulationStates state;
        String option = scanner.nextLine();
        state = switch (option) {
            case "0" -> SimulationStates.QUIT_TO_MAIN;
            case "1" -> SimulationStates.SIMULATION_CONSOLE;
            case "2" -> SimulationStates.SIMULATION_GIF;
            case "3" -> SimulationStates.DISPLAY_MENU;
            default -> SimulationStates.INVALID;
        };
        return state;
    }

    public void close() {
        scanner.close();
    }

    public int setDimension() throws IllegalArgumentException {
        String input = scanner.nextLine();
        int size;
        try {
            size = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\nYou have added an invalid number. Please try it again!\n");
        }
        if (size > 40 || size < 4) {
            throw new IllegalArgumentException("\nPlease add a number between 4 and 40!\n");
        }
        return size;
    }
}