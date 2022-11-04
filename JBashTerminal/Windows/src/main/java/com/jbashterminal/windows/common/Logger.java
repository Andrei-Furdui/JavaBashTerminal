package com.jbashterminal.windows.common;

public interface Logger {
    default String getLogTag(){
        return this.getClass().getSimpleName() + ": ";
    }
}
