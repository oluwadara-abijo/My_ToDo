package com.dara.mytodo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * This models the category that each To-Do item belongs to
 */

@Entity(tableName = "list_table")
@ForeignKey(entity = ToDoItem.class, parentColumns = {"title", "details", "category", "date",
        "time", "isCompleted"}, childColumns = {"title", "items"})
public class ToDoList implements Parcelable {
    @NonNull
    @PrimaryKey
    private String title;
    private List<ToDoItem> items;

    // Constructor
    public ToDoList(@NotNull String title, List<ToDoItem> items) {
        this.title = title;
        this.items = items;
    }

    // Getters and setters
    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public List<ToDoItem> getItems() {
        return items;
    }

    public void setItems(List<ToDoItem> items) {
        this.items = items;
    }

    // Parcelable implementation
    private ToDoList(Parcel in) {
        title = Objects.requireNonNull(in.readString());
        items = in.createTypedArrayList(ToDoItem.CREATOR);
    }

    public static final Creator<ToDoList> CREATOR = new Creator<ToDoList>() {
        @Override
        public ToDoList createFromParcel(Parcel in) {
            return new ToDoList(in);
        }

        @Override
        public ToDoList[] newArray(int size) {
            return new ToDoList[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(items);
    }
}
