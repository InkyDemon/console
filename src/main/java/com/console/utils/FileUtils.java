package com.console.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

    public static ArrayList<Path> getFilesList(Path directory, Predicate<? super Path> filter) {
        try (Stream<Path> files = Files.list(directory)) {
            return new ArrayList<>(files.filter(filter).toList());
        }
        catch (IOException ioException) {
            return new ArrayList<>();
        }
    }
}
