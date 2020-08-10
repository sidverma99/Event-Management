package com.example.zavaso;

public class Event {
    public String getEventName() {
        return eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public String getDate() {
        return date;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public String eventName,eventLocation,eventVenue,date,eventStartTime,eventEndTime;

    public Event() {
    }

    public Event(String eventName, String eventLocation, String eventVenue, String date, String eventStartTime, String eventEndTime) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventVenue = eventVenue;
        this.date = date;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
    }
}
