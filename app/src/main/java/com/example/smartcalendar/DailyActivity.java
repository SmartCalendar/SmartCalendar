package com.example.smartcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcalendar.models.DailyAgenda;
import com.example.smartcalendar.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DailyActivity extends AppCompatActivity {

    private FloatingActionButton fab_main, fab_camera, fab_event;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    Boolean isOpen = false;

    TextView tvYear;
    TextView tvMonth;

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

                Toast.makeText(getApplicationContext(), "Take picture", Toast.LENGTH_SHORT).show();

            }
        });

        fab_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Add event", Toast.LENGTH_SHORT).show();

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