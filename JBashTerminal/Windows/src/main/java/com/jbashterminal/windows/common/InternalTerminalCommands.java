package com.jbashterminal.windows.common;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

public interface InternalTerminalCommands {
    static Map<String, String> getAllCommandsAndHelp() {
        Map<String, String> commandHelp = new HashMap<>();
        commandHelp.put("ls", "list directory contents");
        commandHelp.put("pwd", "print name of current/working directory");
        commandHelp.put("time", "time a simple command or give resource usage");
        commandHelp.put("date", "print or set the system date and time");
        commandHelp.put("ls -l", "list directory contents alongside the properties");
        commandHelp.put("mkdir", "create the DIRECTORY(ies), if they do not already exist");
        commandHelp.put("rm", "remove files or directories");
        commandHelp.put("rmdir", "remove directories");
        commandHelp.put("history", "display history of last commands");
        commandHelp.put("history clean", "remove whole history");
        commandHelp.put("history output", "[BETA] save history of last commands and their results");
        commandHelp.put("cat", "concatenate files and print on the standard output");


        // return a naturalOrdered map (by key)
        return new TreeMap<>(commandHelp);
    }

    static void displayAllCommandsAndHelp(boolean simpleCommands) {
        Predicate<Map.Entry<String, String>> commandType;
        if (simpleCommands) {
            commandType = (key) -> !key.getKey().contains(" ");
        } else {
            commandType = (key) -> key.getKey().contains(" ");
        }
        System.out.println("\n");
        getAllCommandsAndHelp().
                entrySet().
                stream().
                filter(commandType).
                forEach((value) -> {
                    System.out.println("\t" + value.getKey() + ": " + value.getValue());
                });
        System.out.println("\n");
    }

    static List<String> getAllCommands() {
        Function<Map.Entry<String, String>, String> getKeysOnly = Map.Entry::getKey;
        return getAllCommandsAndHelp().entrySet().stream().map(getKeysOnly).collect(Collectors.toList());
    }

    static void displayAllCommands() {
        getAllCommands().forEach(System.out::println);
    }

    @Deprecated
    static HashMap<String, String> getAllCommandsAsExecutable(String cmd, String fileName) {
        HashMap<String, String> allCommands = new HashMap<>();
        allCommands.put("ls", new LS().executeCommand(cmd));
        allCommands.put("pwd", new PWD().executeCommand(cmd));
        allCommands.put("time", new TIME().executeCommand(cmd));

        TOUCH touch = new TOUCH();
        touch.setFileName(fileName);
        allCommands.put("touch", touch.executeCommand(cmd));

        RM rm = new RM();
        rm.setFileName(fileName);
        allCommands.put("rm", rm.executeCommand(cmd));

        return allCommands;
    }
}
