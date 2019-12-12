package com.dara.mytodo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ToDoItem.class, ToDoList.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class ToDoRoomDatabase extends RoomDatabase {

    // Get an instance of ToDoDao
    public abstract ToDoDao toDoDao();

    private static volatile ToDoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.
            newFixedThreadPool(NUMBER_OF_THREADS);

    static ToDoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ToDoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoRoomDatabase.class, "todo_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ToDoDao dao = INSTANCE.toDoDao();

                ToDoItem item = new ToDoItem("First item", "Just a test", "Tests",
                        "12-12-19", "12:00", false);
                dao.insertItem(item);

                item = new ToDoItem("Second item", "Just another test", "Tests",
                        "13-12-19", "18:00", true);
                dao.insertItem(item);
            });
        }
    };
}
