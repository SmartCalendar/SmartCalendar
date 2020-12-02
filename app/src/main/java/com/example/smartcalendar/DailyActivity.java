package com.example.smartcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.smartcalendar.models.DailyAgenda;
import com.example.smartcalendar.models.Item;

import java.util.ArrayList;
import java.util.List;

public class DailyActivity extends AppCompatActivity {

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