package com.dara.mytodo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

/**
 * This class describes the Entity (which represents the SQLite table) for the To-Do items.
 * Each public property in the class represents a column in the table.
 */

@Entity(tableName = "to_do_items_table")
public class ToDoItem implements Parcelable {
    @NonNull
    @PrimaryKey
    private String title;
    private String details;
    private String category;
    private Date startTime;
    private String time;
    private Boolean isCompleted;

    // Constructor
    public ToDoItem(@NonNull String title, String details, String category, Date date,
                    String time, Boolean isCompleted) {
        this.title = title;
        this.details = details;
        this.category = category;
        this.startTime = date;
        this.time = time;
        this.isCompleted = isCompleted;
    }

    // Parcelable implementation
    protected ToDoItem(Parcel in) {
        title = Objects.requireNonNull(in.readString());
        details = in.readString();
        category = in.readString();
        time = in.readString();
        byte tmpIsCompleted = in.readByte();
        isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(details);
        dest.writeString(category);
        dest.writeString(time);
        dest.writeByte((byte) (isCompleted == null ? 0 : isCompleted ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToDoItem> CREATOR = new Creator<ToDoItem>() {
        @Override
        public ToDoItem createFromParcel(Parcel in) {
            return new ToDoItem(in);
        }

        @Override
        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };

    // Getters and setters
    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

}
