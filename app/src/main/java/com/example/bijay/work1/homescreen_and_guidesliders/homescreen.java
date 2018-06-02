package com.example.bijay.work1.homescreen_and_guidesliders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bijay.work1.MainActivity;
import com.example.bijay.work1.R;


public class homescreen extends AppCompatActivity {

    private ImageView mlogo;
    private TextView mtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        mlogo=findViewById(R.id.logo);
        mtitle=findViewById(R.id.titlee);
        //mtitle.startAnimation((Animation)AnimationUtils.loadAnimation(this,R.anim.translate));
        //Animation myanim= AnimationUtils.loadAnimation(this,R.anim.mytransition);
       // mlogo.startAnimation(myanim);
        //.mtitle.startAnimation(myanim);
        final Intent intent=new Intent(this,MainActivity.class);
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();

                }
            }
        };

        timer.start();



    }
}
