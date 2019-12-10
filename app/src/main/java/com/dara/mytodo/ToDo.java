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
public class ToDo implements Parcelable {
    @NonNull
    @PrimaryKey
    private String title;
    private String details;
    private List category;
    private String date;
    private String time;
    private Boolean isCompleted;

    // Constructor
    public ToDo(@NonNull String title, String details, List category, String date,
                String time, Boolean isCompleted) {
        this.title = title;
        this.details = details;
        this.category = category;
        this.date = date;
        this.time = time;
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

    public List getCategory() {
        return category;
    }

    public void setCategory(List category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    // Parcelable implementation
    private ToDo(Parcel in) {
        title = Objects.requireNonNull(in.readString());
        details = in.readString();
        category = in.readParcelable(List.class.getClassLoader());
        date = in.readString();
        time = in.readString();
        byte tmpIsCompleted = in.readByte();
        isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
    }

    public static final Creator<ToDo> CREATOR = new Creator<ToDo>() {
        @Override
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        @Override
        public ToDo[] newArray(int size) {
            return new ToDo[size];
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
        dest.writeParcelable(category, flags);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeByte((byte) (isCompleted == null ? 0 : isCompleted ? 1 : 2));
    }

}
