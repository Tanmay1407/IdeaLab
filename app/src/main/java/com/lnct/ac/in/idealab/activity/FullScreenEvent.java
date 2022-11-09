package com.lnct.ac.in.idealab.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lnct.ac.in.idealab.Constants;
import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.Utils;
import com.lnct.ac.in.idealab.models.EventModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FullScreenEvent extends AppCompatActivity {

    ImageView event_image;
    TextView title, date, desc;
    EventModel event;
    ImageView back;

    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_event);

        event_image = findViewById(R.id.event_imageholder);
        title = findViewById(R.id.event_titleholder);
        date = findViewById(R.id.event_dateholder);
        desc = findViewById(R.id.event_descholder);
        back = findViewById(R.id.backbtn);

        s = getIntent().getStringExtra("event_id");

        File f = Utils.getImageCacheDir(this);
        File image = new File(f,  File.separator + s +".jpeg");
        FileInputStream in = null;
        try {
             in = new FileInputStream(image);
            Bitmap bmp = BitmapFactory.decodeStream(in);
            event_image.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(EventModel ev: Constants.event_list) {
            if(ev.getId().equals(s)) {
                this.event = ev;
                break;
            }
        }

        title.setText(event.getTitle());
        desc.setText(event.getDesc());
        date.setText("Start Date:" + event.getStart_date() + " | End Date:" + event.getEnd_date());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                finishAffinity();
            }
        });

    }
}