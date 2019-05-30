package checkers.util;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Logger {
    private PrintStream out;
    private boolean exception = false;

    Logger(String fileName) {
        try {
            out = new PrintStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exception = true;
        }
    }

    void log(String s) {
        if (!exception)
            out.println(s);
    }
}
