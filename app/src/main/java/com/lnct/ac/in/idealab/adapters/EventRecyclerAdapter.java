package com.lnct.ac.in.idealab.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.Utils;
import com.lnct.ac.in.idealab.activity.FullScreenEvent;
import com.lnct.ac.in.idealab.models.EventModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context c;
    ArrayList<EventModel> event_list;

    public EventRecyclerAdapter(Context c, ArrayList<EventModel> event_list) {
        this.c = c;
        this.event_list = event_list;
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

        if(event_list.size() > 0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c.startActivity(new Intent(c, FullScreenEvent.class));
                }
            });

            //TODO set image in image view and uncomment these lines
            holder.event_date.setText(event_list.get(position).getStart_date());
            holder.event_title.setText(event_list.get(position).getTitle());
            if (event_list.get(position).isPast_event())
                holder.event_register.setVisibility(View.GONE);
            else holder.event_register.setVisibility(View.VISIBLE);
            Glide.with(c)
                    .load(Uri.parse(event_list.get(position).getImage_uri()))
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(holder.event_image);

            holder.mainview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (!event_list.get(hold.getAbsoluteAdapterPosition()).isPast_event()) {
////                        TODO add intent to quiz activity
//                    }
//                    else {
                        File f = Utils.getImageCacheDir(c);

                        File image = new File(f,  File.separator + event_list.get(hold.getAbsoluteAdapterPosition()).getId()+".jpeg");
                        if(!image.exists()) {
                            try {
                                image.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp = ((BitmapDrawable) holder.event_image.getDrawable()).getBitmap();
                            try {
                                FileOutputStream out = new FileOutputStream(image);
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    Log.i("event ids", event_list.get(hold.getAbsoluteAdapterPosition()).getIds().toString());

                        Intent i = new Intent(c, FullScreenEvent.class);
                        i.putExtra("id", event_list.get(hold.getAbsoluteAdapterPosition()).getId());
                        i.putExtra("event_id", event_list.get(hold.getAbsoluteAdapterPosition()).getIds().toString());
                        i.putExtra("title", event_list.get(hold.getAbsoluteAdapterPosition()).getTitle());
                        i.putExtra("desc", event_list.get(hold.getAbsoluteAdapterPosition()).getDesc());
                        i.putExtra("date", "Start Date:" + event_list.get(hold.getAbsoluteAdapterPosition()).getStart_date() + " | End Date:" + event_list.get(hold.getAbsoluteAdapterPosition()).getEnd_date());
                        c.startActivity(i);

//                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(event_list.size() <= 0) {
            return 1;
        }
        return event_list.size();
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

    public void updateView(ArrayList<EventModel> list) {
        event_list.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        ImageView event_image;
        TextView event_title, event_date;
        TextView event_register;
        MaterialCardView mainview;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            event_image = itemView.findViewById(R.id.event_image);
            event_title = itemView.findViewById(R.id.event_title);
            event_date = itemView.findViewById(R.id.event_date);
            event_register = itemView.findViewById(R.id.event_register);
            mainview = itemView.findViewById(R.id.mainview);
        }
    }



}
