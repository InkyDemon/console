package com.console;

import com.console.application.Console;
import com.console.launch.Downloader;
import com.console.utils.ConsoleConstants;
import javafx.application.Application;

import java.io.IOException;
import java.nio.file.Files;

public class Starter {
    public static void main(String[] args) throws IOException {
        if (!Files.exists(ConsoleConstants.ASSETS_DIRECTORY)) {
            Downloader.downloadGithubRep("https://github.com/InkyDemon/Console-games/archive/refs/heads/assets.zip", ConsoleConstants.ASSETS_DIRECTORY);
        }
        Downloader.downloadGithubRep("https://github.com/InkyDemon/Console-games/archive/refs/heads/games.zip", ConsoleConstants.GAMES_DIRECTORY);

        Application.launch(Console.class, args);
    }
}