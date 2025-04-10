package com.example.a41p;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private final TaskRepository repository;
    private final LiveData<List<Task>> incompleteTasks;
    private final LiveData<List<Task>> completedTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        incompleteTasks = repository.getIncompleteTasks();
        completedTasks = repository.getCompletedTasks();
    }

    public void insert(Task task) { repository.insert(task); }
    public void update(Task task) { repository.update(task); }
    public void delete(Task task) { repository.delete(task); }

    public LiveData<List<Task>> getIncompleteTasks() { return incompleteTasks; }
    public LiveData<List<Task>> getCompletedTasks() { return completedTasks; }
}