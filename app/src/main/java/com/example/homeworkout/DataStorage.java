package com.example.homeworkout;


import android.content.SharedPreferences;

public class DataStorage {
    private SharedPreferences preferences;

    public DataStorage(SharedPreferences preferences) {
        this.preferences = preferences;
    }


    public void saveLevel(Name name, int level) {
        SharedPreferences.Editor ed = preferences.edit();
        String key;
        switch (name) {
            case PULLUPS:
                key = Constants.PULLUPS_LEVEL;
                break;
            case PUSHUPS:
                key = Constants.PUSHUPS_LEVEL;
                break;
            case SQUATS:
                key = Constants.SQUATS_LEVEL;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        ed.putInt(key, level);
        ed.apply();
    }

    public void saveDate(Name name, long date) {
        SharedPreferences.Editor ed = preferences.edit();
        String key;
        switch (name) {
            case PULLUPS:
                key = Constants.PULLUPS_DATE;
                break;
            case PUSHUPS:
                key = Constants.PUSHUPS_DATE;
                break;
            case SQUATS:
                key = Constants.SQUATS_DATE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        ed.putLong(key, date);
        ed.apply();
    }

    public void saveTotal(Name name, int total) {
        SharedPreferences.Editor ed = preferences.edit();
        String key;
        switch (name) {
            case PULLUPS:
                key = Constants.PULLUPS_TOTAL;
                break;
            case PUSHUPS:
                key = Constants.PUSHUPS_TOTAL;
                break;
            case SQUATS:
                key = Constants.SQUATS_TOTAL;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        ed.putInt(key, loadTotal(name) + total);
        ed.apply();
    }

    public int loadLevel(Name name) {
        String key;
        switch (name) {
            case PULLUPS:
                key = Constants.PULLUPS_LEVEL;
                break;
            case PUSHUPS:
                key = Constants.PUSHUPS_LEVEL;
                break;
            case SQUATS:
                key = Constants.SQUATS_LEVEL;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return preferences.getInt(key, 0);
    }

    public long loadDate(Name name) {
        String key;
        switch (name) {
            case PULLUPS:
                key = Constants.PULLUPS_DATE;
                break;
            case PUSHUPS:
                key = Constants.PUSHUPS_DATE;
                break;
            case SQUATS:
                key = Constants.SQUATS_DATE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return preferences.getLong(key, 0);
    }

    public int loadTotal(Name name) {
        String key;
        switch (name) {
            case PULLUPS:
                key = Constants.PULLUPS_TOTAL;
                break;
            case PUSHUPS:
                key = Constants.PUSHUPS_TOTAL;
                break;
            case SQUATS:
                key = Constants.SQUATS_TOTAL;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return preferences.getInt(key, 0);
    }

}
