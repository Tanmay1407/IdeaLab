package com.lnct.ac.in.idealab.frgments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.adapters.HomeGalleryAdapter;
import com.lnct.ac.in.idealab.adapters.HomeUpcomingEventAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    VideoView video_view;
    View view;
    RecyclerView gallery_view, event_view;
    TextView pos_tv, pos_tv_gallery;

    HomeUpcomingEventAdapter event_adapter;
    HomeGalleryAdapter gallery_adapter;
    LinearLayoutManager event_manager, gallery_manager;

    SnapHelper snap_helper, snap_helper2;
    ArrayList<String> uri_list;
    int cur_pos_event, cur_pos_gallery, next_pos_event;

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
        video_view.start();
//        scroll_recycler_gallery();
        scroll_recycler_event();
        event_view.smoothScrollToPosition(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        cur_pos_event = -1;

        pos_tv = view.findViewById(R.id.pos_tv);
        pos_tv_gallery = view.findViewById(R.id.pos_tv_gallery);

        video_view = view.findViewById(R.id.video_view);
        video_view.setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE);
        video_view.setVideoPath("android.resource://" + getActivity().getPackageName() + "/"
                + R.raw.videonewwhite);
        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        snap_helper = new PagerSnapHelper();
        snap_helper2 = new PagerSnapHelper();

        event_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        gallery_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

//        TODO add url array list to adapter's constructor
        gallery_adapter = new HomeGalleryAdapter();
        gallery_view = view.findViewById(R.id.gallery_recycler);
        gallery_view.setNestedScrollingEnabled(true);
        gallery_view.setLayoutManager(gallery_manager);
//        gallery_view.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        gallery_view.setAdapter(gallery_adapter);


        event_adapter = new HomeUpcomingEventAdapter();
        event_view = view.findViewById(R.id.upcoming_events_view);
        event_view.setLayoutManager(event_manager);
        event_view.setAdapter(event_adapter);

//        event_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                cur_pos_event = event_manager.findLastCompletelyVisibleItemPosition();
//            }
//        });

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

//        gallery_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                cur_pos_highlight = gallery_manager.findLastVisibleItemPosition();
//            }
//        });

        snap_helper.attachToRecyclerView(gallery_view);
        snap_helper2.attachToRecyclerView(event_view);

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

    private void scroll_recycler_event() {
        new Thread(new Runnable() {
            @Override
            public void run() {

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

}