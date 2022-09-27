package com.lnct.ac.in.idealab.models;

public class EventModel {

    String image_uri;
    String title, date, desc;
    boolean past_event;

    public EventModel(String image_uri, String title, String date, String desc, boolean past_event) {
        this.image_uri = image_uri;
        this.title = title;
        this.date = date;
        this.desc = desc;
        this.past_event = past_event;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isPast_event() {
        return past_event;
    }
}
