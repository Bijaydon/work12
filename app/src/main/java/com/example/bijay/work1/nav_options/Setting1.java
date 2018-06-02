package com.example.bijay.work1.nav_options;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bijay.work1.MainActivity;
import com.example.bijay.work1.R;
import com.example.bijay.work1.firedatabase.Model;
import com.example.bijay.work1.profile.Profile;
import com.example.bijay.work1.homepage.homepage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Setting1 extends AppCompatActivity {

    FirebaseAuth mAuth;
    DrawerLayout mdrawer;
    ActionBarDrawerToggle mtoggle;
    NavigationView mnavbar;
    ImageView mpro;
    TextView musername,musermob;
    DatabaseReference Ueser;
    StorageReference storageReference;
    LinearLayout mprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);
        mdrawer=findViewById(R.id.drawer);
        mtoggle=new ActionBarDrawerToggle(this,mdrawer,R.string.open,R.string.close);
        mdrawer.addDrawerListener(mtoggle);
        mnavbar=findViewById(R.id.navbar);
        View headerlayout=mnavbar.getHeaderView(0);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(mnavbar);


        Ueser= FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/User");
        mAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("Photos");



        mpro=(ImageView)headerlayout.findViewById(R.id.pro);
        mprofile=(LinearLayout)headerlayout.findViewById(R.id.profile);
        musername=(TextView)headerlayout.findViewById(R.id.username);
        musermob=(TextView)headerlayout.findViewById(R.id.usermob);



        setinform();
        mprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent profile=new Intent(Setting1.this,Profile.class);
                startActivity(profile);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem){
        //Fragment myFragment=null;
        //Class fragmentClass = null;
        switch (menuItem.getItemId()){
            case R.id.home:

                //fragmentClass=Home.class;


                Intent b =new Intent(Setting1.this,homepage.class);
                startActivity(b);
                break;
            case R.id.event:
                //fragmentClass=Events.class;
                mdrawer.closeDrawers();
                // menuItem.setChecked(true);


                Intent eventintent =new Intent(Setting1.this,Events1.class);
                startActivity(eventintent);
                break;

            case R.id.settings:
                //fragmentClass=Setting.class;
                mdrawer.closeDrawers();
                //menuItem.setChecked(true);
                //getSupportActionBar().setTitle(menuItem.getTitle());

                Intent settingintent =new Intent(Setting1.this,Setting1.class);
                startActivity(settingintent);

                break;
            case R.id.logout:
                //fragmentClass=Logout.class;
                logout();
                break;

            case R.id.about:
                //fragmentClass=About.class;
                mdrawer.closeDrawers();
                //menuItem.setChecked(true);
                // getSupportActionBar().setTitle(menuItem.getTitle());

                Intent aboutintent =new Intent(Setting1.this,About1.class);
                startActivity(aboutintent);

                break;


            default:
                //menuItem.setChecked(true);
                //getSupportActionBar().setTitle(menuItem.getTitle());
                //fragmentClass=Home.class;

                Intent d =new Intent(Setting1.this,homepage.class);
                startActivity(d);


        }
       /* try {

            myFragment=(Fragment)fragmentClass.newInstance();

        }
        catch (Exception e){
            e.printStackTrace();

        }

         FragmentManager fragmentManager= getSupportFragmentManager();
         fragmentManager.beginTransaction().replace(R.id.frame, myFragment).commit();
         menuItem.setChecked(true);
         setTitle(menuItem.getTitle());
         mdrawer.closeDrawers();*/


    }

    private void setupDrawerContent(NavigationView navigationView){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                selectItemDrawer(item);
                return true;

            }
        });

    }

    public void setinform(){


        Ueser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot es : dataSnapshot.getChildren()){
                    Model model1=new Model();
                    model1.setMobile(es.getValue(Model.class).getMobile());
                    model1.setName(es.getValue(Model.class).getName());
                    model1.setMimageurl(es.getValue(Model.class).getMimageurl());



                    musermob.setText(model1.getMobile());
                    musername.setText(model1.getName());

                    Picasso.with(Setting1.this )
                            .load(model1.getMimageurl())
                            .fit()
                            .centerCrop()
                            .into(mpro);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void logout() {

        mAuth.signOut();
        Intent backintent=new Intent(Setting1.this,MainActivity.class);
        startActivity(backintent);
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent bak =new Intent(Setting1.this,homepage.class);
        startActivity(bak);
    }


}


