package com.example.a41p;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM task_table WHERE isCompleted = 0 ORDER BY dueDate ASC")
    LiveData<List<Task>> getIncompleteTasks();

    @Query("SELECT * FROM task_table WHERE isCompleted = 1 ORDER BY dueDate ASC")
    LiveData<List<Task>> getCompletedTasks();
}