package com.console.json;

import java.util.ArrayList;

public class Preferences {
    public Profile profile;
    public Settings settings;

    public Preferences(Profile profile, Settings settings) {
        this.profile = profile;
        this.settings = settings;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public static Preferences getDefaultPreferences() {
        Profile profile = new Profile(null, null);
        Settings settings = new Settings(512, 2048, new ArrayList<>());

        return new Preferences(profile, settings);
    }

    public static class Profile {
        public String nickname;
        public String uuid;

        public Profile(String nickname, String uuid) {
            this.nickname = nickname;
            this.uuid = uuid;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setUUID(String uuid) {
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
