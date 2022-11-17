package com.lnct.ac.in.idealab.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.models.EventModel;
import com.lnct.ac.in.idealab.models.ProjectModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ProjectFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ProjectModel> list;
    Context c;

    public ProjectFragmentAdapter(Context c, ArrayList<ProjectModel> list) {
        this.list = list;
        this.c = c;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_recycler, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hold, int position) {
        ViewHolder holder = (ViewHolder) hold;

//        TODO uncomment these lines
        holder.title_text.setText(list.get(position).getTitle());
        holder.desc_text.setText(list.get(position).getDesc());
        if(list.get(position).getLiveLink().trim().length() != 0) {
            holder.link_view.setVisibility(View.VISIBLE);
            holder.link_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(list.get(position).getLiveLink().trim()));
                    c.startActivity(i);
                }
            });
        }
        if(list.get(position).getGithubLink().trim().length() != 0) {
            holder.link_code.setVisibility(View.VISIBLE);
            holder.link_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(list.get(position).getLiveLink().trim()));
                    c.startActivity(i);
                }
            });
        }

        try {
            Bitmap bmp = BitmapFactory.decodeStream(new URL(list.get(position).getUrl()).openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateView(ArrayList<ProjectModel> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView project_image;
        TextView desc_text, link_view, link_code, title_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            project_image = itemView.findViewById(R.id.project_image);
            desc_text = itemView.findViewById(R.id.desc_text);
            link_code = itemView.findViewById(R.id.view_code);
            link_view = itemView.findViewById(R.id.view_site);
            title_text = itemView.findViewById(R.id.title_text);
        }
    }
}
