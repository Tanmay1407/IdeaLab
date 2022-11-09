package com.lnct.ac.in.idealab.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.models.EventModel;

import java.util.ArrayList;

public class HomeUpcomingEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<EventModel> event_list;
    private Context c;

    public HomeUpcomingEventAdapter() {

    }

    public HomeUpcomingEventAdapter(ArrayList<EventModel> event_list, Context c) {
        this.event_list = event_list;
        this.c = c;
    }

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

        if(event_list.size() > 0) {
            holder.event_title.setText(event_list.get(position).getTitle());
            holder.event_date.setText((CharSequence) event_list.get(position).getStart_date());
            Glide.with(c)
                    .load(Uri.parse(event_list.get(position).getImage_uri()))
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(holder.event_image);
        }
    }

    @Override
    public int getItemCount() {
        if(event_list.size() > 0)
            return event_list.size();
        return 1;
    }

    public void updateView(ArrayList<EventModel> list) {
        event_list.addAll(list);
        notifyDataSetChanged();
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
