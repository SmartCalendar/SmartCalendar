package com.example.smartcalendar.models;

import java.util.ArrayList;
import java.util.List;

public class Item {

    public String title;
    public String date;
    public String month;
    public String dayOfWeek;
    public String time;
    public String endDate;
    public String endTime;
    public String location;
    public String notification;
    public String description;

    public Item(String title, String time, String location) {
        this.title = title;
        this.time = time;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getNotification() {
        return notification;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) { this.title = title; }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}