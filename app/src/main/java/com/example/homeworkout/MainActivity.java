package com.example.homeworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.example.homeworkout.adapter.WorkoutAdapter;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rvWorkout;
    private WorkoutAdapter workoutAdapter;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.workout));
        //loadPreferences();

        rvWorkout = (RecyclerView) findViewById(R.id.rvWorkout);
        rvWorkout.setLayoutManager(new LinearLayoutManager(this));
        workoutAdapter = new WorkoutAdapter(this);
        rvWorkout.setAdapter(workoutAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        workoutAdapter = new WorkoutAdapter(this);
        rvWorkout.setAdapter(workoutAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //savePreferences();
    }


    private static long back_pressed;

    @Override
    public void onBackPressed() {

        if (back_pressed + 1000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}