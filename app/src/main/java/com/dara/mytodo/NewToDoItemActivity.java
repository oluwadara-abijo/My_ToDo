package com.dara.mytodo;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class NewToDoItemActivity extends AppCompatActivity {

    public static final String EXTRA_NEW_TODO = "to_do_extra";
    private final String CHANNEL_ID = "123";

    //UI elements
    private EditText titleEditText;
    private EditText detailsEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText categoryEditText;

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

        dateEditText.setOnClickListener(v -> showDatePicker());
        timeEditText.setOnClickListener(v -> showTimePicker());

        final MaterialButton button = findViewById(R.id.btn_save);
        button.setOnClickListener(view -> {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(titleEditText.getText())) {
                setResult(RESULT_CANCELED, intent);
            } else {
                String title = titleEditText.getText().toString();
                String details = detailsEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String time = timeEditText.getText().toString();
                String category = categoryEditText.getText().toString();

                ToDoItem toDoItem = new ToDoItem(title, details, date, time, category,
                        false);
                intent.putExtra(EXTRA_NEW_TODO, toDoItem);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }

    /**
     * This displays a date picker when the start date field is clicked
     * It also displays the date picked
     */
    private void showDatePicker() {
        // Use the current time as the default values for the picker
        Calendar c = Calendar.getInstance();
        int calendarYear = c.get(Calendar.YEAR);
        int calendarMonth = c.get(Calendar.MONTH);
        int calendarDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month,
                                                                        dayOfMonth)
                -> dateEditText.setText(DateUtils.formatDate(dayOfMonth, month, year)),
                calendarYear, calendarMonth, calendarDay);

        datePickerDialog.show();
    }

    /**
     * This displays a time picker when the start time field is clicked
     * It also displays the date picked
     */
    private void showTimePicker() {
        // Use the current time as the default values for the picker
        Calendar c = Calendar.getInstance();
        int calendarHour = c.get(Calendar.HOUR_OF_DAY);
        int calendarMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute)
                -> timeEditText.setText(DateUtils.formatTime(hourOfDay, minute)), calendarHour,
                calendarMinute, true);
        timePickerDialog.show();

    }

    private void createNotification() {
        // Create a pending intent for MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setContentTitle("My ToDo")
                .setContentText("Your task starts now")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(123, builder.build());

    }

    private void createNotificationChannel() {
        // Create notification channel for Android O upwards
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNotificationChannel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        createNotification();
    }
}
