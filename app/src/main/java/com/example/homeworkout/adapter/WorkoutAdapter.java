package com.example.homeworkout.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkout.Constants;
import com.example.homeworkout.DataStorage;
import com.example.homeworkout.Name;
import com.example.homeworkout.LevelsActivity;
import com.example.homeworkout.R;
import com.example.homeworkout.RepsActivity;
import com.example.homeworkout.entity.Exercise;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.MyViewHolder> {
    private Context context;
    private List<Exercise> exerciseList;



    public WorkoutAdapter(Context context) {
        this.context = context;
        exerciseList = fillExerciseList();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_exercise_layout, parent, false);
        return new MyViewHolder(view, context, exerciseList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.setData(exercise.getTitle(), exercise.getLevel(), exercise.getDate(), exercise.getTotal());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cvExercise;
        private TextView tvExerciseName, tvDate, tvTotalReps;
        private Button bLevel;
        private LinearLayout lineWiteDate, lineWiteTotal;
        private Context context;
        private List<Exercise> exerciseList;
        private final int[] CELL_COLOR = {R.color.blue_500, R.color.red, R.color.orange};

        public MyViewHolder(@NonNull View itemView, Context context, List<Exercise> exerciseList) {
            super(itemView);
            this.context = context;
            this.exerciseList = exerciseList;
            cvExercise = itemView.findViewById(R.id.cvExercise);
            tvExerciseName = itemView.findViewById(R.id.tvExerciseName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotalReps = itemView.findViewById(R.id.tvTotalReps);
            bLevel = itemView.findViewById(R.id.bLevel);
            lineWiteDate = itemView.findViewById(R.id.lineWithDate);
            lineWiteTotal = itemView.findViewById(R.id.lineWithTotal);
            itemView.setOnClickListener(this);
        }

        public void setData(String name, int level, long date, int total){

            cvExercise.setCardBackgroundColor(context.getResources().getColor(CELL_COLOR[getAdapterPosition() % CELL_COLOR.length]));
            tvExerciseName.setText(name);

            if (level == 0) {
                bLevel.setVisibility(View.GONE);
            } else {
                bLevel.setVisibility(View.VISIBLE);
                bLevel.setText(level + " " + context.getString(R.string.level));
            }

            if (date == 0) {
                lineWiteDate.setVisibility(View.GONE);
            } else {
                lineWiteDate.setVisibility(View.VISIBLE);
                int daysAgo = Math.round((new Date().getTime() - date)/(1000*60*60*24));
                tvDate.setText(daysAgo == 0 ? "сегодня" : String.format("%d дн. назад", daysAgo));
            }

            if (total == 0) {
                lineWiteTotal.setVisibility(View.GONE);
            } else {
                lineWiteTotal.setVisibility(View.VISIBLE);
                tvTotalReps.setText(String.valueOf(total));
            }
            bLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Exercise exercise = exerciseList.get(getAdapterPosition());
                    Intent i = new Intent(context, LevelsActivity.class);
                    i.putExtra(Constants.NAME, exercise.getName());
                    i.putExtra(Constants.TITLE, exercise.getTitle());
                    context.startActivity(i);
                }
            });
        }

        @Override
        public void onClick(View v) {

            Exercise exercise = exerciseList.get(getAdapterPosition());
            if (exercise.getLevel() == 0) {
                Intent i = new Intent(context, LevelsActivity.class);
                i.putExtra(Constants.NAME, exercise.getName());
                i.putExtra(Constants.TITLE, exercise.getTitle());
                context.startActivity(i);
            } else {
                Intent i = new Intent(context, RepsActivity.class);
                i.putExtra(Constants.NAME, exercise.getName());
                i.putExtra(Constants.LEVEL, exercise.getLevel());
                context.startActivity(i);
            }
        }
    }

    private List<Exercise> fillExerciseList() {
        List<Exercise> exerciseList = new ArrayList<>();

        DataStorage dataStorage = new DataStorage(context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE));

        exerciseList.add(new Exercise(Name.PULLUPS,
                context.getResources().getString(R.string.pullups),
                dataStorage.loadLevel(Name.PULLUPS),
                dataStorage.loadDate(Name.PULLUPS),
                dataStorage.loadTotal(Name.PULLUPS)));
        exerciseList.add(new Exercise(Name.PUSHUPS,
                context.getResources().getString(R.string.pushups),
                dataStorage.loadLevel(Name.PUSHUPS),
                dataStorage.loadDate(Name.PUSHUPS),
                dataStorage.loadTotal(Name.PUSHUPS)));
        exerciseList.add(new Exercise(Name.SQUATS,
                context.getResources().getString(R.string.squats),
                dataStorage.loadLevel(Name.SQUATS),
                dataStorage.loadDate(Name.SQUATS),
                dataStorage.loadTotal(Name.SQUATS)));
        notifyDataSetChanged();
        return exerciseList;
    }
}
