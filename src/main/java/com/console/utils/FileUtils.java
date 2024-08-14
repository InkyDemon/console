package com.console.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class FileUtils {
    public static BufferedWriter createFileWriter(Path filePath, OpenOption... openOptions) throws IOException {
        createParentDirs(filePath);

        return Files.newBufferedWriter(filePath, openOptions);
    }

    public static void createParentDirs(Path filePath) throws IOException {
        Path parentPath = filePath.getParent();
        if (parentPath != null) {
            Files.createDirectories(parentPath);
        }
    }
}
