package com.lnct.ac.in.idealab.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lnct.ac.in.idealab.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<String> uri_list;

    public HomeGalleryAdapter(Context c, ArrayList<String> list) {
        this.context = c;
        this.uri_list = list;
        Log.i("size of list---------", uri_list.size()+"");
    }

    public HomeGalleryAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_gallery, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hold, int position) {
        ViewHolder holdr = (ViewHolder) hold;

//        TODO to be uncommented
//        URL url = null;
//        Bitmap bmp;
//        try {
//            url = new URL(uri_list.get(position));
//            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//      TODO followed are static codes
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.app_logo)
                .error(R.drawable.app_logo);
        Glide.with(context)
                .load(uri_list.get(hold.getAbsoluteAdapterPosition()))
                .apply(options)
                .into(holdr.image_holder);

    }

    @Override
    public int getItemCount() {
        return uri_list.size();
//        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_holder;
        public ViewHolder(@NonNull View v) {
            super(v);
            image_holder = v.findViewById(R.id.image_holder);
        }
    }

}
