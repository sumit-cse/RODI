package com.tnjdevelopers.rodi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Animation a1= AnimationUtils.loadAnimation(this,R.anim.imageani1);
        Animation a2=AnimationUtils.loadAnimation(this,R.anim.textani1);
        Animation a3=AnimationUtils.loadAnimation(this,R.anim.textani2);
        TextView t1=(TextView)findViewById(R.id.textappname);
        TextView t2=(TextView)findViewById(R.id.textdevelopers);
        ImageView im=(ImageView)findViewById(R.id.imageappicon);
        a1.setRepeatCount(Animation.INFINITE);
        a1.setRepeatMode(Animation.REVERSE);
        t1.startAnimation(a2);
        t2.startAnimation(a3);
        im.startAnimation(a1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
            }
        }, 1600);
    }
}
