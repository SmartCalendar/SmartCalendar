package com.example.smartcalendar.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.R;

import java.util.List;

public class DailyAgendaAdapter extends RecyclerView.Adapter<DailyAgendaAdapter.ViewHolder>{

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    EventsAdapter eventsAdapter;
    private List<DailyAgenda> agendaList;

    public DailyAgendaAdapter(List<DailyAgenda> agendaList) {
        this.agendaList = agendaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_agenda,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyAgenda dailyAgenda = agendaList.get(position);
        holder.tvDate.setText(String.valueOf(dailyAgenda.getDate()));
        holder.tvDayOfWeek.setText(dailyAgenda.getDayOfWeek());

        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewHolder.rvAgenda.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setInitialPrefetchItemCount(dailyAgenda.getEventsList().size());

        eventsAdapter = new EventsAdapter(dailyAgenda.getEventsList());

        ViewHolder.rvAgenda.setAdapter(eventsAdapter);

        ViewHolder.rvAgenda.setLayoutManager(layoutManager);

        ViewHolder.rvAgenda.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return agendaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate;
        private TextView tvDayOfWeek;
        private static RecyclerView rvAgenda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDayOfWeek = itemView.findViewById(R.id.tvDayOfWeek);
            rvAgenda = itemView.findViewById(R.id.rvAgenda);
        }
    }
}