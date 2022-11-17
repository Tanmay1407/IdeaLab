package com.lnct.ac.in.idealab.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lnct.ac.in.idealab.Constants;
import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.Utils;
import com.lnct.ac.in.idealab.auth.LoginActivity;
import com.lnct.ac.in.idealab.models.EventModel;
import com.lnct.ac.in.idealab.quiz.AssesmentDescriptionActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FullScreenEvent extends AppCompatActivity {

    ImageView event_image;
    TextView title, date, desc, btn;
    EventModel event;
    ImageView back;

    boolean registered = false;
    boolean user_available = false;

    String s;
    String title_, desc_, date_, id_;
    JSONArray id_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_event);

        Intent intent = getIntent();
        title_ = intent.getStringExtra("title");
        desc_ = intent.getStringExtra("desc");
        date_ = intent.getStringExtra("date");
        id_ = intent.getStringExtra("id");

        try {
            id_list = new JSONArray(intent.getStringExtra("event_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        event_image = findViewById(R.id.event_imageholder);
        title = findViewById(R.id.event_titleholder);
        date = findViewById(R.id.event_dateholder);
        desc = findViewById(R.id.event_descholder);
        back = findViewById(R.id.backbtn);
        btn = findViewById(R.id.registerbtn);

        if(Utils.isUserPresent(this)) {
            user_available = true;
            String uid = Utils.getPrefs(this).getString("USER_ID", "000");
            for(int i=0; i<id_list.length(); i++) {
                try {
                    if(id_list.get(i).equals(uid)) {
                        btn.setText("Registered");
                        btn.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
                        registered = true;
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        File f = Utils.getImageCacheDir(this);
        File image = new File(f,  File.separator + id_ + ".jpeg");
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


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_available) {
                    if(!registered) {
                        Intent intent1 = new Intent(FullScreenEvent.this, AssesmentDescriptionActivity.class);
                        startActivity(intent1);
                        Toast.makeText(FullScreenEvent.this, "add intent to quiz activity", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(FullScreenEvent.this, "Login before registering", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(FullScreenEvent.this, LoginActivity.class);
                    startActivity(i);
                    finishAffinity();
                    finish();
                }
            }
        });

        title.setText(title_);
        desc.setText(desc_);
        date.setText(date_);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}