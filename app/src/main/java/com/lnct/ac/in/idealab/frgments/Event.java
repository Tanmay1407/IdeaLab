package com.lnct.ac.in.idealab.frgments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.lnct.ac.in.idealab.Constants;
import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.Utils;
import com.lnct.ac.in.idealab.VolleyRequest;
import com.lnct.ac.in.idealab.adapters.EventRecyclerAdapter;
import com.lnct.ac.in.idealab.interfaces.CallBack;
import com.lnct.ac.in.idealab.models.EventModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Event#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Event extends Fragment {

    RecyclerView event_rv, past_event_rv;
    CustomDialog dialog;
    CardView nonet;
    TextView refresh_btn;

    ArrayList<EventModel> past_ev_list;
    ArrayList<EventModel> upcoming_ev_list;

    EventRecyclerAdapter adapter, past_adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Event() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Event newInstance(String param1, String param2) {
        Event fragment = new Event();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Utils.isNetworkAvailable(getContext())) {

            nonet.setVisibility(View.GONE);
        }
        else {
            if(dialog != null && dialog.isShowing()) dialog.dismiss();
            nonet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        upcoming_ev_list = new ArrayList<>();
        past_ev_list = new ArrayList<>();


        nonet = view.findViewById(R.id.nonet);
        refresh_btn = view.findViewById(R.id.refresh_btn);

        adapter = new EventRecyclerAdapter(getContext(), new ArrayList<>());
        past_adapter = new EventRecyclerAdapter(getContext(), new ArrayList<>());

        past_event_rv = view.findViewById(R.id.past_event_view);
        past_event_rv.setAdapter(past_adapter);
        past_event_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        event_rv = view.findViewById(R.id.event_view);
        event_rv.setAdapter(adapter);
        event_rv.setLayoutManager(new LinearLayoutManager(getActivity().getParent(), LinearLayoutManager.VERTICAL, false));
//        mSnapHelper.attachToRecyclerView(event_rv);

//        fetchAndLoadEvents();
        loadStaticEvents();

        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        dialog = new CustomDialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.create();
//        dialog.show();


        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    fragmentManager.beginTransaction().detach(currentFragment).commitNow();
                    fragmentManager.beginTransaction().attach(currentFragment).commitNow();
                } else {
                    fragmentManager.beginTransaction().detach(currentFragment).attach(currentFragment).commit();
                }
            }
        });

        return view;
    }

    private void loadStaticEvents() {

        upcoming_ev_list.add(new EventModel("001", "https://instagram.fdel36-1.fna.fbcdn.net/v/t51.2885-15/323819003_553057826731316_4065689184604012230_n.webp?stp=dst-jpg_e35_p480x480&_nc_ht=instagram.fdel36-1.fna.fbcdn.net&_nc_cat=108&_nc_ohc=qYb_7XnbZaAAX_wB6h0&edm=ACWDqb8BAAAA&ccb=7-5&ig_cache_key=MzAwODA5MTIzMTg3MDg2MTcyNw%3D%3D.2-ccb7-5&oh=00_AfA7tUyMpG1mqRRb4u8ztjXqzdGHhs4gLYX0lJqMNOwxIQ&oe=63DF53FB&_nc_sid=1527a3", "IDEA Lab internship", "14-01-2023", "Internship oppurtnity at IDEA Lab LNCT, with stipend of 5000rs.", "----------", false, new JSONArray()));
        past_ev_list.add(new EventModel("002", "https://instagram.fdel36-1.fna.fbcdn.net/v/t51.2885-15/311099146_415210620767406_8204783205705142198_n.webp?stp=dst-jpg_e35&_nc_ht=instagram.fdel36-1.fna.fbcdn.net&_nc_cat=106&_nc_ohc=5ogUEGeUofsAX9Wt7Op&tn=IuyQcV3PeJe-YRuJ&edm=ACWDqb8BAAAA&ccb=7-5&ig_cache_key=Mjk0Njc5NDMxMjY5MjA0ODQ0NA%3D%3D.2-ccb7-5&oh=00_AfA9ZHxNHxMIQfKzlrJTR5HDq1XgQXn1IfGWQPT_sXog7Q&oe=63DFB944&_nc_sid=1527a3", "AppZest", "01-11-2022", "Android application development contest for college students", "04-11-2022", true, new JSONArray()));
        past_ev_list.add(new EventModel("003", "https://instagram.fdel36-1.fna.fbcdn.net/v/t51.2885-15/299032428_748356943160321_7119871411348497656_n.webp?stp=dst-jpg_e35&_nc_ht=instagram.fdel36-1.fna.fbcdn.net&_nc_cat=105&_nc_ohc=TkS_oYgY3SMAX9Lelgx&edm=ABmJApABAAAA&ccb=7-5&ig_cache_key=MjkwMzMxNzQzODQyNzEwMTM1Ng%3D%3D.2-ccb7-5&oh=00_AfCaJ21HbnXXflQUam7JuECADl7ulp_-qxvoHgROzAWB4g&oe=63DF98A7&_nc_sid=6136e7", "3D 101", "22-08-2022", "3D modelling workshop", "27-08-2022", true, new JSONArray()));

        adapter.updateView(upcoming_ev_list);
        past_adapter.updateView(past_ev_list);

    }

    private void fetchAndLoadEvents() {
        VolleyRequest request = new VolleyRequest(getContext(), new CallBack() {
            @Override
            public void responseCallback(JSONObject response) {
                Log.i("event_response____", response.toString());
                try {
                    ArrayList<EventModel> tmp_list = new ArrayList<>();
                    JSONArray sucess = response.getJSONArray("success");
                    for(int i=0; i<sucess.length(); i++) {
                        EventModel model = EventModel.objToEventModel(sucess.getJSONObject(i));
                        tmp_list.add(model);
                        if(model.isPast_event()) {
                            past_ev_list.add(model);
                        }
                        else {
                            upcoming_ev_list.add(model);
                        }
                    }
                    Constants.event_list = new ArrayList<>();
                    Constants.event_list.addAll(tmp_list);
                    tmp_list = null;
//                    Log.i("length_arraylistt", upcoming_event_list.size()+"");
                    if(dialog != null && dialog.isShowing()) dialog.dismiss();
                    adapter.updateView(upcoming_ev_list);
                    past_adapter.updateView(past_ev_list);
//                    event_adapter = new HomeUpcomingEventAdapter(upcoming_event_list, getContext());
//                    event_view.setAdapter(event_adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void errorCallback(VolleyError error_message) {
                Log.i("-----error home frag-----", error_message.getMessage());
                if(dialog != null && dialog.isShowing()) dialog.dismiss();
            }

            @Override
            public void responseStatus(NetworkResponse response_code) {
                Log.i("-----response status home frag-----", response_code.statusCode+"");
            }
        });

        request.getRequest(Constants.URL_EVENTS);

    }

}