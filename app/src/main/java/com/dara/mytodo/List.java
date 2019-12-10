package com.dara.mytodo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This models the category that each To-Do item belongs to
 */

@Entity(tableName = "list_table")
public class List implements Parcelable {
    @NonNull
    @PrimaryKey
    private String title;
    private ArrayList<ToDo> items;

    // Constructor
    public List(@NotNull String title, ArrayList<ToDo> items) {
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

    public ArrayList<ToDo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ToDo> items) {
        this.items = items;
    }

    // Parcelable implementation
    private List(Parcel in) {
        title = Objects.requireNonNull(in.readString());
        items = in.createTypedArrayList(ToDo.CREATOR);
    }

    public static final Creator<List> CREATOR = new Creator<List>() {
        @Override
        public List createFromParcel(Parcel in) {
            return new List(in);
        }

        @Override
        public List[] newArray(int size) {
            return new List[size];
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
