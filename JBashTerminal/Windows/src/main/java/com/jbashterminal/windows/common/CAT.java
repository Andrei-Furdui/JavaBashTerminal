package com.jbashterminal.windows.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CAT extends Helper {

    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String executeCommand(String cmd) {
        String actualFilePath = lsFromPath + "/" + filePath;
        if (Helper.isFile.negate().test(new File(actualFilePath))) {
            System.out.println("Sorry, '" + filePath + "' is not a file or doesn't exist");
        } else {
            try {
                initializeFileInstance();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (Stream<String> stream = Files.lines(Paths.get(actualFilePath))) {
                assert stream != null;
                stream.forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Helper.cleanUpFileInstance();
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
