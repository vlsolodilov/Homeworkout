package com.example.homeworkout.entity;

import com.example.homeworkout.Name;

public class Exercise {

    private Name name;
    private String title;
    private int level;
    private long date;
    private int total;

    public Exercise(Name name, String title, int level, long date, int total) {
        this.name = name;
        this.title = title;
        this.level = level;
        this.date = date;
        this.total = total;
    }

    public Name getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
