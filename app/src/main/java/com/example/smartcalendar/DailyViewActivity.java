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
import android.graphics.Rect;
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
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static android.os.Environment.getExternalStoragePublicDirectory;


public class DailyViewActivity extends AppCompatActivity {

    Parser parser = new Parser();
    String [] weekDays = {" ", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] monthNames = {"Jan", "Feb", "March", "April", "May", "June", "July","Aug", "Sep", "Oct", "Nov", "Dec"};

    private FloatingActionButton fab_main, fab_camera, fab_event;
    private Animation fab_open, fab_close, fab_clock, fab_anti_clock;
    Boolean isOpen = false;
    public static final String TAG = "DailyViewActivity";
    String currentPhotoPath;

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

    File photoFile; // declaring global variable to later get path for file


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

        // retrive Image Uri then turn it into InputImage object for input into Mlkit text recgonizer process
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
                                // We might be interested in parsing {Element}s

                                // Extract text is stored in variable "resultText"
                                String resultText = visionText.getText();

                                // can delete the Toast. Log is just for testing.
                                Toast.makeText(getApplicationContext(), resultText, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Text Recognizer: "+ resultText);

                                // Attempt to proxy font size with box frame heights
                                ArrayList<String> frametexts = new ArrayList<String>();
                                ArrayList<Float> frameheights = new ArrayList<Float>();
                                List<Text.TextBlock> blocks = visionText.getTextBlocks();
                                for (Text.TextBlock block : visionText.getTextBlocks()) {
                                    for (Text.Line line : block.getLines()) {
                                        Rect frame = line.getBoundingBox();
                                        float fontsize = frame.bottom - frame.top;
                                        String sampletext = line.getText();
//                                        int fontsize = frame.height();
                                        frameheights.add(fontsize);
                                        frametexts.add(sampletext);
                                    }
                                }

                                // get frame height for each line. can discard this later, just printing it for testing purposes
                                for (int i=0; i<frameheights.size(); i++) {
                                    Log.e(TAG, "Frame height: " + frameheights.get(i));
                                }
//                                Log.e(TAG, "ArrayList Size: " + frameheights.size());

                                // print the text in the line with max height. maybe print 2nd max if its nearby.(use heuristic here?)
                                // once ArrayList is filled, go back find the max, print that line
                                Float maxVal = Collections.max(frameheights);
                                Integer maxIdx = frameheights.indexOf(maxVal);
                                // 15% difference for the 2nd or 3rd line that title test takes up
                                double bottomdouble = (maxVal-maxVal*.15);
                                float maxValbottom = (float) bottomdouble;

                                // get index of each frameheight > maxValBottom (15% cutoff), print in a tag with the text also
                                // TODO: set log for outlier (font size 800 in food fair & cultural night). How to handle these cases?
                                // a for loop to print the biggest text with frame height(fontsize) > max-max*.15
                                ArrayList<Integer> frameheightidxs = new ArrayList<Integer>();
                                for (int i=0; i<frameheights.size(); i++) {
                                    if (frameheights.get(i) >= maxValbottom && !frametexts.get(maxIdx).equals(frametexts.get(i))) {
                                        frameheightidxs.add(i);
                                        Log.e(TAG, "Font height >: "+ maxValbottom + " Spillover text is: " + frametexts.get(i));
                                    }
                                }

                                Log.e(TAG, "Biggest font text: " + frametexts.get(maxIdx));
                                // TODO: get into Natty. Parse Dates, store values, open up EditActivity, pre-load the values
                                // {Weekday}, {MMM} {DD} | {XX:XX} PM
                                List <DateGroup> groups = parser.parse(resultText);
                                Calendar smartcal = Calendar.getInstance();
                                for(DateGroup group : groups) {
                                    List<Date> dates = group.getDates();
                                    Log.e(TAG, "Date: " + dates.get(0));
                                    smartcal.setTime(dates.get(0));

                                    Log.e(TAG, "Weekday: " + weekDays[smartcal.get(Calendar.DAY_OF_WEEK)]);
                                    Log.e(TAG, "Month: " + monthNames[smartcal.get(Calendar.MONTH)]);

                                }



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
                        // TODO: If using CameraX, make sure to close this. Not sure what to do here, for now I will leave it alone
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