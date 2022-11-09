package com.lnct.ac.in.idealab;

import com.lnct.ac.in.idealab.models.EventModel;

import java.util.ArrayList;

public class Constants {
    public static String idaelab_mail = "idealab@lnct.ac.in";
    public static String playstore_link = "";
    public static String BASE_URL = "https://idealablnct.herokuapp.com/api/";
    public static String URL_SEND_OTP = BASE_URL+"auth/sendOtp";
    public static String URL_EVENTS = BASE_URL + "event/getAll";
    public static String URL_CREATE_USER = BASE_URL+"users/create";

    public static ArrayList<EventModel> event_list;
}
