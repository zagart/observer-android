package by.zagart.observer.controller;

/**
 * Manager for Tomcat server console logs.
 *
 * @author zagart
 */
public class Logger {
    private static StringBuilder sLogs = new StringBuilder();

    public static void log(String pLog) {
        sLogs.append(pLog).append("\n");
        System.out.println(pLog);
    }

    public static void print() {
        System.out.println(sLogs.toString());
    }
}
