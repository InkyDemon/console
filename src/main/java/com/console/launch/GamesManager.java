package com.console.launch;

import com.console.utils.ConsoleConstants;
import com.console.utils.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GamesManager {
    private final List<Game> GAMES;

    public GamesManager() {
        this.GAMES = new ArrayList<>();
        this.updateGames();
    }

    public List<Game> getGames() {
        return GAMES;
    }

    public void updateGames() {
        ArrayList<Path> gamesPaths = FileUtils.getFilesList(ConsoleConstants.GAMES_DIRECTORY, (path) -> Files.isDirectory(path) && Files.exists(path.resolve("instance.json")));

        gamesPaths.forEach(path -> {
            Game game = new Game(path);

            GAMES.add(game);
        });
    }
}
