package com.lnct.ac.in.idealab;

import com.lnct.ac.in.idealab.models.EventModel;

import java.util.ArrayList;

public class Constants {
    public static String idaelab_mail = "idealab@lnct.ac.in";
    public static String playstore_link = "";
    public static String BASE_URL = "https://web-production-eda9.up.railway.app/api/";
    public static String URL_SEND_OTP = BASE_URL+"auth/sendOtp";
    public static String URL_EVENTS = BASE_URL + "event/getAll";
    public static String URL_CREATE_USER = BASE_URL+"users/create";
    public static String URL_GET_PROJECTS = BASE_URL+"project/getAll";

    public static ArrayList<EventModel> event_list;
}
