package com.console.launch;

import com.console.json.Preferences;
import com.console.utils.ConsoleConstants;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

public class Launcher {
    public static void launch(Preferences preferences, Path selectedGame) throws IOException {
        if (preferences.profile == null || preferences.profile.nickname.isEmpty()) {

        }
        else {
            preferences.profile.uuid = (preferences.profile.uuid.isEmpty() ? UUID.randomUUID().toString(): preferences.profile.uuid);
            preferences.settings.java_arguments = (preferences.settings.java_arguments.equals(new ArrayList<String>()) ? ConsoleConstants.DEFAULT_JAVA_ARGUMENTS: preferences.settings.java_arguments);

            ArrayList<String> finalCommand = new ArrayList<>();
            finalCommand.add(ConsoleConstants.JAVA_EXE.toString());
            finalCommand.addAll(preferences.settings.java_arguments);

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