package com.console.launch;

import com.console.json.GameInstance;

import java.nio.file.Path;

public class Game {
    public final Path GAME_DIRECTORY;
    public final Path ASSETS_DIRECTORY;
    public final Path BACKGROUNDS_DIRECTORY;
    public final Path INSTANCE_FILE;
    public final Path MINECRAFT_DIRECTORY;
    public final Path ICON;

    public final GameInstance INSTANCE;

    public Game(Path gameDirectory) {
        this.GAME_DIRECTORY = gameDirectory;
        this.INSTANCE_FILE = GAME_DIRECTORY.resolve("instance.json");
        this.ASSETS_DIRECTORY = GAME_DIRECTORY.resolve("assets");

        this.MINECRAFT_DIRECTORY = ASSETS_DIRECTORY.resolve("minecraft");
        this.ICON = ASSETS_DIRECTORY.resolve("icon.png");
        this.BACKGROUNDS_DIRECTORY = ASSETS_DIRECTORY.resolve("backgrounds");

        this.INSTANCE = GameInstance.fromFile(INSTANCE_FILE);
    }
}
