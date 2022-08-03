package hu.hj.io;

import hu.hj.menu.enums.MainStates;
import hu.hj.menu.enums.SimulationStates;

import java.util.Scanner;

public class InputManager {

    private final Scanner scanner = new Scanner(System.in);

    public MainStates handleMainMenu() {
        MainStates state;
        String option = scanner.nextLine();
        switch (option) {
            case "0":
                state = MainStates.QUIT;
                break;
            case "1":
                state = MainStates.NEW_SIMULATION;
                break;
            case "2":
                state = MainStates.RERUN_SIMULATION;
                break;
            case "3":
                state = MainStates.DISPLAY_MENU;
                break;
            default:
                state = MainStates.INVALID;
        }
        return state;
    }

    public SimulationStates handleSimulationMenu() {
        SimulationStates state;
        String option = scanner.nextLine();
        switch (option) {
            case "0":
                state = SimulationStates.QUIT_TO_MAIN;
                break;
            case "1":
                state = SimulationStates.SIMULATION_CONSOLE;
                break;
            case "2":
                state = SimulationStates.SIMULATION_GIF;
                break;
            case "3":
                state = SimulationStates.DISPLAY_MENU;
                break;
            default:
                state = SimulationStates.INVALID;
        }
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