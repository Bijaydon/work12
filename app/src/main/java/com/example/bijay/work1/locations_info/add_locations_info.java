package com.example.bijay.work1.locations_info;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.bijay.work1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;

public class add_locations_info extends AppCompatActivity {

    MaterialEditText mhos_name,mhos_address,mlat,mlong,mopen,mclose;
    Button btnsave;
    String hosname,hosaddress,hoslat,hoslong,hosopen,hosclose;
    RelativeLayout minfo_layout;

    DatabaseReference Ueser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loacations_info);

        mhos_name=findViewById(R.id.hos_name);
        mhos_address=findViewById(R.id.hos_address);
        mlat=findViewById(R.id.hos_latitude);
        mlong=findViewById(R.id.hos_longitude);
        mopen=findViewById(R.id.hos_open);
        mclose=findViewById(R.id.hos_close);
        btnsave=findViewById(R.id.save_info);
        minfo_layout=findViewById(R.id.info_layout);

        Ueser = FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/places_info");


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveinfo();
            }
        });
    }


    public void saveinfo(){

        hosname=mhos_name.getText().toString().trim();
        hosaddress=mhos_address.getText().toString();
        hoslat=mlat.getText().toString();
        hoslong=mlong.getText().toString();
        hosopen=mopen.getText().toString();
        hosclose=mclose.getText().toString();
        //String method="save";

        if(hosname.isEmpty()){
            mhos_name.setError("Hospital's name required");
            mhos_name.requestFocus();
            return;
        }

        if(hosaddress.isEmpty()){
            mhos_address.setError("Hospital's address required");
            mhos_address.requestFocus();
            return;
        }

        if(hoslat.isEmpty()){
            mlat.setError("Hospital's lattitude required");
            mlat.requestFocus();
            return;
        }

        if(hoslong.isEmpty()){
            mlong.setError("Hospital's opening Time required");
            mlong.requestFocus();
            return;
        }

        if(hosopen.isEmpty()){
            mopen.setError("Hospital's closing Time required");
            mclose.requestFocus();
            return;
        }

        //Sql_Link sql_link=new Sql_Link(this);
        //sql_link.execute(method,hosname,hosaddress,hoslat,hoslong,hosopen,hosclose);

        String key=Ueser.push().getKey();
        Model_places_info model_places_info=new Model_places_info();
        model_places_info.setHospital_name(hosname);
        model_places_info.setHospital_address(hosaddress);
        model_places_info.setHospital_lattitude(hoslat);
        model_places_info.setHospital_longitude(hoslong);
        model_places_info.setHospital_open(hosopen);
        model_places_info.setHospital_close(hosclose);

        Ueser.child(key).setValue(model_places_info);
        Snackbar.make(minfo_layout,"Information Saved Successfully !!",Snackbar.LENGTH_LONG).show();
        finish();



    }

}
