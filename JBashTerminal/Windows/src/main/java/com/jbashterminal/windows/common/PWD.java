package com.jbashterminal.windows.common;

public final class PWD extends Helper {

    public String executeCommand(String cmd) {
        return System.getProperty("user.dir");
    }

    @Override
    public String executeCommand(String cmd, String path) {
        return null;
    }
}
