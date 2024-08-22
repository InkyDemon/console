package com.console.launch;

import com.console.utils.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class GamesManager {
    private final Path GAMES_DIRECTORY;
    private final HashMap<String, Game> GAMES;

    public GamesManager(Path gamesDirectory) {
        this.GAMES_DIRECTORY = gamesDirectory;
        this.GAMES = new HashMap<>();

        this.updateGames();
    }

    public Path getGamesDirectory() {
        return GAMES_DIRECTORY;
    }

    public HashMap<String, Game> getGames() {
        return GAMES;
    }

    public void updateGames() {
        ArrayList<Path> gamesPaths = FileUtils.getFilesList(GAMES_DIRECTORY, (path) -> Files.isDirectory(path) && Files.exists(path.resolve("instance.json")));

        gamesPaths.forEach(path -> {
            Game game = new Game(path);

            GAMES.put(game.instance.name, game);
        });
    }
}
