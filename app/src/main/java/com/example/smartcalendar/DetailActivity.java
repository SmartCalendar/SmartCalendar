package com.example.smartcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcalendar.models.Event;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    String [] weekDays = {" ", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sep", "Oct", "Nov", "Dec"};

    String objectId;
    TextView tvTitle;
    TextView tvDate;
    TextView tvLocation;
    TextView tvNotification;
    TextView tvDescription;
    Date date;
    int notification;


    // TODO: Re-use a lot of the code from EditActivity for displaying the details in certain formats (Date, Time, Notification?)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvLocation = findViewById(R.id.tvLocation);
        tvNotification = findViewById(R.id.tvNotification);
        tvDescription = findViewById(R.id.tvDescription);

        queryEvent();
    }

    private void queryEvent() {
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.whereEqualTo("objectId", objectId);
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> events, ParseException e) {
                if (e != null) {
                    return;
                }
                for (Event event: events) {
                    Log.i("DetailActivity",  "Event: " + event.getTitle());
                    date = event.getDate();
                    notification = event.getNotification();
                    fillTitle(event.getTitle());
                    fillDate(event.getDate());
                    fillLocation(event.getLocation());
                    fillNotification(event.getNotification());
                    fillDescription(event.getDescription());
                }
            }

            private void fillTitle(String title) {
                tvTitle.setText(title);
            }

            private void fillDate(Date date) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                String dayOfWeek = weekDays[cal.get(Calendar.DAY_OF_WEEK)];
                String month = monthNames[cal.get(Calendar.MONTH)];
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR);

                String minute;
                if (cal.get(Calendar.MINUTE) != 0) {
                    minute = String.valueOf(cal.get(Calendar.MINUTE));
                } else {
                    minute = "00";
                }

                String AM_PM;
                if (cal.get(Calendar.AM_PM) == 1) {
                    AM_PM = "PM";
                } else {
                    AM_PM = "AM";
                }

                tvDate.setText(dayOfWeek + ", " + month + " " + dayOfMonth + "  " + hour + ":" + minute + " " + AM_PM ); }

            private void fillLocation(String location) {
                if (location != null) {
                    tvLocation.setText(location);
                }
            }

            private void fillNotification(int notification) {
                tvNotification.setText(notification + " minutes before");
            }

            private void fillDescription(String description) {
                if (description != null) {
                    tvDescription.setText(description);
                }
            }
        });
    }

    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(), EditActivity.class);
        i.putExtra("sender", "DetailActivity");
        i.putExtra("objectId", objectId);
        i.putExtra("title", tvTitle.getText());
        i.putExtra("date", date);
        i.putExtra("location", tvLocation.getText());
        i.putExtra("notification", notification);
        i.putExtra("description", tvDescription.getText());
    }
}