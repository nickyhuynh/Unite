package com.powergroup.unite.app;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bummy on 4/2/17.
 */

public enum Profile {
    INSTANCE;

    public ProfileInfo info;

    Profile() {
        info = new ProfileInfo("", "", "", "", "", new String[0], new String[0], "", "");
    }

    public static class ProfileInfo {
        public String name;
        public String age;
        public String bio;
        public String nation;
        public String gender;
        public List<String> ethnicities;
        public List<String> languages;
        public String pic;
        public String id;

        public HashMap<String, ProfileInfo> friends;
        public List<String> history;

        public ProfileInfo() {
        }

        public ProfileInfo(String name, String age, String bio, String nation, String gender, String[] ethnicities, String[] languages, String pic, String id) {
            this.name = name;
            this.age = age;
            this.bio = bio;
            this.nation = nation;
            this.gender = gender;
            this.ethnicities = Arrays.asList(ethnicities);
            this.languages = Arrays.asList(languages);
            this.pic = pic;
            this.id = id;
            friends = new HashMap<>();
            history = new ArrayList<>();
        }

        @Exclude
        public void setInfo(String name, String age, String bio, String nation, String gender, String[] ethnicities, String[] languages, String pic, String id, HashMap<String, ProfileInfo> friends, List<String> history) {
            this.name = name;
            this.age = age;
            this.bio = bio;
            this.nation = nation;
            this.gender = gender;
            this.ethnicities = Arrays.asList(ethnicities);
            this.languages = Arrays.asList(languages);
            this.pic = pic;
            this.id = id;
            this.friends = friends;
            this.history = history;
        }
    }
}
