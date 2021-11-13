package hu.hj.menu.view;

import hu.hj.io.Printer;

public abstract class Menu {

    protected static final String SEPARATOR = "================================================";
    protected Printer printer;

    protected Menu(Printer printer) {
        this.printer = printer;
    }

    public void displayValidNumberInfo() {
        printer.println("\nPlease add a valid number!\n");
    }

    public abstract void displayMenu();
}
