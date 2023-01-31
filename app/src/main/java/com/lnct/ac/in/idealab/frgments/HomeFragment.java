package com.lnct.ac.in.idealab.frgments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.lnct.ac.in.idealab.Constants;
import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.Utils;
import com.lnct.ac.in.idealab.VolleyRequest;
import com.lnct.ac.in.idealab.adapters.HomeGalleryAdapter;
import com.lnct.ac.in.idealab.adapters.HomeUpcomingEventAdapter;
import com.lnct.ac.in.idealab.adapters.ScrollRecyclerAdapter;
import com.lnct.ac.in.idealab.interfaces.CallBack;
import com.lnct.ac.in.idealab.models.EventModel;
import com.mindinventory.AutoScrollCircularPagerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    VideoView video_view;
    View view;
    RecyclerView gallery_view, event_view, scroll_recycler_view;
    TextView pos_tv, pos_tv_gallery, refresh_btn;
    CardView nonet;
    NestedScrollView mainview;

    HomeUpcomingEventAdapter event_adapter;
    HomeGalleryAdapter gallery_adapter;
    LinearLayoutManager event_manager, gallery_manager, view_manager;

    SnapHelper snap_helper, snap_helper2, snap_helper3;
    ArrayList<String> uri_list;
    ArrayList<Drawable> list;
    ArrayList<EventModel> upcoming_event_list;
    int cur_pos_event, cur_pos_gallery, next_pos_event;
    AutoScrollCircularPagerView autoScrollContainer;
    ArrayList<Integer> image_list;

    CustomDialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            upcoming_event_list = new ArrayList<>();
            image_list =  new ArrayList<>();
            image_list.add(R.drawable.slide_a);
            image_list.add(R.drawable.slide_b);
            image_list.add(R.drawable.slide_e);
            image_list.add(R.drawable.slide_d);
            image_list.add(R.drawable.slide_c);

//            autoScrollContainer.setItems(image_list, true);

//            dialog = new androidx.appcompat.app.AlertDialog.Builder(getContext())
//                    .setTitle("Please Wait")
//                    .setCancelable(false)
//                    .setMessage("Loading data").create();
//
//            dialog.show();
//            mainview.setVisibility(View.VISIBLE);
        }
        else {
//            if(dialog != null && dialog.isShowing()) dialog.dismiss();
            nonet.setVisibility(View.VISIBLE);
//            mainview.setVisibility(View.GONE);
        }

//        video_view.start();
//        scroll_recycler_gallery();
        scroll_recycler_event();
        scroll_recycler_view();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        cur_pos_event = -1;

        pos_tv = view.findViewById(R.id.pos_tv);
        pos_tv_gallery = view.findViewById(R.id.pos_tv_gallery);
        nonet = view.findViewById(R.id.nonet);
        mainview = view.findViewById(R.id.mainview);
        refresh_btn = view.findViewById(R.id.refresh_btn);

//        video_view = view.findViewById(R.id.video_view);
//        video_view.setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE);
//        video_view.setVideoPath("android.resource://" + getActivity().getPackageName() + "/"
//                + R.raw.videonewwhite);
//        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//            }
//        });

        snap_helper = new PagerSnapHelper();
        snap_helper2 = new PagerSnapHelper();
        snap_helper3 = new PagerSnapHelper();

        view_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        event_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        gallery_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

//        autoScrollContainer = view.findViewById(R.id.autoScrollContainer);
//        autoScrollContainer.setItems(image_list, true);

//        TODO add url array list to adapter's constructor
        gallery_adapter = new HomeGalleryAdapter();
        gallery_view = view.findViewById(R.id.gallery_recycler);
        gallery_view.setNestedScrollingEnabled(true);
        gallery_view.setLayoutManager(gallery_manager);
//        gallery_view.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        gallery_view.setAdapter(gallery_adapter);


        event_adapter = new HomeUpcomingEventAdapter(new ArrayList<>(), getContext());
        event_view = view.findViewById(R.id.upcoming_events_view);
        event_view.setLayoutManager(event_manager);
        event_view.setAdapter(event_adapter);

        list = new ArrayList<Drawable>();
        list.add(getResources().getDrawable(R.drawable.slide_a));
        list.add(getResources().getDrawable(R.drawable.slide_b));
        list.add(getResources().getDrawable(R.drawable.slide_c));
        list.add(getResources().getDrawable(R.drawable.slide_d));
        list.add(getResources().getDrawable(R.drawable.slide_e));

        scroll_recycler_view = view.findViewById(R.id.scroll_recycler_view);
        ScrollRecyclerAdapter adap = new ScrollRecyclerAdapter(list, getContext());
        scroll_recycler_view.setLayoutManager(view_manager);
        scroll_recycler_view.setAdapter(adap);

