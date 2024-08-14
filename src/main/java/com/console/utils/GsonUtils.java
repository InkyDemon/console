package com.console.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class GsonUtils {
    private static final Gson DEFAULT_GSON = new GsonBuilder().setPrettyPrinting().create();

    public static JsonObject jsonToObject(String json) {
        return DEFAULT_GSON.fromJson(json, JsonObject.class);
    }

    public static <T> T jsonToObject(String json, Class<T> objectType) {
        return DEFAULT_GSON.fromJson(json, objectType);
    }

    public static void objectToJson(Object object, Path jsonFile) throws IOException {
        try (BufferedWriter bufferedWriter = FileUtils.createFileWriter(jsonFile, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            DEFAULT_GSON.toJson(object, bufferedWriter);
        }
    }
}
