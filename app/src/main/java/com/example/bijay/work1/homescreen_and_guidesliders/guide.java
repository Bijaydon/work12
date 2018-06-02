package com.example.bijay.work1.homescreen_and_guidesliders;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bijay.work1.MainActivity;
import com.example.bijay.work1.R;

public class guide extends AppCompatActivity {
    ViewPager mpageview;
    LinearLayout mdotsview;

    private TextView[] mdots;

    SliderAdapter sliderAdapter;

    Button mnxtbtn,mbackbtn,mfinishbtn;
    private int mcurrentpage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);



        mpageview=findViewById(R.id.pageview);
        mdotsview=findViewById(R.id.dotsview);

        mnxtbtn=findViewById(R.id.nxtbtn);
        mbackbtn=findViewById(R.id.backbtn);
        mfinishbtn=findViewById(R.id.finishbtn);

        sliderAdapter=new SliderAdapter(this);
        mpageview.setAdapter(sliderAdapter);

        mDotsIndicator(0);

        mpageview.addOnPageChangeListener(viewListener);

        //clicklisteners

        mnxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpageview.setCurrentItem(mcurrentpage+1);

            }
        });

        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpageview.setCurrentItem(mcurrentpage-1);
            }
        });

        mfinishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(guide.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void mDotsIndicator(int i ){
        mdots=new TextView[3];
        mdotsview.removeAllViews();
        for( int position=0;position<mdots.length;position++){
            mdots[position]=new TextView(this);
            mdots[position].setText(Html.fromHtml("&#8226;"));
            mdots[position].setTextSize(35);
            mdots[position].setTextColor(getResources().getColor(R.color.transparentwhite));

            mdotsview.addView(mdots[position]);
        }

        if (mdots.length>0){

            mdots[i].setTextColor(getResources().getColor(R.color.accent));
        }
    }


    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

         mDotsIndicator(position);

         mcurrentpage=position;

         if(position == 0){

             mnxtbtn.setEnabled(true);
             mbackbtn.setEnabled(false);
             mfinishbtn.setEnabled(false);
             mfinishbtn.setVisibility(View.INVISIBLE);
             mbackbtn.setVisibility(View.INVISIBLE);

             mnxtbtn.setText("Next");
             mbackbtn.setText("");
         }else if (position == mdots.length-1){

             mfinishbtn.setEnabled(true);
             mbackbtn.setEnabled(true);
             mnxtbtn.setEnabled(false);
             mbackbtn.setVisibility(View.VISIBLE);
             mfinishbtn.setVisibility(View.VISIBLE);
             mnxtbtn.setVisibility(View.INVISIBLE);
             mfinishbtn.setText("Finish");
             mbackbtn.setText("Back");

         }else {

             mnxtbtn.setEnabled(true);
             mbackbtn.setEnabled(true);
             mfinishbtn.setEnabled(false);
             mbackbtn.setVisibility(View.VISIBLE);
             mfinishbtn.setVisibility(View.INVISIBLE);
             mnxtbtn.setVisibility(View.VISIBLE);
             mnxtbtn.setText("Next");
             mbackbtn.setText("Back");
         }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
