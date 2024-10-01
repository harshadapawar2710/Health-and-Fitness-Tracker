package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    ImageView ivLogo;
    TextView tvtitle,tvSubtitle;
    Animation fadeinanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivLogo=findViewById(R.id.IvMainLogo);
        tvtitle=findViewById(R.id.tvMainTitle);
        tvSubtitle=findViewById(R.id.tvMainTitle);

        fadeinanim = AnimationUtils.loadAnimation(this,R.anim.fadein);

        ivLogo.setAnimation(fadeinanim);
        tvtitle.setAnimation(fadeinanim);
        tvSubtitle.setAnimation(fadeinanim);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);

            }
        },3000);


    }
}