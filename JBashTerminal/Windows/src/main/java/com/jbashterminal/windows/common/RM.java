package com.jbashterminal.windows.common;

import java.io.File;
import java.io.IOException;

public class RM extends Helper {

    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String executeCommand(String cmd) {
        if (!validateFileName.test(this.fileName) && !nameNotNull.test(this.fileName)) {
            File f = new File(this.fileName);
            try {
                if (!f.delete()) {
                    throw new IOException("Sorry, the file couldn't be deleted!");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String executeCommand(String cmd, String path) {
        return null;
    }
}
