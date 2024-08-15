package com.console.launch;

import com.console.json.GameInstance;
import com.console.utils.GsonUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class GamesManager {
    private final Path gamesDirectory;

    public GamesManager(Path gamesDirectory) {
        this.gamesDirectory = gamesDirectory;
    }

    public GameInstance getGameInstance(Path gameDirectory) {
        Path instanceJson = gameDirectory.resolve("instance.json");
        try {
            if (Files.exists(instanceJson)) {
                return GsonUtils.jsonToObject(Files.readString(instanceJson), GameInstance.class);
            }
        }
        catch (IOException ignored) {}

        return new GameInstance("not found", "not found", "not found");
    }

    public ArrayList<Path> getGamesDirectories() {
        try (Stream<Path> files = Files.list(gamesDirectory)) {
           return new ArrayList<>(files.filter((path) -> Files.isDirectory(path) && Files.exists(path.resolve("instance.json"))).toList());
        }
        catch (IOException ioException) {
            return new ArrayList<>();
        }
    }
}