//        event_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                cur_pos_event = event_manager.findLastCompletelyVisibleItemPosition();
//            }
//        });

//        ----------------------dialog show----------------------------------
//        LayoutInflater inflater1 = getActivity().getLayoutInflater();
//        dialog = new CustomDialog(getActivity());
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        dialog.create();
//        dialog.show();

        gallery_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StringBuilder sb = new StringBuilder();
                cur_pos_gallery = gallery_manager.findLastCompletelyVisibleItemPosition();
                for(int i=0; i<gallery_manager.getItemCount(); i++) {
                    if(i != cur_pos_gallery) sb.append("• ");
                    else sb.append("| ");
                }
                pos_tv_gallery.setText(sb.toString().trim());
            }
        });

        event_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StringBuilder sb = new StringBuilder();
                cur_pos_event = event_manager.findLastCompletelyVisibleItemPosition();
                for(int i=0; i<event_manager.getItemCount(); i++) {
                    if(i != cur_pos_event) sb.append("• ");
                    else sb.append("| ");
                }
                pos_tv.setText(sb.toString().trim());
            }
        });

//        fetchAndLoadEvents();
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

//                Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
//
//                if (currentFragment instanceof HomeFragment) {
//                    FragmentTransaction fragTransaction =   (getActivity()).getSupportFragmentManager().beginTransaction();
//                    fragTransaction.detach(currentFragment);
//                    fragTransaction.attach(currentFragment);
//                    fragTransaction.commit();
//                }
            }
        });

//        gallery_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                cur_pos_highlight = gallery_manager.findLastVisibleItemPosition();
//            }
//        });

        snap_helper.attachToRecyclerView(gallery_view);
        snap_helper2.attachToRecyclerView(event_view);
        snap_helper3.attachToRecyclerView(scroll_recycler_view);

        return view;
    }

//    private void scroll_recycler_gallery() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
////                TODO change to i<uri_list.size()
//                for(; true; ) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            gallery_view.scrollToPosition(finalI);
//
////                            TODO change to size of url list
//                            if(cur_pos_highlight == 7) cur_pos_highlight = -1;
//                            gallery_view.smoothScrollToPosition(cur_pos_highlight+1);
//                        }
//                    });
//                    try {
//                        Thread.sleep(2500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }).start();
//    }

//    private void scroll_to_next() {
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2500);
//                    int i = cur_pos_event;
//                    if(i == 5) i = -1;
//                    event_view.smoothScrollToPosition(i + 1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    private void scroll_recycler_view() {
        new Thread(new Runnable() {
            @Override
            synchronized public void run() {
                for(int i=0; i<=list.size(); i++)  {
                    if(i == list.size()) i = 0;
                    scroll_recycler_view.smoothScrollToPosition(i);
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    private void scroll_recycler_event() {
        new Thread(new Runnable() {
            @Override
            synchronized public void run() {

                for(; true; ) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
                            next_pos_event = event_manager.findLastCompletelyVisibleItemPosition() + 1;
//                            StringBuilder sb = new StringBuilder();
                            if(next_pos_event == event_manager.getItemCount()) next_pos_event = 0;
                            event_view.smoothScrollToPosition(next_pos_event);
//                            for(int i=0; i<event_manager.getItemCount(); i++) {
//                                if(i == next_pos_event) sb.append("| ");
//                                else sb.append("• ");
//                            }
//                            pos_tv.setText(sb.toString().trim());

//                        }
//                    });
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void fetchAndLoadEvents() {
        VolleyRequest request = new VolleyRequest(getContext(), new CallBack() {
            @Override
            public void responseCallback(JSONObject response) {
                Log.i("-----on res home frag-----", response.toString());
                try {
                    JSONArray sucess = response.getJSONArray("success");
                    ArrayList<EventModel> tmp_list = new ArrayList<>();
                    for(int i=0; i<sucess.length(); i++) {
                        EventModel model = EventModel.objToEventModel(sucess.getJSONObject(i));
                        tmp_list.add(model);
                        if(!model.isPast_event()) {
                            upcoming_event_list.add(model);
                        }
                    }
                    Log.i("length_arraylistt", upcoming_event_list.size()+"");
                    if(dialog != null && dialog.isShowing()) dialog.dismiss();
                    event_adapter.updateView(upcoming_event_list);
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

class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
    }
}