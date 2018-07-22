package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";

    private static String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS : ");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void d(String tag, String message) {
        System.out.println(BLUE + getDate() +  tag + " " + message + RESET);
    }

    public static void d(String message) {
        System.out.println(BLUE + getDate() + message + RESET);
    }

    public static void e(String tag, String message) {
        System.out.println(RED + getDate() + tag + " " + message + RESET);
    }

    public static void e(String message) {
        System.out.println(RED + getDate()  + message + RESET);
    }

    public static void i(String tag, String message) {
        System.out.println(GREEN + getDate() + tag + " " + message + RESET);
    }

    public static void i(String message) {
        System.out.println(GREEN + getDate()  + message + RESET);
    }
}
