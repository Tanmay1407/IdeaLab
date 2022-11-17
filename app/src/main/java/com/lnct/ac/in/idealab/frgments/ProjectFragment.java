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
import com.lnct.ac.in.idealab.adapters.ProjectFragmentAdapter;
import com.lnct.ac.in.idealab.interfaces.CallBack;
import com.lnct.ac.in.idealab.models.EventModel;
import com.lnct.ac.in.idealab.models.ProjectModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends Fragment {

    RecyclerView project_rv;
    ArrayList<ProjectModel> project_list;
    CustomDialog dialog;
    CardView nonet;
    TextView refresh_btn;
    ProjectFragmentAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectFragment newInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
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
            project_list = new ArrayList<>();
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
        View v = inflater.inflate(R.layout.fragment_project, container, false);

        project_rv = v.findViewById(R.id.project_rv);
        nonet = v.findViewById(R.id.nonet);
        refresh_btn = v.findViewById(R.id.refresh_btn);

        adapter = new ProjectFragmentAdapter(getContext(), project_list);

        project_rv.setAdapter(adapter);
        project_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        dialog = new CustomDialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();

        fetchData();

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

        return v;
    }

    public void fetchData() {
        VolleyRequest req = new VolleyRequest(getContext(), new CallBack() {
            @Override
            public void responseCallback(JSONObject response) {
                try {
                    JSONArray success = (JSONArray) response.get("success");
                    for(int i = 0; i< success.length(); i++) {
                        JSONObject obj = (JSONObject) success.get(i);
                        ProjectModel model = ProjectModel.objToProjectmodel(obj);
                        project_list.add(model);
                    }
                    adapter.updateView(project_list);
                    if(dialog != null && dialog.isShowing()) dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void errorCallback(VolleyError error_message) {
                Log.i("-----error event frag-----", error_message.getMessage());
                if(dialog != null && dialog.isShowing()) dialog.dismiss();
            }

            @Override
            public void responseStatus(NetworkResponse response_code) {
                Log.i("-----response status home frag-----", response_code.statusCode+"");

            }
        });

        req.getRequest(Constants.URL_GET_PROJECTS);
    }
}