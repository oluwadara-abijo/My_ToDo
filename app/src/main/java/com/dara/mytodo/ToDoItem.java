package com.dara.mytodo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    private String startDate;
    private String startTime;
    private Boolean isCompleted;

    // Constructor
    public ToDoItem(@NonNull String title, String details, String category, String startDate,
                    String startTime, Boolean isCompleted) {
        this.title = title;
        this.details = details;
        this.category = category;
        this.startDate = startDate;
        this.startTime = startTime;
        this.isCompleted = isCompleted;
    }

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    // Parcelable implementation
    private ToDoItem(Parcel in) {
        title = Objects.requireNonNull(in.readString());
        details = in.readString();
        category = in.readString();
        startDate = in.readString();
        startTime = in.readString();
        byte tmpIsCompleted = in.readByte();
        isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(details);
        dest.writeString(category);
        dest.writeString(startDate);
        dest.writeString(startTime);
        dest.writeByte((byte) (isCompleted == null ? 0 : isCompleted ? 1 : 2));
    }

}
