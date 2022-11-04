package com.jbashterminal.windows.common;

import java.util.Date;

public class TIME extends Helper{

    @Override
    public String executeCommand(String cmd) {
        return  String.valueOf(new Date());
    }

    @Override
    public String executeCommand(String cmd, String path) {
        return null;
    }
}
