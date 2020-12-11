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
        queryEvents();
    }

    private void initializeViews() {
        int temp;
        id = new ArrayList<String>();

        for(int i=0; i<42; i++){
            id.add("tvDay" + String.valueOf(i+1));
        }

        for(int i=0; i<42; i++){
            temp = getResources().getIdentifier(id.get(i), "id", getPackageName());
            textViews[i] = (TextView)findViewById(temp);
        }

        tvMonth = findViewById(R.id.tvMonth);
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