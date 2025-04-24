package com.example.taskflow;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks WHERE user_id = :userId AND status='Pending' ORDER BY " +
            "SUBSTR(date, 7, 4) || SUBSTR(date, 4, 2) || SUBSTR(date, 1, 2), " +
            "time LIMIT 6")
    List<Task> getTasksByUserPending(int userId);

    @Query("SELECT * FROM tasks WHERE user_id = :userId AND status='Completed' ORDER BY " +
            "SUBSTR(date, 7, 4) || SUBSTR(date, 4, 2) || SUBSTR(date, 1, 2), " +
            "time LIMIT 6")
    List<Task> getTasksByUserCompleted(int userId);

    @Query("UPDATE tasks SET status = 'Completed' WHERE status = 'Pending' AND " +
            "datetime(SUBSTR(date, 7, 4) || '-' || SUBSTR(date, 4, 2) || '-' || SUBSTR(date, 1, 2) || ' ' || " +
            "printf('%02d', CAST(SUBSTR(time, 1, INSTR(time, ':') - 1) AS INTEGER)) || ':' || " +
            "printf('%02d', CAST(SUBSTR(time, INSTR(time, ':') + 1) AS INTEGER))) <= datetime('now')")
    void updatePendingTasksToCompleted();


}
