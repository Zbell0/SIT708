package com.example.a41p;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;
    private final LiveData<List<Task>> incompleteTasks;
    private final LiveData<List<Task>> completedTasks;

    public TaskRepository(Application application) {
        TaskDatabase db = TaskDatabase.getInstance(application);
        taskDao = db.taskDao();
        incompleteTasks = taskDao.getIncompleteTasks();
        completedTasks = taskDao.getCompletedTasks();
    }

    public void insert(Task task) {
        new InsertTaskAsync(taskDao).execute(task);
    }

    public void update(Task task) {
        new UpdateTaskAsync(taskDao).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsync(taskDao).execute(task);
    }

    public LiveData<List<Task>> getIncompleteTasks() {
        return incompleteTasks;
    }

    public LiveData<List<Task>> getCompletedTasks() {
        return completedTasks;
    }

    private static class InsertTaskAsync extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;

        InsertTaskAsync(TaskDao dao) {
            this.taskDao = dao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsync extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;

        UpdateTaskAsync(TaskDao dao) {
            this.taskDao = dao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsync extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;

        DeleteTaskAsync(TaskDao dao) {
            this.taskDao = dao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }
}