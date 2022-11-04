package com.jbashterminal.windows.common;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Helper {

    public static final String HELLO_MSG = getHelloMsg();
    public static final String MARKER = "# ";
    public static final String HISTORY_OUTPUT_DELIMITER = "-----";

    // flag specifying if this class has been used at all
    // mainly used for testing purposes
    public static boolean classCalled = false;

    // validates the name of a given file
    // if the name is empty then it returns true
    static Predicate<String> validateFileName = String::isEmpty;
    static Predicate<String> nameNotNull = Objects::isNull;
    static Predicate<File> pathExists = File::exists;
    static Predicate<File> isFile = File::isFile;

    static Consumer<SortedMap<String, String>> removeAllElements = Map::clear;


    // variable used by file/directory utility commands:
    // ls
    // mkdir
    public static File directoryPath;
    public static String lsFromPath;

    // flags used for returning various values from methods
    public static final String RESULT_OK = "result_ok";
    public static final String RESULT_NOK = "result_nok";

    public static void sleep(int seconds) {
        classCalled = true;
        try {
            Thread.sleep(1000L * seconds);
        } catch (InterruptedException e) {

        }
    }

    public static byte getNumberOfCommandArguments(String command) {
        classCalled = true;
        byte counter = 1; // the first element is represented by the
        // command itself
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                counter++;
            }
        }
        return counter;
    }

    public static String getOSName() {
        classCalled = true;
        return System.getProperty("os.name");
    }

    public static String getOSVersion() {
        classCalled = true;
        return System.getProperty("os.version");
    }

    private static String getHelloMsg() {
        classCalled = true;
        return "Hello, nice to see you, hope you're doing well today!\n" +
                "Welcome to JShell.\n" +
                "Version: " + getProperty("version") + "\n" +
                "Running OS:\n\t" +
                "Name: " + getOSName() + "\n\t" +
                "Version: " + getOSVersion();
    }

    public static /*@Nullable*/ String getProperty(String property) {
        classCalled = true;
        Properties prop = new Properties();
        String propValue = null;
        try {
            prop.load(Helper.class.getResourceAsStream("/maven.properties"));

            propValue = prop.getProperty(property);
            propValue = propValue.substring(2, propValue.length() - 1);
        } catch (IOException | NullPointerException e) {
            System.out.println("Error - property may not exist? ");
            e.getStackTrace();
        }
        return propValue;
    }

    public static void initializeFileInstance() throws IOException {
        if (directoryPath == null) {
            if (lsFromPath == null) {
                directoryPath = new File(new PWD().executeCommand("pwd"));
            } else {
                if (pathExists.test(new File(lsFromPath))) {
                    directoryPath = new File(lsFromPath);
                } else {
                    throw new IOException("File does not exist " + lsFromPath);
                }
            }
        }
    }

    public static void cleanUpFileInstance() {
        if (directoryPath != null) {
            directoryPath = null;
        }
    }

    public static void cleanUpLSPathInstance() {
        if (lsFromPath != null) {
            lsFromPath = null;
        }
    }

    public static String convertFromEpochToHuman(long epochTime) {
        Date date = new Date(epochTime);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(date);
    }

    public abstract String executeCommand(String cmd);

    public abstract String executeCommand(String cmd, String path);
}
