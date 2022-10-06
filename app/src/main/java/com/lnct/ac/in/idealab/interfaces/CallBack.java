package com.lnct.ac.in.idealab.interfaces;

public interface CallBack {
    public abstract void responseCallback(String response);
    public abstract void errorCallback(String error_message);
}
