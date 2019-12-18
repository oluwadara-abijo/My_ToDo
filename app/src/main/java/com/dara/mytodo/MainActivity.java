package com.dara.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private ToDoViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final ToDoListAdapter adapter = new ToDoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);

        // Update the cached copy of the words in the adapter.
        mViewModel.getItems().observe(this, adapter::setItems);

        // Set click listener on FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            List<ToDoItem> itemList = mViewModel.getItems().getValue();
            for (ToDoItem item : itemList) {
                Log.d("ITEMS>>>", item.toString());
            }

            Intent intent = new Intent(MainActivity.this, NewToDoItemActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);

        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            ToDoItem toDoItem = data.getParcelableExtra(NewToDoItemActivity.EXTRA_REPLY);
            mViewModel.insertItem(toDoItem);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
