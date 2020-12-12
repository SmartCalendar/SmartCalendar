package com.example.smartcalendar.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.DailyViewActivity;
import com.example.smartcalendar.DetailActivity;
import com.example.smartcalendar.EditActivity;
import com.example.smartcalendar.R;

import java.util.Calendar;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    public List<Event> eventList;
    Context context;

    public EventsAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsViewHolder holder, int position) {
        final Event event = eventList.get(position);
        Calendar cal = Calendar.getInstance();
        cal.setTime(event.getDate());
        int hour = cal.get(Calendar.HOUR);
        String minute;
        if (cal.get(Calendar.MINUTE) != 0) {
            minute = String.valueOf(cal.get(Calendar.MINUTE));
        } else {
            minute = "00";
        }
        String AM_PM;
        if (cal.get(Calendar.AM_PM) == 1) {
            AM_PM = "PM";
        } else {
            AM_PM = "AM";
        }
        holder.tvTitle.setText(event.getTitle());
        holder.tvTime.setText(hour + ":" + minute + " " + AM_PM);
        holder.tvLocation.setText(event.getLocation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("objectId", event.getObjectId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvLocation;

        EventsViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLocation = itemView.findViewById(R.id.tvLocation);
        }
    }
}
