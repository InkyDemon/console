package com.console.launch;

import com.console.json.Preferences;
import com.console.utils.ConsoleConstants;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

public class Launcher {
    public static void launch(Preferences preferences, Path selectedGame) throws IOException {
        if (preferences.profile == null || preferences.profile.nickname == null) {

        }
        else {
            if (preferences.profile.uuid == null) {
                preferences.profile.setUUID(UUID.randomUUID().toString());
            }

            ArrayList<String> java_arguments = ConsoleConstants.DEFAULT_JAVA_ARGUMENTS;
            java_arguments.addAll(preferences.settings.java_arguments);

            ArrayList<String> finalCommand = new ArrayList<>(java_arguments);
            finalCommand.add("-Xms"+preferences.settings.min_ram+"M");
            finalCommand.add("-Xmx"+preferences.settings.max_ram+"M");
            finalCommand.add("-cp"); finalCommand.add(String.join(";", ConsoleConstants.DEFAULT_LIBRARIES));
            finalCommand.add("net.minecraft.launchwrapper.Launch"); finalCommand.addAll(ConsoleConstants.DEFAULT_MINECRAFT_ARGUMENTS);
            finalCommand.add("--username"); finalCommand.add(preferences.profile.nickname);
            finalCommand.add("--gameDir"); finalCommand.add(selectedGame.toString());
            finalCommand.add("--uuid"); finalCommand.add(preferences.profile.uuid);
            finalCommand.add("--accessToken"); finalCommand.add(preferences.profile.uuid);

            ProcessBuilder processBuilder = new ProcessBuilder(finalCommand);
            processBuilder.directory(ConsoleConstants.ASSETS_DIRECTORY.toFile());

            processBuilder.start();
        }
    }
}