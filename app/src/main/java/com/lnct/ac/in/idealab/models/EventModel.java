package com.lnct.ac.in.idealab.models;

public class EventModel {

    String image_uri;
    String title, date, desc;

    public EventModel(String image_uri, String title, String date, String desc) {
        this.image_uri = image_uri;
        this.title = title;
        this.date = date;
        this.desc = desc;
    }
}
