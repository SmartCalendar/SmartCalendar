package com.example.smartcalendar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.BitmapCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcalendar.models.DailyAgenda;
import com.example.smartcalendar.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class DailyActivity extends AppCompatActivity {

    private FloatingActionButton fab_main, fab_camera, fab_event;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    Boolean isOpen = false;
    public static final String TAG = "dailyactivity";
    ImageView imageView;

    TextView tvYear;
    TextView tvMonth;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        RecyclerView rvAgenda = findViewById(R.id.rvDailyView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        DailyAgendaAdapter adapter = new DailyAgendaAdapter(getAgendaList());

        rvAgenda.setLayoutManager(layoutManager);

        rvAgenda.setAdapter(adapter);

        // Floating_action_button views and animations
        // layout_fab_submenu changed from RelativeLayout to Framelayout, couldn't do <include with activity_daily so just manually copied over layout elements
        // TODO: Fix the color themes for the layouts and have it more polished.
        fab_main = findViewById(R.id.fab_main);
        fab_camera = findViewById(R.id.fab_camera);
        fab_event = findViewById(R.id.fab_event);


        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    fab_camera.startAnimation(fab_close);
                    fab_event.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
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
    File photoFile;
    private void openCamera() {

        Intent camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (camintent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
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
    String currentPhotoPath;
    private File createImageFile() throws IOException {
//        String currentPhotoPath;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        InputImage imageProxy2=null;
        Uri takenPhotoUri = Uri.fromFile(new File(currentPhotoPath));
        try {
            imageProxy2 = InputImage.fromFilePath(this, takenPhotoUri);
        } catch (IOException e) {
            e.printStackTrace();
        }


        TextRecognizer recognizer = TextRecognition.getClient();

        Task<Text> result =
                recognizer.process(imageProxy2)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Each {Line} object contains 0 or more {Element} objects which represent words and word-like entities such as dates and numbers
                                // We might be interested in parsing {Element}s. For now we do a triple nested for loop for each {TextBlock}, {Line}, {Element}

                                // Extract text is stored in variable "resultText"
                                String resultText = visionText.getText();
                                Toast.makeText(getApplicationContext(), resultText, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Text Recognizer: "+ resultText);

                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // Toast.makeText(getApplicationContext(), "on failure", Toast.LENGTH_SHORT).show();
                                        Log.i("CameraX", "Text Recognition failed. Error: " + e);
                                    }
                                })
                        // TODO: Make sure this is working!!!!
                        // check for errors in this OnCompleteListener implementation (im scared)
                        .addOnCompleteListener(new OnCompleteListener<Text>() {
                            @Override
                            public void onComplete(@NonNull Task<Text> task) {

                            }
                        });


    }


    private List<DailyAgenda> getAgendaList() {
        List<DailyAgenda> agendaList = new ArrayList<>();

        agendaList.add(new DailyAgenda("11", "Tue", getItemsList()));
        agendaList.add(new DailyAgenda("12", "Wed", getItemsList()));
        agendaList.add(new DailyAgenda("13", "Thu", getItemsList()));

        return agendaList;
    }

    private List<Item> getItemsList() {
        List<Item> itemsList = new ArrayList<>();

        itemsList.add(new Item("Work", "9 AM - 5 PM", "Texas Christian University"));
        itemsList.add(new Item("Lunch with Bryan", "12 - 1 PM", "In-N-Out Burger"));

        return itemsList;
    }

}