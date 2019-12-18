package com.dara.mytodo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {

    private ToDoRepository mRepository;

    private LiveData<List<ToDoItem>> items;
    private LiveData<List<ToDoList>> lists;

    public ToDoViewModel (Application application) {
        super(application);
        mRepository = new ToDoRepository(application);
        items = mRepository.getItems();
        lists = mRepository.getLists();
    }

    LiveData<List<ToDoItem>> getItems() { return items; }
    LiveData<List<ToDoList>> getLists() { return lists; }

    public void insertItem(ToDoItem toDoItem) {
        mRepository.insertItem(toDoItem);
    }

    public void insertList(ToDoList toDoList) {
        mRepository.insertList(toDoList);
    }
}
