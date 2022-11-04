package com.jbashterminal.windows.common;

import java.io.File;
import java.io.IOException;

public class MKDIR extends Helper {

    private String directoryName;

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    @Override
    public String executeCommand(String cmd) {
        try {
            initializeFileInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (validateFileName.negate().test(this.directoryName) &&
                nameNotNull.negate().test(this.directoryName)) {
            File f = new File(directoryPath.getPath() + "/" + this.directoryName);
            try {
                if (!f.mkdir()) {
                    String callerClass =
                            Class.forName(Thread.currentThread().getStackTrace()[3].getClassName()).toString();
                    if (!callerClass.contains("HISTORYPersistence")) {
                        throw new IOException("Sorry, the directory couldn't be created!");
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String executeCommand(String cmd, String path) {
        lsFromPath = path;
        return this.executeCommand(cmd);
    }
}
