package com.lnct.ac.in.idealab.models;

public class ProjectModel {

    private String url;
    private String desc, title;
    private boolean app;

    public ProjectModel(String url, String desc, String title, boolean app) {
        this.url = url;
        this.desc = desc;
        this.title = title;
        this.app = app;
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

    public boolean isApp() {
        return app;
    }
}
