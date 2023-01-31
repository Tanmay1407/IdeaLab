package com.lnct.ac.in.idealab.models;

import java.util.ArrayList;

public class QuestionModel {

    private String q;
    private boolean mcq;
    private ArrayList<String> options;

    public QuestionModel(String q, boolean mcq, ArrayList<String> options) {
        this.q = q;
        this.mcq = mcq;
        this.options = options;
    }

    public String getQ() {
        return q;
    }

    public boolean isMcq() {
        return mcq;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
}
