package hu.hj.menu.view;

import hu.hj.io.Printer;
import hu.hj.simulation.Simulation;
import hu.hj.simulation.SimulationGIF;

public class SimulationMenu extends Menu {

    public SimulationMenu(Printer printer) {
        super(printer);
    }

    public void displayMenu() {
        printer.println(SEPARATOR);
        printer.println("SIMULATION MENU");
        printer.println("Press");
        printer.println(
                        "0 - to quit to Main Menu\n" +
                        "1 - to simulate on console\n" +
                        "2 - to create an animated GIF\n" +
                        "3 - to display Simulation Menu\n");
        printer.println(SEPARATOR);
    }

    public void displayVisualizationInfo() {
        printer.println(
                        "The creation of an animated gif is a heap space killer process.\n" +
                        "To avoid OutOfMemoryError the upper border of dimension is limited to 40.\n" +
                        "The lower limit is 4.\n");
    }

    public void askUserToEnterSize() {
        printer.print("Add the dimension of the terrain: ");
    }

    public void displayVisualizationInProgress(Simulation simulation) {
        printer.println(String.format("The simulation consists of %d steps.", simulation.getNumberOfSimulationSteps()));
        printer.println("The visualization is in progress...\n");
    }

    public void displayVisualizationDone(Simulation simulation) {
        printer.println("\nVisualization is done!\n");
        if (simulation instanceof SimulationGIF) {
            String filePath = ((SimulationGIF) simulation).getAnimatedGIF().getPath().toString();
            printer.println(String.format("A simulation.gif named file can be found at: %s.%n", filePath));
        }
    }
}