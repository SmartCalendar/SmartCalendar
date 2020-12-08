package com.example.smartcalendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;


public class EditActivity extends AppCompatActivity implements View.OnClickListener {


    String [] weekDays = {" ", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String TAG = "EditActivity";

    EditText tvTitle;
    EditText tvStartDate;
    EditText tvEndDate;
    EditText tvStartTime;
    EditText tvEndTime;
    DatePickerDialog picker;

    EditText tvLocation;
    // TODO: What format is tvNotification?
    EditText tvNotification;
    EditText tvDescription;

    Button btnClose;
    Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

        tvTitle = findViewById(R.id.etTitle);
        tvStartDate = findViewById(R.id.etStartDate);
        tvEndDate = findViewById(R.id.etEndDate);

        tvStartTime = findViewById(R.id.etStartTime);
        tvEndTime = findViewById(R.id.etEndTime);

        btnClose = findViewById(R.id.btnClose);
        btnComplete = findViewById(R.id.btnComplete);


        // should we leave this as null? Try both ways first. Null makes it so user can input some incorrect format.
        tvStartDate.setInputType(InputType.TYPE_NULL);
        tvEndDate.setInputType(InputType.TYPE_NULL);
        tvStartTime.setInputType(InputType.TYPE_NULL);
        tvEndTime.setInputType(InputType.TYPE_NULL);

        // if this activity was launched from DailyViewActivity (specifically the launchEditActivity(titletex, rawdateobj) function)
        // later try to convert this into Enums so the other activities that launch into EditActivity can distinguish where it came from
        if (getIntent().getStringExtra("Sender is DailyView").equals("True")) {

            String receivedmovie = getIntent().getStringExtra("Complete Title");
            Date receiveddate = (Date) getIntent().getSerializableExtra("Complete Date Object");
            Calendar smartcal3 = Calendar.getInstance();
            smartcal3.setTime(receiveddate);

            tvStartDate.setText(weekDays[smartcal3.get(Calendar.DAY_OF_WEEK)] + "," + monthNames[smartcal3.get(Calendar.MONTH)] + " " + smartcal3.get(Calendar.DAY_OF_MONTH));
            tvTitle.setText(receivedmovie);
            // TODO: set the time as well (parse the 19:00 in the Board Election example

        }

        // Dates will open up Android Datepicker. Times will open up Android Timepicker
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);



        // end of OnCreate
    }

    final Calendar cldr = Calendar.getInstance();
    //default method for onCLick events
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.etStartDate:
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(EditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvStartDate.setText(weekDays[cldr.get(Calendar.DAY_OF_WEEK)] + "," + monthNames[monthOfYear] + " " + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
                break;

            case R.id.etEndDate:
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(EditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvEndDate.setText(weekDays[cldr.get(Calendar.DAY_OF_WEEK)] + "," + monthNames[monthOfYear] + " " + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
                break;

            // TODO: add cases for Time Spinner Widgets (listener on R.id.etStartTime & R.id.etEndTime
            // TODO: How to handle {"Add Notifications"}? Google this
            // TODO: Add cases for the {x} in upper left, and the {Done} in the upper right. Done saves Event object and sends ObjectID to Parse backend. x cancels the activity & goes back?
                // TODO: Figure out how to save a Date Format to be sent in the Parse Event

            // TODO: Cleanup and polish the color scheme for the DatePicker Widget

            default:
                break;
        }
    }

    // notes from Ankit on setting abstract method for initiating the EditActivity instance that other activities can use and pass custom parameters into
//    public static void newinstance() {
//        Intent autofill = new Intent(DailyViewActivity.this, EditActivity.class);
//
//        autofill.putExtra("Complete Title", title);
//        autofill.putExtra("Complete Date Object", rawdate);
//        autofill.putExtra("Sender is DailyView", "True");
//
//        startActivity(autofill);
//    }

}
