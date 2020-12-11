package com.example.smartcalendar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
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
import com.example.smartcalendar.models.DailyAgendaAdapter;
import com.example.smartcalendar.models.Event;
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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static java.util.regex.Pattern.CASE_INSENSITIVE;


public class DailyViewActivity extends AppCompatActivity {

    Parser parser = new Parser();
    String [] weekDays = {" ", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] monthNames = {"Jan", "Feb", "March", "April", "May", "June", "July","Aug", "Sep", "Oct", "Nov", "Dec"};

    private FloatingActionButton fab_main, fab_camera, fab_event;
    private Animation fab_open, fab_close, fab_clock, fab_anti_clock;
    Boolean isOpen = false;
    public static final String TAG = "DailyViewActivity";
    String currentPhotoPath;

    DailyAgendaAdapter dailyAgendaAdapter;
    List<DailyAgenda> agendaList;
    RecyclerView rvAgenda;

    int month;
    int date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        rvAgenda = findViewById(R.id.rvDailyView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        dailyAgendaAdapter = new DailyAgendaAdapter(getAgendaList());

        rvAgenda.setLayoutManager(layoutManager);

        rvAgenda.setAdapter(dailyAgendaAdapter);

        rvAgenda.smoothScrollToPosition(date);

        createFloatingActionButton();

//        queryEvents();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<DailyAgenda> getAgendaList() {

        Intent intent = getIntent();
        month = intent.getIntExtra("month", 12);
        date = intent.getIntExtra("date", 12);

        agendaList = new ArrayList<>();

        int year = 2020;

        for (int i = 1; i < LocalDate.of(year, month, date).lengthOfMonth()+1; i++) {
            LocalDate day = LocalDate.of(year, month, i);
            String dayOfWeek = day.getDayOfWeek().toString().substring(0, 1) + day.getDayOfWeek().toString().substring(1, 3).toLowerCase();
            agendaList.add(new DailyAgenda(i, dayOfWeek, new ArrayList<Event>()));
        }

        return agendaList;
    }

//    private void queryEvents() {
//        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
//        query.include(Event.KEY_USER);
//        query.findInBackground(new FindCallback<Event>() {
//            @Override
//            public void done(List<Event> events, ParseException e) {
//                if (e != null) {
//                    return;
//                }
//                for (Event event: events) {
//                    Log.i("DailyViewActivity", "Event: " + event.getTitle() + ", date: " + event.getDate().getDate());
//                    // TODO: populate events
//                }
//            }
//        });
//    }

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

    // launch edit activity after processing the extracted text
    public void launchEditActivity(String title, Date rawdate) {
        Intent autofill = new Intent(DailyViewActivity.this, EditActivity.class);

        autofill.putExtra("Complete Title", title);
        autofill.putExtra("Complete Date Object", rawdate);
        autofill.putExtra("Sender is DailyView", "True");

        startActivity(autofill);

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
                                Log.e(TAG, "Text Recognizer: "+ resultText);

                                // Attempt to proxy font size with box frame heights
                                ArrayList<String> frametexts = new ArrayList<String>();
                                ArrayList<Float> frameheights = new ArrayList<Float>();
                                StringBuilder nattytext = new StringBuilder();

                                List<Text.TextBlock> blocks = visionText.getTextBlocks();

                                for (Text.TextBlock block : visionText.getTextBlocks()) {
                                    for (Text.Line line : block.getLines()) {
                                        Rect frame = line.getBoundingBox();
                                        float fontsize = frame.bottom - frame.top;
                                        String sampletext = line.getText();
//                                        int fontsize = frame.height();
                                        frameheights.add(fontsize);
                                        frametexts.add(sampletext);

                                        // natty parser gets messed up with unrelated text. this will search for lines of text with numbers, then only call parser on those lines
                                        Pattern pattern = Pattern.compile("[0-9]");
                                        Matcher matcher = pattern.matcher(sampletext);
                                        boolean matchFound = matcher.find();
                                        if(matchFound) {
                                            List <DateGroup> groups2 = parser.parse(sampletext);
                                            nattytext.append(sampletext);
                                            nattytext.append(" ");
                                        }
                                    }
                                }

                                // {Weekday}, {MMM} {DD} | {XX:XX} PM
                                // only use parser on lines where elements match regex pattern of numbers? numbers mean dates and or times
                                String finalnattytext = nattytext.toString();
                                List <DateGroup> groups2 = parser.parse(finalnattytext);
                                Calendar smartcal2 = Calendar.getInstance();
                                Date rawdateobj = new Date();
                                for(DateGroup group : groups2) {
                                    List<Date> dates = group.getDates();
                                    rawdateobj = dates.get(0);
                                    Log.e(TAG, "Date: " + dates.get(0));
                                    smartcal2.setTime(dates.get(0));

                                    Log.e(TAG, "Weekday: " + weekDays[smartcal2.get(Calendar.DAY_OF_WEEK)]);
                                    Log.e(TAG, "Month: " + monthNames[smartcal2.get(Calendar.MONTH)]);
                                }
                                Log.e(TAG, "The concatenated string with numbers only: " + finalnattytext);


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
                                StringBuilder titletexts = new StringBuilder();
                                titletexts.append(frametexts.get(maxIdx));
                                titletexts.append(" ");

                                ArrayList<Integer> frameheightidxs = new ArrayList<Integer>();
                                for (int i=0; i<frameheights.size(); i++) {
                                    if (frameheights.get(i) >= maxValbottom && !frametexts.get(maxIdx).equals(frametexts.get(i))) {
                                        frameheightidxs.add(i);
                                        Log.e(TAG, "Font height >: "+ maxValbottom + " Spillover text is: " + frametexts.get(i));
                                        titletexts.append(frametexts.get(i));
                                        titletexts.append(" ");
                                    }
                                }

                                String titletex = titletexts.toString();
                                Log.e(TAG, "Biggest font text: " + frametexts.get(maxIdx));
                                Log.e(TAG, "Complete title: " + titletex);

                                launchEditActivity(titletex, rawdateobj);

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
}