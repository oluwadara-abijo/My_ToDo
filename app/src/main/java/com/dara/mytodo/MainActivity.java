package com.dara.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoListAdapter.ItemClickListener {

    public static final int CREATE_NEW_ITEM = 1;
    public static final int UPDATE_NEW_ITEM = 2;

    private ToDoViewModel mViewModel;

    // List of items
    private List<ToDoItem> itemList;

    private ToDoListAdapter adapter;

    // UI elements
    private TextView emptyListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise UI elements
        emptyListTextView = findViewById(R.id.tv_empty_todo);

        //Setup recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ToDoListAdapter(this, itemList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);

        itemList = new ArrayList<>();

        // Update the cached copy of the words in the adapter.
        mViewModel.getItems().observe(this, items -> {
            itemList = items;
            if (itemList.isEmpty()) {
                emptyListTextView.setVisibility(View.VISIBLE);
            } else {
                emptyListTextView.setVisibility(View.GONE);
            }
            adapter.setItems(itemList);

        });

        // Set click listener on FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startNewItemActivity(CREATE_NEW_ITEM));

    }

    private void startNewItemActivity(Integer requestCode) {
        Intent intent = new Intent(MainActivity.this, NewToDoItemActivity.class);
        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_NEW_ITEM && resultCode == RESULT_OK) {
            ToDoItem toDoItem = data.getParcelableExtra(NewToDoItemActivity.EXTRA_REPLY);
            mViewModel.insertItem(toDoItem);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(ToDoItem item, ToDoListAdapter.ListenerType listenerType) {
        switch (listenerType) {
            case EDIT:
                startNewItemActivity(UPDATE_NEW_ITEM);
                break;
            case MARK_DONE:
                item.setCompleted(true);
                mViewModel.updateItem(item);
                adapter.notifyDataSetChanged();
                break;
            case UNMARK_DONE:
                item.setCompleted(false);
                mViewModel.updateItem(item);
                adapter.notifyDataSetChanged();
                break;
        }

    }
}
