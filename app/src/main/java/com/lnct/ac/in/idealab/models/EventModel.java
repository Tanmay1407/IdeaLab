package com.lnct.ac.in.idealab.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventModel {

    private String id;
    private String image_uri;
    private String title, start_date, desc, end_date;
    private boolean past_event;
    private JSONArray ids;

    public EventModel(String id, String image_uri, String title, String start_date, String desc, String end_date, boolean past_event, JSONArray ids) {
        this.id = id;
        this.image_uri = image_uri;
        this.title = title;
        this.start_date = start_date;
        this.desc = desc;
        this.end_date = end_date;
        this.past_event = past_event;
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public String getTitle() {
        return title;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isPast_event() {
        return past_event;
    }

    public JSONArray getIds() {
        return ids;
    }

    public static EventModel objToEventModel(JSONObject obj) throws JSONException {


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String cur = dtf.format(now);
//        System.out.println(dtf.format(now));

        EventModel model = new EventModel(obj.getString("_id"), obj.getString("imgUrl"), obj.getString("title"), obj.getString("startDate").substring(0, 10), obj.getString("description"), obj.getString("endDate").substring(0, 10) ,cur.compareTo(obj.getString("startDate").substring(0, 10))<0?false:true, obj.getJSONArray("studentEnroll"));
//        EventModel model = new EventModel("https://www.adobe.com/express/create/media_104b5f1f25bd4d236d5cefc971e8192c7fe6f9318.jpeg?width=400&format=jpeg&optimize=medium", obj.getString("title"), obj.getString("startDate").substring(0, 10), obj.getString("description"), cur.compareTo(obj.getString("startDate").substring(0, 10))<0?false:true, obj.getJSONArray("studentEnroll"));
        return model;


    }
}
