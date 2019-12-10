package com.dara.mytodo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This interface contains all the methods used for accessing the database
 */

@Dao
public interface ToDoDao {

    @Insert()
    void insertList(ToDoList toDoList);

    @Insert()
    void insertItem(ToDoItem toDoItem);

    @Update()
    void updateList(ToDoList toDoList);

    @Update()
    void updateItem(ToDoItem toDoItem);

    @Delete()
    void deleteList(ToDoList toDoList);

    @Delete()
    void deleteItem(ToDoItem toDoItem);

    @Query("SELECT * FROM list_table")
    LiveData<List<ToDoList>> getLists();

    @Query("SELECT * FROM to_do_items_table WHERE title==:listTitle ")
    LiveData<List<ToDoItem>> getToDoItems(String listTitle);
}
