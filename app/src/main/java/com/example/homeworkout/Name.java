package com.example.homeworkout;

import androidx.annotation.NonNull;

public enum Name {
    PULLUPS,
    PUSHUPS,
    SQUATS;

    @NonNull
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
