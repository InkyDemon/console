package com.console.json;

import com.console.utils.ConsoleConstants;

import java.util.ArrayList;

public class Preferences {
    public Profile profile;
    public Settings settings;

    public Preferences(Profile profile, Settings settings) {
        this.profile = profile;
        this.settings = settings;
    }

    public static Preferences getDefaultPreferences() {
        Profile profile = new Profile("", "");
        Settings settings = new Settings(512, 2048, ConsoleConstants.DEFAULT_JAVA_ARGUMENTS);

        return new Preferences(profile, settings);
    }

    public static class Profile {
        public String nickname;
        public String uuid;

        public Profile(String nickname, String uuid) {
            this.nickname = nickname;
            this.uuid = uuid;
        }
    }

    public static class Settings {
        public int min_ram;
        public int max_ram;

        public ArrayList<String> java_arguments;

        public Settings(int min_ram, int max_ram, ArrayList<String> java_arguments) {
            this.min_ram = min_ram;
            this.max_ram = max_ram;

            this.java_arguments = java_arguments;
        }
    }
}
