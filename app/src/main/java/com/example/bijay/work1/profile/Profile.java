package com.example.bijay.work1.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bijay.work1.R;
import com.example.bijay.work1.firedatabase.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {


    TextView medname,medemail,medphone,medaddress;
    ImageButton meditbtn;
    ImageView mproimage;
    LinearLayout meditpro;


    FirebaseAuth mAuth;
    DatabaseReference Ueser;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        meditbtn=findViewById(R.id.editbtn);
        meditpro=findViewById(R.id.editpro);
        mproimage=findViewById(R.id.proimage);
        medname=findViewById(R.id.edname);
        medemail=findViewById(R.id.edemail);
        medphone=findViewById(R.id.edphone);
        medaddress=findViewById(R.id.edaddress);


        Ueser= FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/User");
        mAuth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("Photos");

        setpro();

        meditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editintent=new Intent(Profile.this,editmode_layout.class);
                startActivity(editintent);

            }
        });

    }
    public void setpro(){


        Ueser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot es : dataSnapshot.getChildren()){
                    Model model1=new Model();
                    model1.setAddress(es.getValue(Model.class).getAddress());
                    model1.setEmail(es.getValue(Model.class).getEmail());
                    model1.setMobile(es.getValue(Model.class).getMobile());
                    model1.setName(es.getValue(Model.class).getName());
                    model1.setMimageurl(es.getValue(Model.class).getMimageurl());



                    medemail.setText(new StringBuilder("Email : ").append(model1.getEmail()));
                    medname.setText(new StringBuilder("Name: ").append(model1.getName()));
                    medphone.setText(new StringBuilder("Mobile Number : ").append(model1.getMobile()));
                    medaddress.setText(new StringBuilder("Current Address : ").append(model1.getAddress()));
                    //medphone.setText(model1.getMobile());
                    //medaddress.setText(model1.getAddress());
                    //medemail.setText(model1.getEmail());
                    //medname.setText(model1.getName());

                    Picasso.with(Profile.this )
                            .load(model1.getMimageurl())
                            .fit()
                            .centerCrop()
                            .into(mproimage);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
