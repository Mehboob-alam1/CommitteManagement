package com.mehboob.committemanagement.common.models;

import androidx.annotation.OpenForTesting;

public class Event {

    private String eventId;
    private String eventName;

    private String eventVenue;
    private String eventDesc;
    private String eventDuration;
    private String eventSuperVisor;

    public Event() {
    }

    public Event(String eventId, String eventName, String eventVenue, String eventDesc, String eventDuration, String eventSuperVisor) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.eventDesc = eventDesc;
        this.eventDuration = eventDuration;
        this.eventSuperVisor = eventSuperVisor;
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(String eventDuration) {
        this.eventDuration = eventDuration;
    }

    public String getEventSuperVisor() {
        return eventSuperVisor;
    }

    public void setEventSuperVisor(String eventSuperVisor) {
        this.eventSuperVisor = eventSuperVisor;
    }
}
