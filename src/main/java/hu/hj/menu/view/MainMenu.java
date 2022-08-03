package hu.hj.menu.view;

import hu.hj.io.Printer;

public class MainMenu extends Menu {

    public MainMenu(Printer printer) {
        super(printer);
    }

    public void displayMenu() {
        printer.println(SEPARATOR);
        printer.println("MAIN MENU");
        printer.println("Press");
        printer.println(
                        "0 - to quit\n" +
                        "1 - to initialize a new terrain\n" +
                        "2 - rerun a simulation\n" +
                        "3 - to display Main Menu\n");
        printer.println(SEPARATOR);
    }

    public void displayGreeting() {
        printer.println("Welcome!");
        printer.println("\n" + SEPARATOR);
        printer.println("Mountains and Founts Simulation");
        printer.println(SEPARATOR + "\n");
    }

    public void displayGoodbye() {
        printer.println("\nGood bye!\n");
    }

    public void displayIfTerrainIsNull() {
        printer.println("\nTerrain is not created yet, create a terrain before!\n");
    }

    public void displayTerrainHeader() {
        printer.println("\nThe random generated terrain:\n");
    }
}