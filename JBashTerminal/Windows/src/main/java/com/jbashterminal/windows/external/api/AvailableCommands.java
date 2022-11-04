package com.jbashterminal.windows.external.api;

@FunctionalInterface
public interface AvailableCommands<T> {

    /**
     * Returns all available commands for this application
     *
     * @return
     */
    T get();

    default T getAllCommands() {
        return this.get();
    }
}
