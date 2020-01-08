package com.dara.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Date;

public class NewToDoItemActivity extends AppCompatActivity {

    public static final String EXTRA_NEW_TODO = "reply_extra";

    //UI elements
    private EditText titleEditText;
    private EditText detailsEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText categoryEditText;
    private EditText isCompletedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_to_do_item);

        // Initialise UI elements
        titleEditText = findViewById(R.id.editText_title);
        detailsEditText = findViewById(R.id.editText_details);
        dateEditText = findViewById(R.id.editText_date);
        timeEditText = findViewById(R.id.editText_time);
        categoryEditText = findViewById(R.id.editText_category);
//        isCompletedEditText = findViewById(R.id.editText_isCompleted);

        final MaterialButton button = findViewById(R.id.btn_save);
        button.setOnClickListener(view -> {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(titleEditText.getText())) {
                setResult(RESULT_CANCELED, intent);
            } else {
                String title = titleEditText.getText().toString();
                String details = detailsEditText.getText().toString();
                String date = dateEditText.getText().toString();
                Date time = timeEditText.getText().toString();
                String category = categoryEditText.getText().toString();
//                String status = isCompletedEditText.getText().toString();
//                boolean isCompleted = false;
//                if (status.equals("T")) {
//                    isCompleted = true;
//                }

                ToDoItem toDoItem = new ToDoItem(title, details, date, time, category, false);
                intent.putExtra(EXTRA_NEW_TODO, toDoItem);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }
}
