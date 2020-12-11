package com.example.smartcalendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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
    EditText tvNotification;
    EditText tvDescription;

    Button btnClose;
    Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);
        createNotificationChannel();

        tvTitle = findViewById(R.id.etTitle);
        tvStartDate = findViewById(R.id.etStartDate);
        tvEndDate = findViewById(R.id.etEndDate);

        tvStartTime = findViewById(R.id.etStartTime);
        tvEndTime = findViewById(R.id.etEndTime);

        btnClose = findViewById(R.id.btnClose);
        btnComplete = findViewById(R.id.btnComplete);

        tvNotification = findViewById(R.id.etNotification);
        tvDescription = findViewById(R.id.etDescription);
        tvLocation = findViewById(R.id.etLocation);

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
        btnClose.setOnClickListener(this);
        btnComplete.setOnClickListener(this);

        // end of OnCreate
    }

    final Calendar endcldr = Calendar.getInstance();
    final Calendar startcldr = Calendar.getInstance();
    int chosenstart_dayOfMonth = 0;
    int chosenstart_monthOfYear = 0;
    int chosenstart_minute = 0;
    int chosenstart_hour = 0;
    int chosenstart_year = 0;
    //default method for onCLick events
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.etStartDate:
                int day = startcldr.get(Calendar.DAY_OF_MONTH);
                int month = startcldr.get(Calendar.MONTH);
                int year = startcldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(EditActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startcldr.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                startcldr.set(Calendar.MONTH, monthOfYear);
                                startcldr.set(Calendar.YEAR, year);

                                tvStartDate.setText(weekDays[startcldr.get(Calendar.DAY_OF_WEEK)] + ", " + monthNames[monthOfYear] + " " + dayOfMonth);

                                // set the stuff to the parse object thats going to be sent back to server. (inject). chosenstarts commnted out for future optional stories
                                chosenstart_dayOfMonth = dayOfMonth;
                                chosenstart_monthOfYear = monthOfYear;
                                chosenstart_year = year;

                            }
                        }, year, month, day);
                datepicker.show();
                break;

            case R.id.etEndDate:
                day = endcldr.get(Calendar.DAY_OF_MONTH);
                month = endcldr.get(Calendar.MONTH);
                year = endcldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(EditActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                endcldr.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                endcldr.set(Calendar.MONTH, monthOfYear);
                                endcldr.set(Calendar.YEAR, year);

                                tvEndDate.setText(weekDays[endcldr.get(Calendar.DAY_OF_WEEK)] + ", " + monthNames[monthOfYear] + " " + dayOfMonth);

                                // save the chosen enddates to be sent to parse backend here
                                // chosenend_dayOfMonth = dayOfMonth; -> do the same for monthOfYear and year. make sure to initialize those variables globally above
                            }
                        }, year, month, day);
                datepicker.show();
                break;

            case R.id.etStartTime:
                // should i create different cldr instances for each widget? 4 cldrs or 2 cldrs, 1 for time, 1 for date?
                int hour = startcldr.get(Calendar.HOUR_OF_DAY);
                int minute = startcldr.get(Calendar.MINUTE);

                timepicker = new TimePickerDialog(EditActivity.this, R.style.DialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int chosenhour, int chosenminute) {
//                            cldr.set(hour, minute);
                            // TODO: How do we format the time to be sent to the parse backend?
                            tvStartTime.setText(CustomTimeParser(chosenhour, chosenminute));
                            chosenstart_minute = chosenminute;
                            chosenstart_hour = chosenhour;

                            startcldr.set(Calendar.HOUR_OF_DAY, chosenhour);
                            startcldr.set(Calendar.MINUTE, chosenminute);

                        }
                    }, hour, minute, false);

                timepicker.show();
                break;

            case R.id.etEndTime:
                // can we remember the time we selected when its clicked again? It resets the time to the current time
                hour = endcldr.get(Calendar.HOUR_OF_DAY);
                minute = endcldr.get(Calendar.MINUTE);

                timepicker = new TimePickerDialog(EditActivity.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int chosenhour, int chosenminute) {

                                // TODO: How do we format the time to be sent to the parse backend?
                                tvEndTime.setText(CustomTimeParser(chosenhour, chosenminute));

                                endcldr.set(Calendar.HOUR_OF_DAY, chosenhour);
                                endcldr.set(Calendar.MINUTE, chosenminute);

                            }
                        }, hour, minute, false);

                timepicker.show();
                break;

            // TODO: Add cases for the {x} in upper left, and the {Done} in the upper right. Done saves Event object and sends ObjectID to Parse backend. x cancels the activity & goes back?
                // TODO: Figure out how to save a Date Format to be sent in the Parse Event

            case R.id.etNotification:

                showNotifDialog();

                break;

            case R.id.btnClose:
                finish();
                break;

            case R.id.btnComplete:
                // use variable notificationtime for custom time method
                if (notificationtime == null) {
                    set_ontime_notification();
                    Toast.makeText(this, "Notification set at MM:DD:YYYY " +
                                    chosenstart_monthOfYear + " " + chosenstart_dayOfMonth + " " + chosenstart_year + '\n' + " at Time HH:MM" + chosenstart_hour + " " + chosenstart_minute,
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    set_custom_notification(notificationtime);
                    Toast.makeText(this, "Custom notification set at MM:DD:YYYY " +
                                    chosenstart_monthOfYear + " " + chosenstart_dayOfMonth + " " + chosenstart_year + '\n' + " at Time HH:MM" + chosenstart_hour + " " + chosenstart_minute
                            ,Toast.LENGTH_SHORT).show();

                }

                // TODO: End Activity and go to dailyview to show added event. also send all relevant data back to parse backend

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


    Integer notificationtime;
    @Override
    public void onFinishEditDialog(String inputText) {
        // TODO: Later use to setup notifications on the phone (note-to-self look at old chrome tabs)
        tvNotification.setText(inputText + " minutes before");
        notificationtime = Integer.parseInt(inputText);
    }



    public void notification_intent_databuilder(Intent intent) {
        intent.putExtra("Title", tvTitle.getText().toString());
        intent.putExtra("Start Time", tvStartTime.getText().toString());
        intent.putExtra("End Time", tvEndTime.getText().toString());
        intent.putExtra("Location", tvLocation.getText().toString());
        intent.putExtra("Description", tvDescription.getText().toString());
    }

    // a method to help us create a notification channel, the id comes from the ReminderBroadcast.java file. this methos is called in the OnCreate method
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "EventReminderChannel";
            String description = "Channel for Event Reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyevent", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    // sets an alarm/notification at the time of the event
    public void set_ontime_notification() {
        Calendar ontimecal = Calendar.getInstance();
        set_calendar(ontimecal, chosenstart_year, chosenstart_monthOfYear, chosenstart_dayOfMonth, chosenstart_hour, chosenstart_minute, 00 );
        // order is; year, month, day, hour, minute, second
        long eventTime = ontimecal.getTimeInMillis();

//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intentalarm1 = new Intent(this, ReminderBroadcast.class);
        notification_intent_databuilder(intentalarm1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(EditActivity.this, 0, intentalarm1, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

//        final long timeid = System.currentTimeMillis();

        alarmManager.set(AlarmManager.RTC_WAKEUP, eventTime, pendingIntent);
    }

    // intents with unique identifies needed so there are different broadcast IDs or else they would be replaced
    public void set_custom_notification(int mins) {
        Calendar customtimecal = Calendar.getInstance();
        set_calendar(customtimecal, chosenstart_year, chosenstart_monthOfYear, chosenstart_dayOfMonth, chosenstart_hour, chosenstart_minute,00 );
        long eventTime = customtimecal.getTimeInMillis();

        long oneMinute = (long) ((AlarmManager.INTERVAL_FIFTEEN_MINUTES)/15);
        Toast.makeText(this, "testing out divisions: " + oneMinute, Toast.LENGTH_SHORT).show();
        long reminderTime = eventTime - (mins*oneMinute);

        Intent intentalarm2 = new Intent(this, ReminderBroadcast.class);
        notification_intent_databuilder(intentalarm2);
        final int unqid = (int) System.currentTimeMillis();
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(EditActivity.this, 0, intentalarm2, 0);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(EditActivity.this, unqid, intentalarm2, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent2);
        alarmManager.set(AlarmManager.RTC_WAKEUP, eventTime, pendingIntent3);
    }

    public void set_calendar(Calendar cal, int chosenstart_year, int startmonth, int startdayOfMonth, int startchosenhour, int startchosenminute, int startchosensecond) {
        // order is; year, month, day, hour, minute, second
        cal.set(Calendar.YEAR, chosenstart_year);
        cal.set(Calendar.MONTH, startmonth);
        cal.set(Calendar.DAY_OF_MONTH, startdayOfMonth);
        cal.set(Calendar.HOUR_OF_DAY, startchosenhour);
        cal.set(Calendar.MINUTE, startchosenminute);
        cal.set(Calendar.SECOND, startchosensecond);
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
