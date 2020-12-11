package com.example.smartcalendar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.smartcalendar.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MonthlyViewActivity  extends AppCompatActivity {

    public static final String TAG = "MonthlyViewActivity";

    private FloatingActionButton fab_main, fab_camera, fab_event;
    private Animation fab_open, fab_close, fab_clock, fab_anti_clock;
    Boolean isOpen = false;

    private ArrayList<String> id;
    private TextView[] textViews = new TextView[42];

    int month, date;
    TextView tvMonth;

    ArrayList<Event> eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);
        eventsList = new ArrayList<>();

        initializeViews();
        createFloatingActionButton();
        // 0 is January. 11 is December
        initializemonthview(10);
        queryEvents();
    }

    private void initializeViews() {
        int temp;
        id = new ArrayList<String>();

        // id has a ArrayList of strings that are ["tvDay1", "tvDay2"...."tvDay42"]
        for(int i=0; i<42; i++){
            id.add("tvDay" + String.valueOf(i+1));
        }

        // temp retrieves each element("tvDay1") from ArrayList ID (above).
        // textViews is an Array of size 42 containing TextView objects. Initializes all the textviews
        for(int i=0; i<42; i++){
            temp = getResources().getIdentifier(id.get(i), "id", getPackageName());
            textViews[i] = (TextView)findViewById(temp);
        }

        tvMonth = findViewById(R.id.tvMonth);
    }

    // 0 is January, 11 is December. int month input ranges from 0 to 11.
    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July","August", "September", "October", "November", "December"};
    String [] weekDays = {" ", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    // textviews {tvDayOfWeek1}=Sunday, {tvDayOfWeek2}=Monday, {tvDayOfWeek7}=Saturday
    // in the future, clicklistener will recall this initializemonthview function with old input +- 1 depending on the direction. also account for changing the year?
    Calendar monthviewcal = Calendar.getInstance();
    private void initializemonthview(int month) {
        // What day of the week does the 1st of the month input start?
        monthviewcal.set(Calendar.MONTH, month);
        monthviewcal.set(Calendar.DAY_OF_MONTH, 1);// Calendar has month input and 1st day.
        monthviewcal.set(Calendar.YEAR, 2020);
        tvMonth.setText(monthNames[month]);
        int weekdayint = monthviewcal.get(Calendar.DAY_OF_WEEK); // values range from 1=Sunday to 7=Saturday. For December the weekdayint=3, so tvDayOfWeek3 is the start.
        int maxdays = monthviewcal.getActualMaximum(Calendar.DAY_OF_MONTH); // December this value would be 31

        // December it will loop from i=3 to i<34 or i=33. tvDay1 to tvDay33 is filled.
        // textViews index ranges from 0 to 41. size is 42.
        Log.e(TAG, "Week day integer: " + weekdayint);
        Log.e(TAG, "Max days in november " + maxdays);
        Log.e(TAG, "Date " + monthviewcal.getTime());
        for (int i=weekdayint-1, j = 1; i<maxdays+weekdayint-1; i++, j++) {
            textViews[i].setText(String.valueOf(j));
        }

        // forward loop after end of month. start from tvday34 to 42. this will break for leap years.
        for (int i=maxdays+weekdayint-1, j=1; i<=41; i++, j++) {
            textViews[i].setText(String.valueOf(j));
        }

        // backward loop before the month. probably will break when changing years.
        Calendar previousmonth = Calendar.getInstance();
        previousmonth.set(Calendar.MONTH, month-1);
        int prevlastday = previousmonth.getActualMaximum(Calendar.DAY_OF_MONTH); // December example -> It will check to see 30 days in november. iterate from 30 until no room left.
        for (int i=weekdayint-2, j=prevlastday;  i >= 0 ; i--, j--) {
            textViews[i].setText(String.valueOf(j));
        }


    }


    private void createFloatingActionButton() {
        // Floating_action_button views and animations
        // layout_fab_submenu changed from RelativeLayout to FrameLayout, couldn't do <include with activity_daily so just manually copied over layout elements
        // TODO: Fix the color themes for the layouts and have it more polished.
        fab_main = findViewById(R.id.fab_main);
        fab_camera = findViewById(R.id.fab_camera);
        fab_event = findViewById(R.id.fab_event);

        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anti_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    fab_camera.startAnimation(fab_close);
                    fab_event.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anti_clock);
                    fab_camera.setClickable(false);
                    fab_event.setClickable(false);
                    isOpen = false;

                } else {
                    fab_camera.startAnimation(fab_open);
                    fab_event.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab_camera.setClickable(true);
                    fab_event.setClickable(true);
                    isOpen = true;
                }
            }
        });

        // camera button click listener
        // TODO: 1. Setup implicit intent to access camera and take a photograph.
        // TODO:        Startactivity for result? How to navigate from here? Should there be a camera layout screen or jump straight to device camera?
        // TODO: 2. TextRecognition processor on high quality image then -> auto-fill event details with Natty parser and other support libs
        fab_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_DENIED)
                    {
                        // Permission was not granted
                        // Ask for runtime permission
                        String[] permissions = new String[2];
                        permissions[0] = Manifest.permission.CAMERA;
                        permissions[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE;

                        requestPermissions(
                                permissions,
                                101
                        );
                    } else {
                        // Permission already granted
                        openCamera();
                    }
                } else {
                    // System OS < Marshmallow
                    openCamera();
                }
            }
        });
        // TODO: Link this to open up the EditActivity
        fab_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Add event", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void queryEvents() {
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.include(Event.KEY_USER);
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> events, ParseException e) {
                if (e != null) {
                    return;
                }
                for (Event event: events) {
                    Log.i("MonthlyViewActivity", "Adding Event: " + event.getTitle());
                    int date = event.getDate().getDate();
                    textViews[date-1].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5A62AE")));
                    textViews[date-1].setBackgroundResource(R.drawable.background_color_circle_selector);
                    textViews[date-1].setTextColor(Color.parseColor("#FFFFFF"));
                }
                eventsList.addAll(events);
                Log.i("MonthlyViewActivity", "First Event: " + eventsList.get(0).getTitle());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v) {
        TextView t = (TextView) v;

        date = Integer.parseInt(t.getText().toString()) ;
        month = Month.valueOf(tvMonth.getText().toString().toUpperCase()).getValue();
        Intent i = new Intent(getApplicationContext(), DailyViewActivity.class);
        i.putExtra("month", month);
        i.putExtra("date", date);
        i.putParcelableArrayListExtra("eventsList", eventsList);
        Log.i("MonthlyViewActivity", "Date clicked: " + date);
        startActivity(i);
    }

    private void openCamera() {

        Intent camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (camintent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "Error occured creating the file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                camintent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(camintent, 101);
            }
        }
    }

    private File createImageFile() throws IOException {
        String currentPhotoPath;
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}