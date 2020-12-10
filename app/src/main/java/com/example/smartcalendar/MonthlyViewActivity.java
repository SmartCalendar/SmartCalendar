package com.example.smartcalendar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonthlyViewActivity  extends AppCompatActivity {

    private FloatingActionButton fab_main, fab_camera, fab_event;
    private Animation fab_open, fab_close, fab_clock, fab_anti_clock;
    Boolean isOpen = false;
    public static final String TAG = "DetailActivity";

    TextView tvDay01, tvDay02, tvDay03, tvDay04, tvDay05, tvDay06, tvDay07,
            tvDay08, tvDay09, tvDay10, tvDay11, tvDay12, tvDay13, tvDay14,
            tvDay15, tvDay16, tvDay17, tvDay18, tvDay19, tvDay20, tvDay21,
            tvDay22, tvDay23, tvDay24, tvDay25, tvDay26, tvDay27, tvDay28,
            tvDay29, tvDay30, tvDay31, tvDay32, tvDay33, tvDay34, tvDay35,
            tvDay36, tvDay37, tvDay38, tvDay39, tvDay40, tvDay41, tvDay42;

//    ArrayList<TextView> tvDayList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);

        // Set on click listener for each day on the calendar
//        tvDayList.add(tvDay01); tvDayList.add(tvDay02); tvDayList.add(tvDay03); tvDayList.add(tvDay04); tvDayList.add(tvDay05); tvDayList.add(tvDay06); tvDayList.add(tvDay07);
//        tvDayList.add(tvDay08); tvDayList.add(tvDay09); tvDayList.add(tvDay10); tvDayList.add(tvDay11); tvDayList.add(tvDay12); tvDayList.add(tvDay13); tvDayList.add(tvDay14);
//        tvDayList.add(tvDay15); tvDayList.add(tvDay16); tvDayList.add(tvDay17); tvDayList.add(tvDay18); tvDayList.add(tvDay19); tvDayList.add(tvDay20); tvDayList.add(tvDay21);
//        tvDayList.add(tvDay22); tvDayList.add(tvDay23); tvDayList.add(tvDay24); tvDayList.add(tvDay25); tvDayList.add(tvDay26); tvDayList.add(tvDay27); tvDayList.add(tvDay28);
//        tvDayList.add(tvDay29); tvDayList.add(tvDay30); tvDayList.add(tvDay31); tvDayList.add(tvDay32); tvDayList.add(tvDay33); tvDayList.add(tvDay34); tvDayList.add(tvDay35);
//        tvDayList.add(tvDay36); tvDayList.add(tvDay37); tvDayList.add(tvDay38); tvDayList.add(tvDay39); tvDayList.add(tvDay40); tvDayList.add(tvDay41); tvDayList.add(tvDay42);

        tvDay01 = findViewById(R.id.tvDay01); tvDay02 = findViewById(R.id.tvDay02); tvDay03 = findViewById(R.id.tvDay03); tvDay04 = findViewById(R.id.tvDay04); tvDay05 = findViewById(R.id.tvDay05);
        tvDay06 = findViewById(R.id.tvDay06); tvDay07 = findViewById(R.id.tvDay07); tvDay08 = findViewById(R.id.tvDay08); tvDay09 = findViewById(R.id.tvDay09); tvDay10 = findViewById(R.id.tvDay10);
        tvDay11 = findViewById(R.id.tvDay11); tvDay12 = findViewById(R.id.tvDay12); tvDay13 = findViewById(R.id.tvDay13); tvDay14 = findViewById(R.id.tvDay14); tvDay15 = findViewById(R.id.tvDay15);
        tvDay16 = findViewById(R.id.tvDay16); tvDay17 = findViewById(R.id.tvDay17); tvDay18 = findViewById(R.id.tvDay18); tvDay19 = findViewById(R.id.tvDay19); tvDay20 = findViewById(R.id.tvDay20);
        tvDay21 = findViewById(R.id.tvDay21); tvDay22 = findViewById(R.id.tvDay22); tvDay23 = findViewById(R.id.tvDay23); tvDay24 = findViewById(R.id.tvDay24); tvDay25 = findViewById(R.id.tvDay25);
        tvDay26 = findViewById(R.id.tvDay26); tvDay27 = findViewById(R.id.tvDay27); tvDay28 = findViewById(R.id.tvDay28); tvDay29 = findViewById(R.id.tvDay29); tvDay30 = findViewById(R.id.tvDay30);
        tvDay31 = findViewById(R.id.tvDay31); tvDay32 = findViewById(R.id.tvDay32); tvDay33 = findViewById(R.id.tvDay33); tvDay34 = findViewById(R.id.tvDay34); tvDay35 = findViewById(R.id.tvDay35);
        tvDay36 = findViewById(R.id.tvDay36); tvDay37 = findViewById(R.id.tvDay37); tvDay38 = findViewById(R.id.tvDay38); tvDay39 = findViewById(R.id.tvDay39); tvDay40 = findViewById(R.id.tvDay40);
        tvDay41 = findViewById(R.id.tvDay41); tvDay42 = findViewById(R.id.tvDay42);

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
                                    == PackageManager.PERMISSION_DENIED
                    ) {
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

    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Tap", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), DailyViewActivity.class);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    // https://developer.android.com/training/system-ui/immersive -  reference
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}