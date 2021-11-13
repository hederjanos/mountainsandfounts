package hu.hj.io;

import java.io.PrintStream;

public class Printer {

    private final PrintStream printStream;

    public Printer() {
        this.printStream = new PrintStream(System.out);
    }

    public void print(String stringToPrint) {
        printStream.print(stringToPrint);
    }

    public void println(String stringToPrint) {
        printStream.println(stringToPrint);
    }

    public void close() {
        printStream.close();
    }
}