package com.example.smartcalendar.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DailyAgenda {

    ArrayList<Event> eventsList = new ArrayList<Event>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Event> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(Event event) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends Event> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends Event> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public Event get(int i) {
            return null;
        }

        @Override
        public Event set(int i, Event event) {
            return null;
        }

        @Override
        public void add(int i, Event event) {

        }

        @Override
        public Event remove(int i) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<Event> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Event> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<Event> subList(int i, int i1) {
            return null;
        }
    };
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