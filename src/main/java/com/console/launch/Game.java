package com.console.launch;

import com.console.json.GameInstance;
import com.console.utils.FileUtils;

import java.nio.file.Path;
import java.util.HashMap;

public class Game {
    public final Path GAME_DIRECTORY;
    public final Path ASSETS_DIRECTORY;
    public final Path BACKGROUNDS_DIRECTORY;
    public final Path INSTANCE_FILE;
    public final Path MINECRAFT_DIRECTORY;
    public final Path ICON;

    public final HashMap<String, Path> GRAPHICS_PATHS;

    public GameInstance instance;

    public Game(Path gameDirectory) {
        this.GAME_DIRECTORY = gameDirectory;
        this.INSTANCE_FILE = GAME_DIRECTORY.resolve("instance.json");
        this.ASSETS_DIRECTORY = GAME_DIRECTORY.resolve("assets");

        this.MINECRAFT_DIRECTORY = ASSETS_DIRECTORY.resolve("minecraft");
        this.ICON = ASSETS_DIRECTORY.resolve("icon.png");
        this.BACKGROUNDS_DIRECTORY = ASSETS_DIRECTORY.resolve("backgrounds");

        this.GRAPHICS_PATHS = new HashMap<>();
        FileUtils.getFilesList(MINECRAFT_DIRECTORY).forEach(path -> GRAPHICS_PATHS.put(path.getFileName().toString(), path));

        this.instance = GameInstance.fromFile(INSTANCE_FILE);
    }
}
