package com.example.homeworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

public class RepsActivity extends AppCompatActivity {
    private TextView tvReps1, tvReps2, tvReps3, tvReps4, tvReps5, tvSummReps, tvTotal;
    private TextView tvCurrentReps, tvCurrentTime;
    private FloatingActionButton fabDecreaseReps, fabIncreaseReps;
    private ExtendedFloatingActionButton fabDecreaseTime, fabIncreaseTime;
    private Button bDone, bStop;
    private ProgressBar progressBar;

    private ConstraintLayout clReps, clTimer;

    private Name name;
    private int level;
    private int[] reps;
    private int count;
    private int summReps;
    private int deltaReps;
    private long remainingTime;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reps);

        getMyIntents();
        init();
        setTitle(getActivityTitle(name));
    }

    private void init(){
        clReps = findViewById(R.id.clReps);
        clTimer = findViewById(R.id.clTimer);

        tvReps1 = findViewById(R.id.tvReps1);
        tvReps2 = findViewById(R.id.tvReps2);
        tvReps3 = findViewById(R.id.tvReps3);
        tvReps4 = findViewById(R.id.tvReps4);
        tvReps5 = findViewById(R.id.tvReps5);
        tvSummReps = findViewById(R.id.tvSummReps);
        tvTotal = findViewById(R.id.tvTotal);
        tvCurrentReps = findViewById(R.id.tvCurrentReps);
        if (level != 0)
            fillReps(level);
        else
            finish();
        tvCurrentReps.setText(String.valueOf(reps[count]));
        changeTextColor(count);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);

        progressBar = findViewById(R.id.progressBar);

        fabDecreaseReps = findViewById(R.id.fabDecreaseReps);
        fabIncreaseReps = findViewById(R.id.fabIncreaseReps);
        fabDecreaseReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentReps = Integer.parseInt(tvCurrentReps.getText().toString());
                if (currentReps > 0) {
                    currentReps--;
                    deltaReps--;
                }
                tvCurrentReps.setText(String.valueOf(currentReps));
            }
        });
        fabIncreaseReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentReps = Integer.parseInt(tvCurrentReps.getText().toString());
                currentReps++;
                deltaReps++;
                tvCurrentReps.setText(String.valueOf(currentReps));
            }
        });

        bDone = findViewById(R.id.bDone);
        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deltaReps != 0) {
                    changeReps(count);
                    changeTotal(deltaReps);
                }
                summReps += Integer.parseInt(tvCurrentReps.getText().toString());
                count++;
                if (count > 4) {
                    Intent i = new Intent(RepsActivity.this, MainActivity.class);
                    DataStorage dataStorage = new DataStorage(getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE));
                    dataStorage.saveTotal(name, summReps);
                    startActivity(i);
                } else {
                    tvCurrentReps.setText(String.valueOf(reps[count]));
                    tvSummReps.setText(String.valueOf(summReps));
                    resetTextColor();
                    changeTextColor(count);
                    clTimer.setVisibility(View.VISIBLE);
                    clReps.setVisibility(View.GONE);
                    createNewTimer(90000);
                }
            }
        });

        fabDecreaseTime = findViewById(R.id.fabDecreaseTime);
        fabIncreaseTime = findViewById(R.id.fabIncreaseTime);
        fabDecreaseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseTime(30000);
            }
        });
        fabIncreaseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseTime(30000);
            }
        });

        bStop = findViewById(R.id.bStop);
        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.onFinish();
                }
            }
        });
    }

    private void getMyIntents(){

        Intent i = getIntent();
        if(i != null){
            name = (Name) i.getSerializableExtra(Constants.NAME);
            level = i.getIntExtra(Constants.LEVEL, 0);
        }
    }

    private void fillReps(int level) {
        String idString = name.toString() + "_" +"level" + "_" + level;
        int id = getResources().getIdentifier(idString, "array", getPackageName());
        reps = Arrays.copyOf(getResources().getIntArray(id), 5);
        int total = 0;
        for (int r : reps)
            total += r;
        tvReps1.setText(reps[0] == 0 ? "" : String.valueOf(reps[0]));
        tvReps2.setText(reps[1] == 0 ? "" : String.valueOf(reps[1]));
        tvReps3.setText(reps[2] == 0 ? "" : String.valueOf(reps[2]));
        tvReps4.setText(reps[3] == 0 ? "" : String.valueOf(reps[3]));
        tvReps5.setText(reps[4] == 0 ? "" : String.valueOf(reps[4]));
        tvTotal.setText(String.valueOf(total));
    }

    private void changeTextColor(int count) {
        int color = getResources().getColor(R.color.purple_500);
        switch (count) {
            case 0:
                tvReps1.setTextColor(color);
                break;
            case 1:
                tvReps2.setTextColor(color);
                break;
            case 2:
                tvReps3.setTextColor(color);
                break;
            case 3:
                tvReps4.setTextColor(color);
                break;
            case 4:
                tvReps5.setTextColor(color);
                break;
            default:
                resetTextColor();
                break;
        }

    }

    private void resetTextColor() {
        int color = getResources().getColor(R.color.black);
        tvReps1.setTextColor(color);
        tvReps2.setTextColor(color);
        tvReps3.setTextColor(color);
        tvReps4.setTextColor(color);
        tvReps5.setTextColor(color);
    }

    private void changeReps(int count) {
        switch (count) {
            case 0:
                tvReps1.setText(tvCurrentReps.getText().toString());
                break;
            case 1:
                tvReps2.setText(tvCurrentReps.getText().toString());
                break;
            case 2:
                tvReps3.setText(tvCurrentReps.getText().toString());
                break;
            case 3:
                tvReps4.setText(tvCurrentReps.getText().toString());
                break;
            case 4:
                tvReps5.setText(tvCurrentReps.getText().toString());
                break;
        }
    }

    private void changeTotal(int delta) {
        int total = Integer.parseInt(tvTotal.getText().toString());
        tvTotal.setText(String.valueOf(total + delta));
        deltaReps = 0;
    }

    private void increaseTime(long increaseTimeInMillis) {
        createNewTimer(remainingTime + increaseTimeInMillis);
    }

    private void decreaseTime(long decreasedTimeInMillis) {
        if (remainingTime > decreasedTimeInMillis)
            createNewTimer(remainingTime - decreasedTimeInMillis);
        else
            timer.onFinish();
    }

    private void createNewTimer(long timeInMillis) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(timeInMillis, 1000) {

            @Override
            public void onTick(final long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                int m = (int) ((millisUntilFinished / 1000) / 60);
                int s = (int) ((millisUntilFinished / 1000) % 60);
                tvCurrentTime.setText(String.format("%02d:%02d", m, s));
                progressBar.setMax(90000);
                progressBar.setProgress((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                clTimer.setVisibility(View.GONE);
                clReps.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private String getActivityTitle(Name name) {
        String title;
        switch (name) {
            case PULLUPS:
                title = getString(R.string.pullups);
                break;
            case PUSHUPS:
                title = getString(R.string.pushups);
                break;
            case SQUATS:
                title = getString(R.string.squats);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return title +" "+level+" уровень";
    }

}