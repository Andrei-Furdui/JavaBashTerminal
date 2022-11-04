package com.jbashterminal.windows.common;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LS extends Helper {
    private StringBuilder stringBuilder;

    @Override
    public String executeCommand(String cmd) {
        this.stringBuilder = new StringBuilder();
        try {
            initializeFileInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (cmd) {
            case "ls":
                return this.getAllFilesAndDirs();
            case "ls -l":
                return this.getAllFilesAndDirsWithProperties();
            default:
                return null;
        }

    }

    @Override
    public String executeCommand(String cmd, String path) {
        lsFromPath = path;
        return this.executeCommand(cmd);
    }

    private String getAllFilesAndDirs() {
        if (directoryPath != null) {
            for (String s : Objects.requireNonNull(directoryPath.list())) {
                this.stringBuilder.append(s).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private String getAllFilesAndDirsWithProperties() {
        File[] content = directoryPath.listFiles();
        assert content != null;
        for (File f : content) {
            stringBuilder.append(f.isFile() ? "-" : "d");
            stringBuilder.append(f.canRead() ? "r" : "-");
            stringBuilder.append(f.canWrite() ? "w" : "-");
            stringBuilder.append(f.canExecute() ? "x" : "-");
            stringBuilder.append("\t" + Helper.convertFromEpochToHuman(f.lastModified()));
            stringBuilder.append("\t" + f.getName());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
