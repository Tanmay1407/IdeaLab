package com.lnct.ac.in.idealab.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.models.EventModel;

import java.util.ArrayList;

public class HomeUpcomingEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<EventModel> event_list;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_events_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hold, int position) {
        ViewHolder holder = (ViewHolder) hold;
//        TODO add items to recycler view
    }

    @Override
    public int getItemCount() {
//        event_list.size();
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView event_image;
        TextView event_title, event_date;

        public ViewHolder(@NonNull View v) {
            super(v);

            event_date = v.findViewById(R.id.event_date);
            event_title = v.findViewById(R.id.event_title);
            event_image = v.findViewById(R.id.event_image);

        }
    }

}
