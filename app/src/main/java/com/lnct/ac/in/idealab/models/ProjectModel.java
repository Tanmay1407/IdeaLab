package com.lnct.ac.in.idealab.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ProjectModel {

    private  String id;
    private String url;
    private String desc, title, githubLink, liveLink;
    private boolean app;

    public ProjectModel(String id, String url, String desc, String title, String githubLink, String liveLink) {
        this.id = id;
        this.url = url;
        this.desc = desc;
        this.title = title;
        this.githubLink = githubLink;
        this.liveLink = liveLink;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public String getLiveLink() {
        return liveLink;
    }

    public boolean isApp() {
        return app;
    }

    public static ProjectModel objToProjectmodel(JSONObject obj) {

        ProjectModel model = null;

        try {
            String id = (String) obj.get("_id");
            String title = (String) obj.get("title");
            String description = (String) obj.get("description");
            String imgUrl = (String) obj.get("imgUrl");
            String githubLink = (String) obj.get("imgUrl");
            String liveLink = (String) obj.get("imgUrl");

            model = new ProjectModel(id,imgUrl, description, title, githubLink, liveLink);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }

}
