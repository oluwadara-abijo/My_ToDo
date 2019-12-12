package com.dara.mytodo;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<ToDoItem> stringToItemsList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ToDoItem>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String itemsListToString(List<ToDoItem> someObjects) {
        return gson.toJson(someObjects);
    }
}
