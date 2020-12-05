package com.example.smartcalendar;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;

import java.util.Calendar;

import okhttp3.Headers;

public class EditActivity extends AppCompatActivity {
    {
        /*final String GOOGLE_CALENDAR_URL = "https://www.googleapis.com/calendar/v3/calendars/calendarId/events/";
        final String TAG = "EditActivity";// Initialize Calendar service with valid OAuth credentials
        Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credentials)
                .setApplicationName("applicationName").build();

// Quick-add an event
        String eventText = "Appointment at Somewhere on June 3rd 10am-10:25am";
        Event createdEvent =
                service.events().quickAdd('primary').setText(eventText).execute();

        System.out.println(createdEvent.getId());*/
        /*AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("calendarId", );
        params.put("eventId", "1");
        client.get(GOOGLE_CALENDAR_URL, params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, String response) {
                        // called when response HTTP status is "200 OK"
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }
                }
        );

    }*/

}