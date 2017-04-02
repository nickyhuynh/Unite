package com.powergroup.unite.app;

/**
 * Created by bummy on 4/2/17.
 */

public enum Profile {
    INSTANCE;

    public ProfileInfo info;

    Profile() {
        info = new ProfileInfo("", "", "", "", new String[0], new String[0], "", "");
    }

//    public void setInfo(String name, String age, String bio, String nation, String[] ethnicities, String[] languages) {
//        info.setInfo(name, age, bio, nation, ethnicities, languages);
//    }

    public class ProfileInfo {
        public String name;
        public String age;
        public String bio;
        public String nation;
        public String[] ethnicities;
        public String[] languages;
        public String pic;
        public String id;

        public ProfileInfo(String name, String age, String bio, String nation, String[] ethnicities, String[] languages, String pic, String id) {
            this.name = name;
            this.age = age;
            this.bio = bio;
            this.nation = nation;
            this.ethnicities = ethnicities;
            this.languages = languages;
            this.pic = pic;
            this.id = id;
        }

        public void setInfo(String name, String age, String bio, String nation, String[] ethnicities, String[] languages, String pic, String id) {
            this.name = name;
            this.age = age;
            this.bio = bio;
            this.nation = nation;
            this.ethnicities = ethnicities;
            this.languages = languages;
            this.pic = pic;
            this.id = id;
        }
    }
}
