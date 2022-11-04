package com.jbashterminal.windows.common;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.BiPredicate;


/**
 * This class is a #{@link Thread} (daemon) and can be used to:
 * 1. store all the commands + their results (if needed) executed by the user into a file
 * 2. access the content of that file (whole file or just the last X commands)
 */
public final class HISTORYPersistence extends Thread implements Logger {

    private final String LOG_TAG = getLogTag();
    // flag that indicates if the file is open
    private static boolean fileOpen = false;

    // flag that indicates if the whole will be read
    private boolean readFullFile;

    // flag used to indicate that the current key has not
    // already been inserted into the file, otherwise the same
    // entry might appear twice - for example, for the following sequence:
    // history -> exit (both commands calling this API for writing
    // the map to file, the map containing the same last command)
    private String lastKeyInserted;

    // the path of the persistent file (excluding the file name)
    private String filePath;
    private static final String FILE_NAME = "jshell.history.txt";
    private static final String DIRECTORY_NAME = "JShellHistory";
    private FileWriter fWriter;

    // map used to store data and periodically to insert
    // it into the file, avoiding in this way opening and closing
    // the file after every command
    // key = new TIME().executeCommand()
    // value = command that is executed
    private final SortedMap<String, String> mapMessage;

    private final BiPredicate<Map<String, String>, String> valueNotContainedYet = Map::containsKey;

    public HISTORYPersistence() {
        this.setDaemon(true);
        mapMessage = new TreeMap<>();
        lastKeyInserted = "";

        createPersistentDirectoryAndFile();
        //openHistoryFile();
    }

    private void initializeFilePath() {
        this.filePath = System.getProperty("user.dir") + "/Windows/src/main/resources/";
    }

    /**
     * If the PersistentDirectory/File exists then it's not re-created
     */
    private void createPersistentDirectoryAndFile() {
        initializeFilePath();

        // persistent directory
        MKDIR mkdir = new MKDIR();
        mkdir.setDirectoryName(DIRECTORY_NAME);
        mkdir.executeCommand("mkdir", filePath);

        // TODO - find a more elegant way:
        // clean up the 'directoryPath' because it may
        // contain a wrong path
        Helper.cleanUpFileInstance();

        // persistent file
        filePath += DIRECTORY_NAME;
        TOUCH touch = new TOUCH();
        touch.setFileName(FILE_NAME);
        touch.executeCommand("touch", filePath);
        Helper.cleanUpFileInstance();
        Helper.cleanUpLSPathInstance();

        // prepare filePath for further writes to the actual file
        filePath = filePath + "/" + FILE_NAME;
    }

    private void openHistoryFile() {
        if (!fileOpen) {
            try {
                fWriter = new FileWriter(filePath, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openAndAppendHistoryToFileAsString(String time, String msg /*msg = aka command*/) {
        if (!lastKeyInserted.equals(time)) {
            openHistoryFile();
            try {
                String finalMsg = "[" + time + "]";
                finalMsg += ":  " + msg + "\n";
                fWriter.write(finalMsg);
                fileOpen = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                lastKeyInserted = time;
            }
        }
    }

    public void openAndReadHistoryFromFile() {
        CAT cat = new CAT();

        // no need to specify the file name here as the
        // variable filePath already contains it
        cat.setFilePath("");
        cat.executeCommand("", filePath);
    }

    public void cleanUpFile() {
        try {
            new FileWriter(filePath, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeHistoryFile() {
        if (fWriter != null && fileOpen) {
            try {
                fWriter.close();
                fileOpen = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openAndAddHistoryToMap(String time, String msg, String... output) {
        String actualOutput = output.length > 0 ? output[0] : null;
        if (actualOutput != null) {
            msg = msg + "\n" + actualOutput + "\n";
        }
        if (valueNotContainedYet.negate().test(mapMessage, time) &&
                mapMessage != null) {
            mapMessage.put(time, msg);
        }
    }

    /**
     * Flushes all the data from this #{@code mapMessage} into the file
     * The method is designed to be called when the user types either
     * "history" or "exit"
     */
    public void addMapDataToFile() {
        if (mapMessage != null) {
            mapMessage.forEach(this::openAndAppendHistoryToFileAsString);
            Helper.removeAllElements.accept(mapMessage);
            closeHistoryFile();
        }
    }

    public void listMapItems() {
        mapMessage.forEach((key, value) -> {
            System.out.println(LOG_TAG + "key (time) - " + key + ", value (command) - " + value);
        });
    }

}
