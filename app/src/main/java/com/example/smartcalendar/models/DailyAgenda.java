package com.example.smartcalendar.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DailyAgenda {

    ArrayList<Event> eventsList;
    int date;
    String dayOfWeek;

    public DailyAgenda(int date, String dayOfWeek, ArrayList<Event> eventsList) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.eventsList = eventsList;
    }

    public List<Event> getEventsList() {
        return eventsList;
    }

    public int getDate() {
        return date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void add(Event event) {
    }
}