package com.example.smartcalendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EditActivity extends AppCompatActivity implements View.OnClickListener, NotificationFragment.DialogListener {


    String [] weekDays = {" ", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String TAG = "EditActivity";

    TimePickerDialog timepicker;
    EditText tvTitle;
    EditText tvStartDate;
    EditText tvEndDate;
    EditText tvStartTime;
    EditText tvEndTime;
    DatePickerDialog datepicker;

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

        tvNotification = findViewById(R.id.etNotification);
        tvDescription = findViewById(R.id.etDescription);

        // should we leave this as null? Try both ways first. Null makes it so user can input some incorrect format.
        tvStartDate.setInputType(InputType.TYPE_NULL);
        tvEndDate.setInputType(InputType.TYPE_NULL);
        tvStartDate.setFocusable(false);
        tvEndDate.setFocusable(false);
        tvStartTime.setFocusable(false);
        tvEndTime.setFocusable(false);
        tvNotification.setFocusable(false);
        tvNotification.setInputType(InputType.TYPE_NULL);
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
            tvStartTime.setText(CustomTimeParser(smartcal3.get(Calendar.HOUR_OF_DAY), smartcal3.get(Calendar.MINUTE)));

        }

        // Dates will open up Android Datepicker. Times will open up Android Timepicker
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        tvNotification.setOnClickListener(this);


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
                datepicker = new DatePickerDialog(EditActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvStartDate.setText(weekDays[cldr.get(Calendar.DAY_OF_WEEK)] + "," + monthNames[monthOfYear] + " " + dayOfMonth);
                            }
                        }, year, month, day);
                datepicker.show();
                break;

            case R.id.etEndDate:
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(EditActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvEndDate.setText(weekDays[cldr.get(Calendar.DAY_OF_WEEK)] + "," + monthNames[monthOfYear] + " " + dayOfMonth);
                            }
                        }, year, month, day);
                datepicker.show();
                break;

            case R.id.etStartTime:
                // can we remember the time we selected when its clicked again? It resets the time to the current time
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);
                int amorpm = cldr.get(Calendar.AM_PM);

                timepicker = new TimePickerDialog(EditActivity.this, R.style.DialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int chosenhour, int chosenminute) {

                            // TODO: How do we format the time to be sent to the parse backend?
                            tvStartTime.setText(CustomTimeParser(chosenhour, chosenminute));

                        }
                    }, hour, minute, false);

                timepicker.show();
                break;

            case R.id.etEndTime:
                // can we remember the time we selected when its clicked again? It resets the time to the current time
                hour = cldr.get(Calendar.HOUR_OF_DAY);
                minute = cldr.get(Calendar.MINUTE);
                amorpm = cldr.get(Calendar.AM_PM);

                timepicker = new TimePickerDialog(EditActivity.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int chosenhour, int chosenminute) {

                                // TODO: How do we format the time to be sent to the parse backend?
                                tvEndTime.setText(CustomTimeParser(chosenhour, chosenminute));

                            }
                        }, hour, minute, false);

                timepicker.show();
                break;

            // TODO: Add cases for the {x} in upper left, and the {Done} in the upper right. Done saves Event object and sends ObjectID to Parse backend. x cancels the activity & goes back?
                // TODO: Figure out how to save a Date Format to be sent in the Parse Event

            case R.id.etNotification:

                showNotifDialog();

                break;

            default:
                break;
        }
    }


    // method to parse Calendar INTs returned from the get methods into a more aesthetic time format {HH:MM AM/PM}
    public static String CustomTimeParser(int inputhour, int inputminute) {
        String time = inputhour + ":" + inputminute;
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm");
        Date date = null;
        try {
            date = fmt.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

        String formattedTime = fmtOut.format(date);
        // use formattedTime in setText
        return formattedTime;

    }

    private void showNotifDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new NotificationFragment();
        dialogFragment.show(ft, "dialog");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        // TODO: Later use to setup notifications on the phone (note-to-self look at old chrome tabs)
        tvNotification.setText(inputText + " minutes before");
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
