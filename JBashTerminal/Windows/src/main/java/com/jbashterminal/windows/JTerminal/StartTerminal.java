package com.jbashterminal.windows.JTerminal;

import com.jbashterminal.windows.common.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartTerminal {
    private boolean startTerminal;
    private String commandExecuted;
    private String commandExecutedAndItsResult;
    private String actualContentToBeAddedToHistoryFile;

    // flag used to indicate if the output of the
    // commands should be added to the history file as well
    private boolean includeCMDOutputToHistory;
    private final HISTORYPersistence historyPersistence;

    public StartTerminal(boolean startTerminal) {
        this.startTerminal = startTerminal;

        historyPersistence = new HISTORYPersistence();
        historyPersistence.start();

        Thread runTerminal = new Thread(this::run);
        runTerminal.start();
    }

    private void run() {
        System.out.println(Helper.HELLO_MSG);
        String cmd = "";
        while (startTerminal) {
            System.out.print(Helper.MARKER);
            Scanner scan = new Scanner(System.in);
            cmd = scan.nextLine().toLowerCase();

            try {
                String generateException = cmd.split(" ")[1];

                if (cmd.equals("ls -l")) {
                    generateException = cmd.split(" ")[2];
                }
                executeComplexCommand(cmd);
            } catch (IndexOutOfBoundsException e) {
                executeSimpleCommand(cmd);
            }
        }
    }

    private void executeSimpleCommand(String cmd) {
        switch (cmd) {
            case "pwd":
                commandExecutedAndItsResult = Helper.MARKER + new PWD().executeCommand("pwd");
                break;
            case "time":
            case "date":
                commandExecutedAndItsResult = Helper.MARKER + new TIME().executeCommand("time");
                break;
            case "ls":
            case "ls -l":
                commandExecutedAndItsResult = new LS().executeCommand(cmd, new PWD().executeCommand("pwd"));
                //System.out.println(new LS().executeCommand(cmd));
                break;
            case "touch":
            case "rm":
            case "rmdir":
            case "mkdir":
            case "cat":
                commandExecutedAndItsResult = Helper.MARKER + "Sorry, invalid name!";
                break;
            case "help":
                InternalTerminalCommands.displayAllCommandsAndHelp(true);
                InternalTerminalCommands.displayAllCommandsAndHelp(false);
                break;
            case "history":
                historyPersistence.addMapDataToFile();
                historyPersistence.openAndReadHistoryFromFile();
                break;
            case "exit":
                stop();
        }
        if (commandExecutedAndItsResult != null) {
            System.out.println(commandExecutedAndItsResult);
            if (includeCMDOutputToHistory) {
                historyPersistence.openAndAddHistoryToMap(new TIME().executeCommand("time"), cmd, commandExecutedAndItsResult);
            } else {
                historyPersistence.openAndAddHistoryToMap(new TIME().executeCommand("time"), cmd);
            }
            commandExecutedAndItsResult = null;
        }
    }

    private void executeComplexCommand(String cmd) {
        List<String> commandPart = new ArrayList<>();
        for (int i = 0; i < Helper.getNumberOfCommandArguments(cmd); i++) {
            commandPart.add(cmd.split(" ")[i]);
        }

        String actualCMD = commandPart.get(0);
        String actualCMDParameters = commandPart.get(1);
        switch (actualCMD.toLowerCase()) {
            case "touch":
                TOUCH touch = new TOUCH();
                touch.setFileName(actualCMDParameters);
                touch.executeCommand(cmd);
                break;
            case "rm":
            case "rmdir":
                RM rm = new RM();
                rm.setFileName(actualCMDParameters);
                rm.executeCommand(cmd);
                break;
            case "mkdir":
                MKDIR mkdir = new MKDIR();
                mkdir.setDirectoryName(actualCMDParameters);
                mkdir.executeCommand(cmd);
                break;
            case "history":
                if (actualCMDParameters.equals("clean")) {
                    historyPersistence.cleanUpFile();
                } else if (actualCMDParameters.equals("output")) {
                    if (!includeCMDOutputToHistory) {
                        System.out.println("Results will be added to history... [for this session]");
                    } else {
                        System.out.println("Results won't be added to history anymore...[for this session]");
                    }
                    includeCMDOutputToHistory = !includeCMDOutputToHistory;
                }
                break;
            case "cat":
                CAT cat = new CAT();
                cat.setFilePath(actualCMDParameters);
                cat.executeCommand(cmd, new PWD().executeCommand("pwd"));
                break;
        }
        historyPersistence.openAndAddHistoryToMap
                (new TIME().executeCommand("time"), actualCMD + " " + actualCMDParameters);
    }

    private void stop() {
        System.out.println("Exiting the terminal... Goodbye!");
        Helper.sleep(1);
        this.startTerminal = false;
        historyPersistence.addMapDataToFile();
    }

    public static void main(String[] args) {
        new StartTerminal(true);
        //System.out.println(Helper.getProperty("version1"));
    }

}
