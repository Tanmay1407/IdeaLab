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
            upcoming_ev_list = new ArrayList<>();
            past_ev_list = new ArrayList<>();
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

        fetchAndLoadEvents();

        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        dialog = new CustomDialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();

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