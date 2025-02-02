package com.console.json;

import com.console.utils.GsonUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameInstance {
    public String name;
    public String description;
    public String version;

    public GameInstance(String name, String description, String version) {
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public static GameInstance fromFile(Path file) {
        try {
            String json = Files.readString(file);

            return GsonUtils.jsonToObject(json, GameInstance.class);
        }
        catch (IOException ioException) {
            return new GameInstance("Null", "Null", "Null");
        }
    }
}
