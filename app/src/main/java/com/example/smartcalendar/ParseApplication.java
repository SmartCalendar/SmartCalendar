package com.example.smartcalendar;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        // ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("8XAZWDrRfeQrOLJj7CNYli0L7sJresnUHeVegGu3")
                .clientKey("88mksybeflgBsMjgSrUK7xDp7FSucqKqPTdk0VH8")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
