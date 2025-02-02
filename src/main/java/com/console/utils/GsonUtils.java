package com.console.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class GsonUtils {
    private static final Gson DEFAULT_GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T jsonToObject(String json, Class<T> objectType) {
        return DEFAULT_GSON.fromJson(json, objectType);
    }

    public static void objectToFile(Object object, Path jsonFile) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(jsonFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            DEFAULT_GSON.toJson(object, bufferedWriter);
        }
    }
}
