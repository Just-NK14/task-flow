package com.example.taskflow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    public int taskId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "status") // New status field
    public String status;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "user_id")  // Foreign key linking task to user
    public int userId;

    // Default constructor (Room requires it)
    public Task() {
    }

    // Constructor with status
    public Task(String title, String description, String status, String date, String time, int userId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
        this.time = time;
        this.userId = userId;
    }

    // Getters and setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
