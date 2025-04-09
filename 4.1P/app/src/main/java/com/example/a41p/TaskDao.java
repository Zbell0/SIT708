package com.example.a41p;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task_table")
    void deleteAllTasks();

    @Query("SELECT * FROM task_table ORDER BY dueDate ASC")
    LiveData<List<Task>> getAllTasksSorted();

}
