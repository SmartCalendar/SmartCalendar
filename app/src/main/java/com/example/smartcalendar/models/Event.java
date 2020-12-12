package com.example.smartcalendar.models;

import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Event")
public class Event extends ParseObject {

    public static final String KEY_ID = "objectId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_END_DATE = "end_date";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_NOTIFICATION = "notification_minutes_before";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "user";

    public String getTitle() { return getString(KEY_TITLE); }

    public Date getDate() {
        return getDate(KEY_DATE);
    }

    public Date getEndDate() {
        return getDate(KEY_END_DATE);
    }

    public String getLocation() {
        return getString(KEY_LOCATION);
    }

    public int getNotification() {
        return getInt(KEY_NOTIFICATION);
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
}