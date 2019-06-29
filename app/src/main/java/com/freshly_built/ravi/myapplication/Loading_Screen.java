package com.freshly_built.ravi.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Loading_Screen extends AppCompatActivity

{   private static int set_time_out=2000;
//attaching loading screen activity to main activity
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading__screen);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent homeIntent=new Intent(Loading_Screen.this,MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },set_time_out);
        ImageView logo=(ImageView)findViewById(R.id.imageView);
        AnimationDrawable fb_logo= (AnimationDrawable)logo.getDrawable();
        fb_logo.start();
    }
}
