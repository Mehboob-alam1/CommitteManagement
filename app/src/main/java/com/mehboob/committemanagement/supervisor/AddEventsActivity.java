package com.mehboob.committemanagement.supervisor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.databinding.ActivityAddEventsBinding;

import java.util.Calendar;
import java.util.Locale;

public class AddEventsActivity extends AppCompatActivity {
    ActivityAddEventsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog();
            }
        });
    }

    private void showDateTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar timeCalendar = Calendar.getInstance();
                        int hourOfDay = timeCalendar.get(Calendar.HOUR_OF_DAY);
                        int minute = timeCalendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                AddEventsActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String selectedDateTime = dayOfMonth + "/" + (month + 1) + "/" + year + " " +
                                                String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                        binding.etEventDurations.setText(selectedDateTime);
                                    }
                                },
                                hourOfDay, minute, true);

                        timePickerDialog.show();
                    }
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }
}