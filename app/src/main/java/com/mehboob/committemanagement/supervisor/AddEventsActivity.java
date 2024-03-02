package com.mehboob.committemanagement.supervisor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.models.Event;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.common.viewmodels.CommitteeViewModel;
import com.mehboob.committemanagement.databinding.ActivityAddEventsBinding;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class AddEventsActivity extends AppCompatActivity {
  private   ActivityAddEventsBinding binding;
  private String comname,superv ,eventId;

  private CommitteeViewModel committeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        committeeViewModel = new ViewModelProvider(this).get(CommitteeViewModel.class);


        comname= getIntent().getStringExtra("comname");
        eventId = getIntent().getStringExtra("eventId");
        superv= getIntent().getStringExtra("superv");


        binding.etEventSupervisor.setText(superv);
        binding.imageView3.setOnClickListener(v -> showDateTimePickerDialog());

        binding.btnConfirm.setOnClickListener(v -> {
            if(binding.etEventName.getText().toString().isEmpty()){
                Toast.makeText(this, "Add Event name", Toast.LENGTH_SHORT).show();
            }else if (binding.etEventVeneu.getText().toString().isEmpty()){
                Toast.makeText(this, "Add Event Venue", Toast.LENGTH_SHORT).show();
            }else if (binding.etEventDescription.getText().toString().isEmpty()){
                Toast.makeText(this, "Add Event Description", Toast.LENGTH_SHORT).show();
            } else if (binding.etEventDurations.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add Event time and durations", Toast.LENGTH_SHORT).show();
            } else if (binding.etEventSupervisor.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add a supervisor", Toast.LENGTH_SHORT).show();
            }else{
                 binding.progressBar.setVisibility(View.VISIBLE);
                String eventId= UUID.randomUUID().toString();
                String eventName= binding.etEventName.getText().toString();
                String eventVenue= binding.etEventVeneu.getText().toString();
                String eventDesc= binding.etEventDescription.getText().toString();
                String eventDuration= binding.etEventDurations.getText().toString();
                String eventSuperVisor= binding.etEventSupervisor.getText().toString();


//                Event event= new Event(eventId,eventName,eventVenue,eventDesc,eventDuration,eventSuperVisor,"Upcoming");
                Event event;
                if (eventId != null) {
                    // Update existing event
                    event = new Event(eventId, eventName, eventVenue, eventDesc, eventDuration, eventSuperVisor, "Upcoming");
                } else {
                    // Create new event
                    eventId = UUID.randomUUID().toString();
                    event = new Event(eventId, eventName, eventVenue, eventDesc, eventDuration, eventSuperVisor, "Upcoming");
                }

                addEvent(event,comname);



            }
        });
    }

    private void addEvent(Event event, String comname) {


        committeeViewModel.addEventToCommittee(comname,event)
                .observe(this,aBoolean -> {
                    binding.progressBar.setVisibility(View.GONE);
                    if (aBoolean){
                        Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(this, "Event not added", Toast.LENGTH_SHORT).show();
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