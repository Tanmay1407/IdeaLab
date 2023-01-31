package com.lnct.ac.in.idealab.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lnct.ac.in.idealab.R;

import java.util.ArrayList;

public class ScrollRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Drawable> list;
    Context c;

    public ScrollRecyclerAdapter(ArrayList<Drawable> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scroll_recycler_layout, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hold, int position) {
        Holder holder = (Holder) hold;
        holder.im.setImageDrawable(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView im;

        public Holder(@NonNull View itemView) {
            super(itemView);
            im = itemView.findViewById(R.id.image_holder);
        }
    }

}
