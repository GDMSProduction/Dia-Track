package com.Diatrack.Classes;

/**
 * Created by Marshall on 5/29/2018.
 */

public class UserProfileData {
    private String gender;
    private int age;
    private String height;
    private float weight;
    private int maxBlood;
    private int minBlood;
    private int targetGlucose;
    private int type;
    private float insulinSense;

    public UserProfileData() {}

    public UserProfileData(String gender, int age, String height, float weight, int maxBlood, int minBlood, int targetGlucose,int type, float insulinSens) {
        // ...
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public int getMaxBlood() {
        return maxBlood;
    }
    public int getMinBlood() {
        return minBlood;
    }
    public int getTargetGlucose() {
        return targetGlucose;
    }
    public int getType() {
        return type;
    }
    public float getInsulinSense() {
        return insulinSense;
    }

}
