package com.lnct.ac.in.idealab.quiz;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lnct.ac.in.idealab.R;

public class RightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        MediaPlayer audio = MediaPlayer.create(this, R.raw.right);
        audio.start();
        thread.start();
    }
}

