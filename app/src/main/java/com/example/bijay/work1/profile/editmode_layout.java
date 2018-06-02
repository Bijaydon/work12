package com.example.bijay.work1.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

public class editmode_layout extends AppCompatActivity {


    MaterialEditText mname,mphone,maddress,memail;
    ImageView mproimagee;
    Button mbtnupdate;
    String userid,phonenumber,passcode,imageurl;

    FirebaseAuth mAuth;
    DatabaseReference Ueser;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmode_layout);

        //to hide keypad initially
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mname=findViewById(R.id.name);
        memail=findViewById(R.id.email);
        //mphone=findViewById(R.id.phone);
        maddress=findViewById(R.id.address);
        mproimagee=findViewById(R.id.propicture);
        mbtnupdate=findViewById(R.id.btnupdate);

        //Database (firebase)

        Ueser= FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/User");
        mAuth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("Photos");


        setpro();

        mproimagee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepic();
            }
        });

        mbtnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address=maddress.getText().toString().trim();
                String name=mname.getText().toString().trim();
                String email=memail.getText().toString().trim();

                if (name.isEmpty()) {

                    mname.setError("Name is Required");
                    mname.requestFocus();
                    return;
                }


                if (email.isEmpty()) {

                    memail.setError("Email is Required");
                    memail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    memail.setError("Please enter Valid email");
                    memail.requestFocus();
                    return;

                }

                if (address.isEmpty()) {
                    maddress.setError("Address is Required");
                    maddress.requestFocus();
                    return;
                }

                update(userid,name,email,address,passcode,phonenumber,imageurl);


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
                    model1.setId(es.getValue(Model.class).getId());
                    model1.setPassword(es.getValue(Model.class).getPassword());
                    userid = model1.getId();
                    phonenumber=model1.getMobile();
                    imageurl=model1.getMimageurl();
                    passcode=model1.getPassword();





                    //mphone.setText(model1.getMobile());
                    maddress.setText(model1.getAddress());
                    memail.setText(model1.getEmail());
                    mname.setText(model1.getName());

                    Picasso.with(editmode_layout.this )
                            .load(model1.getMimageurl())
                            .fit()
                            .centerCrop()
                            .into(mproimagee);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void changepic(){








    }

    private boolean update( String userid, String name,String email,String address,String passcode,String phonenumber,String imageurl ){

        Ueser= FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/User").child(userid);

        Model model2=new Model();
        model2.setId(userid);
        model2.setName(name);
        model2.setEmail(email);
        model2.setAddress(address);

        //due to mismatch in password and imageurl

        model2.setPassword(passcode);
        model2.setMobile(phonenumber);

        model2.setMimageurl(imageurl);



        //model2.setMimageurl(image);


        Ueser.setValue(model2);

        Toast.makeText(editmode_layout.this, "Update Successful", Toast.LENGTH_SHORT).show();
        Intent intentf=new Intent(editmode_layout.this,Profile.class);
        startActivity(intentf);
        finish();

     return true;
    }
}
