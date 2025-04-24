package com.example.taskflow;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.date.setText(task.getDate());
        holder.time.setText(task.getTime());

        // Alternating row colors for better readability
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#EEEEEE")); // Light Gray
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date, time;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTaskTitle);
            description = itemView.findViewById(R.id.textTaskDescription);
            date = itemView.findViewById(R.id.textTaskDate);
            time = itemView.findViewById(R.id.textTaskTime);
        }
    }
}
