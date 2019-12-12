package com.dara.mytodo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ToDoRepository {

    private ToDoDao mDao;

    private LiveData<List<ToDoItem>> items;
    private LiveData<List<ToDoList>> lists;

    private ToDoRepository(Application application) {
        ToDoRoomDatabase database = ToDoRoomDatabase.getDatabase(application);
        mDao = database.toDoDao();
        items = mDao.getToDoItems();
        lists = mDao.getLists();

    }

    LiveData<List<ToDoItem>> getItems() {
        return items;
    }

    LiveData<List<ToDoList>> getLists() {
        return lists;
    }

    void insertItem(ToDoItem toDoItem) {
        ToDoRoomDatabase.databaseWriteExecutor.execute(() -> mDao.insertItem(toDoItem));
    }

    void insertList(ToDoList toDoList) {
        ToDoRoomDatabase.databaseWriteExecutor.execute(() -> mDao.insertList(toDoList));
    }
}
