package com.example.homeworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.homeworkout.adapter.ExerciseLevelAdapter;

public class LevelsActivity extends AppCompatActivity {
    private RecyclerView rvExerciseLevel;
    private ExerciseLevelAdapter exerciseLevelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullup);

        Intent i = getIntent();
        Name name = (Name) i.getSerializableExtra(Constants.NAME);
        String title = i.getStringExtra(Constants.TITLE);

        setTitle(title);
        rvExerciseLevel = (RecyclerView) findViewById(R.id.rvExerciseLevel);
        rvExerciseLevel.setLayoutManager(new LinearLayoutManager(this));
        exerciseLevelAdapter = new ExerciseLevelAdapter(this, name);
        rvExerciseLevel.setAdapter(exerciseLevelAdapter);
    }
}