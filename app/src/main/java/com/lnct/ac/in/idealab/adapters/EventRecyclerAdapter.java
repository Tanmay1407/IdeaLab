package com.lnct.ac.in.idealab.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.activity.FullScreenEvent;
import com.lnct.ac.in.idealab.models.EventModel;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context c;
    ArrayList<EventModel> event_list;

    public EventRecyclerAdapter(Context c) {
        this.c = c;
    }

//        ====recycler layout: R.layout.recycler_event_layout ==================
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_event_layout, parent, false);
//
//        return new ViewHolder(view);
//    }


//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hold, int position) {
//
//        ViewHolder holder = (ViewHolder) hold;
//
//        //TODO set image in image view and uncomment these lines
////        holder.event_date.setText(event_list.get(position).getDate());
////        holder.event_title.setText(event_list.get(position).getTitle());
////        holder.event_desc.setText(event_list.get(position).getDesc());
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_recycler_layout, parent, false);

        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hold, int position) {

        ViewHolder2 holder = (ViewHolder2) hold;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.startActivity(new Intent(c, FullScreenEvent.class));
            }
        });

        //TODO set image in image view and uncomment these lines
//        holder.event_date.setText(event_list.get(position).getDate());
//        holder.event_title.setText(event_list.get(position).getTitle());
//        if(event_list.get(position).isPast_event()) {
//            holder.event_register.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
//        return event_list.size();
        return 5;
    }

//    class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView event_image;
//        TextView event_title, event_date, event_desc;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            event_image = itemView.findViewById(R.id.event_imageholder);
//            event_title = itemView.findViewById(R.id.event_titleholder);
//            event_date = itemView.findViewById(R.id.event_dateholder);
//            event_desc = itemView.findViewById(R.id.event_descholder);
//        }
//    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        ImageView event_image;
        TextView event_title, event_date;
        TextView event_register;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            event_image = itemView.findViewById(R.id.event_image);
            event_title = itemView.findViewById(R.id.event_title);
            event_date = itemView.findViewById(R.id.event_date);
            event_register = itemView.findViewById(R.id.event_register);
        }
    }

}
