package com.example.zavaso;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EventFragment extends Fragment {
    private EditText eventName,eventLocation,eventVenue,eventDate,eventStartTime,eventEndTime;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private Button saveEvent;
    private DatabaseReference databaseReference;
    private String userId;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_event, container, false);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        eventName=(EditText)v.findViewById(R.id.event_name);
        eventLocation=(EditText)v.findViewById(R.id.event_location);
        eventVenue=(EditText)v.findViewById(R.id.venue_address);
        eventDate=(EditText)v.findViewById(R.id.event_date);
        eventStartTime=(EditText)v.findViewById(R.id.event_start_time);
        eventEndTime=(EditText)v.findViewById(R.id.event_end_time);
        saveEvent=(Button)v.findViewById(R.id.save_event);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("event").child(userId);
        eventDate.setInputType(InputType.TYPE_NULL);
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c1=Calendar.getInstance();
                int day=c1.get(Calendar.DAY_OF_MONTH);
                int month=c1.get(Calendar.MONTH);
                int year=c1.get(Calendar.YEAR);
                datePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        eventDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },year,month,day);
                datePicker.show();
            }
        });
        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c2=Calendar.getInstance();
                int hour=c2.get(Calendar.HOUR_OF_DAY);
                int minute=c2.get(Calendar.MINUTE);
                timePicker=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay>=0 && hourOfDay<12){
                            eventStartTime.setText(hourOfDay+":"+minute+"AM");
                        }
                        if(hourOfDay==12){
                            eventStartTime.setText(hourOfDay+":"+minute+"PM");
                        }
                        if(hourOfDay>12 && hourOfDay<24){
                            eventStartTime.setText(hourOfDay-12+":"+minute+"PM");
                        }
                    }
                },hour,minute,true);
                timePicker.show();
            }
        });
        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c3=Calendar.getInstance();
                int hour=c3.get(Calendar.HOUR_OF_DAY);
                int minute=c3.get(Calendar.MINUTE);
                timePicker=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay>=0 && hourOfDay<12){
                            eventEndTime.setText(hourOfDay+":"+minute+"AM");
                        }
                        if(hourOfDay==12){
                            eventEndTime.setText(hourOfDay+":"+minute+"PM");
                        }
                        if(hourOfDay>12 && hourOfDay<24){
                            eventEndTime.setText(hourOfDay-12+":"+minute+"PM");
                        }
                    }
                },hour,minute,true);
                timePicker.show();
            }
        });
        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEventName=eventName.getText().toString().trim();
                String inputEventLocation=eventLocation.getText().toString().trim();
                String inputEventVenue=eventVenue.getText().toString().trim();
                String inputDate=eventDate.getText().toString().trim();
                String inputStartTime=eventStartTime.getText().toString().trim();
                String inputEndTime=eventEndTime.getText().toString().trim();
                if(TextUtils.isEmpty(inputDate) || TextUtils.isEmpty(inputEndTime) || TextUtils.isEmpty(inputEventLocation) || TextUtils.isEmpty(inputStartTime) || TextUtils.isEmpty(inputEventName) || TextUtils.isEmpty(inputEventVenue)){
                    Toast.makeText(getContext(),"All field are mandatory",Toast.LENGTH_SHORT).show();
                    return;
                }
                Event event=new Event(inputEventName,inputEventLocation,inputEventVenue,inputDate,inputStartTime,inputEndTime);
                databaseReference.push().setValue(event);
                Toast.makeText(getContext(),"Event saved",Toast.LENGTH_LONG).show();

            }
        });
        return v;
    }
}