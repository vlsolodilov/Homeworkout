package com.example.homeworkout.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkout.Constants;
import com.example.homeworkout.DataStorage;
import com.example.homeworkout.Name;
import com.example.homeworkout.R;
import com.example.homeworkout.RepsActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExerciseLevelAdapter extends RecyclerView.Adapter<ExerciseLevelAdapter.MyViewHolder> {
    private Context context;
    private Name name;
    private List<Item> itemList;


    public ExerciseLevelAdapter(Context context, Name name) {
        this.context = context;
        this.name = name;
        this.itemList = fillItemList();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_pullup_layout, parent, false);
        return new MyViewHolder(view, context, name, itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.setData(item.getLevel(), item.getSets());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvLevel, tvSets;
        private Context context;
        private List<Item> itemList;
        private DataStorage dataStorage;
        private Name name;

        public MyViewHolder(@NonNull View itemView, Context context, Name name, List<Item> itemList) {
            super(itemView);
            this.context = context;
            this.name = name;
            this.itemList = itemList;
            tvLevel = itemView.findViewById(R.id.tvLevel);
            tvSets = itemView.findViewById(R.id.tvSets);
            itemView.setOnClickListener(this);
        }

        public void setData(String level, String sets){
            tvLevel.setText("Уровень " + level);
            tvSets.setText(sets);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, RepsActivity.class);
            int level = getAdapterPosition() + 1;
            i.putExtra(Constants.NAME, name);
            i.putExtra(Constants.LEVEL, level);
            DataStorage dataStorage = new DataStorage(context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE));
            dataStorage.saveLevel(name, level);
            dataStorage.saveDate(name);
            context.startActivity(i);
        }
    }

    private List<Item> fillItemList() {
        List<Item> itemList = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            String idString = name.toString() + "_" + "level"+ "_" + i;
            int id = context.getResources().getIdentifier(idString, "array", context.getPackageName());
            int[] reps = context.getResources().getIntArray(id);
            StringBuilder stringBuilder = new StringBuilder();
            for (int r : reps) {
                stringBuilder.append(r).append(" ");
            }
            itemList.add(new Item(String.valueOf(i), stringBuilder.toString()));
        }
        return itemList;
    }

    private static class Item {
        private String level;
        private String sets;


        public Item(String level, String sets) {
            this.level = level;
            this.sets = sets;
        }

        public String getLevel() {
            return level;
        }

        public String getSets() {
            return sets;
        }
    }

}
