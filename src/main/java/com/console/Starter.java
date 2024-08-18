package com.console;

import com.console.application.Console;
import com.console.launch.Downloader;
import com.console.utils.ConsoleConstants;
import javafx.application.Application;

import java.io.IOException;

public class Starter {
    public static void main(String[] args) {
//        try {
//            Downloader.downloadGithubRep("https://github.com/InkyDemon/Console-games/archive/refs/heads/assets.zip", ConsoleConstants.ASSETS_DIRECTORY);
//            Downloader.downloadGithubRep("https://github.com/InkyDemon/Console-games/archive/refs/heads/games.zip", ConsoleConstants.GAMES_DIRECTORY);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        Application.launch(Console.class, args);
    }
}
