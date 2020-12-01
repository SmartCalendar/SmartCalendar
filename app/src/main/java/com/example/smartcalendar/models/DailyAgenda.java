package com.example.smartcalendar.models;

import java.util.List;

public class DailyAgenda {

    List<Item> itemsList;
    String date;
    String dayOfWeek;

    public DailyAgenda(String date, String dayOfWeek, List<Item> itemsList) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.itemsList = itemsList;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public String getDate() {
        return date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setItemsList(List<Item> items) {
        this.itemsList = items;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}