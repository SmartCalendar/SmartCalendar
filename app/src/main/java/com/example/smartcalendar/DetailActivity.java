package com.example.smartcalendar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvDate;
    TextView tvTime;
    TextView tvLocation;
    TextView tvNotification;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvLocation = findViewById(R.id.tvLocation);
        tvNotification = findViewById(R.id.tvNotification);
        tvDescription = findViewById(R.id.tvDescription);

    }
}