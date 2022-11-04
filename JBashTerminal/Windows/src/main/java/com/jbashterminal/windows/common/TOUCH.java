package com.jbashterminal.windows.common;

import java.io.File;
import java.io.IOException;

public class TOUCH extends Helper {

    private String fileName;


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String executeCommand(String cmd) {
        if (!validateFileName.test(this.fileName) && !nameNotNull.test(this.fileName)) {

            // this variable is here to avoid the call of the
            // initializeFileInstance(), letting the method executeCommand(cmd, path)
            // to take care of this
            String firstPart = "";
            if (directoryPath != null) {
                firstPart = directoryPath.getPath() + "/";
            }
            File f = new File(firstPart + this.fileName);
            try {
                if (!f.createNewFile()) {
                    String callerClass =
                            Class.forName(Thread.currentThread().getStackTrace()[3].getClassName()).toString();
                    if (!callerClass.contains("HISTORYPersistence")) {
                        throw new IOException("Sorry, the filename seems to exist. Please try again!");
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
        try {
            initializeFileInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.executeCommand(cmd);
    }
}
