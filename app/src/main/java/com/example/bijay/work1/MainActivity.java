package com.example.bijay.work1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import android.widget.RelativeLayout;

import com.example.bijay.work1.homepage.homepage;
import com.example.bijay.work1.homescreen_and_guidesliders.guide;
import com.example.bijay.work1.signup.Signup_with_email;
import com.example.bijay.work1.signup.signup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText mphonefield,mpassfield,memailaddress;
    Button mbtnsignup,mlearn,mloginbtn;
    FirebaseAuth mAuth;
    RelativeLayout mainLayout;
    DatabaseReference Ueser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=1 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        //getWindow().setBackgroundDrawableResource(R.drawable.background);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        //auto permissions check while installation of application

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


















        mbtnsignup=findViewById(R.id.btnsignup);
        mlearn=findViewById(R.id.learn);

        //mphonefield=findViewById(R.id.phonefield);
        memailaddress=findViewById(R.id.emailaddress);
        mpassfield=findViewById(R.id.passfield);
        mloginbtn=findViewById(R.id.loginbtn);

        mainLayout = findViewById(R.id.mainLayout);
        mAuth=FirebaseAuth.getInstance();


        Ueser = FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/User");

        //chexk if intially signed in or not

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()!= null) {
                    Intent startIntent = new Intent(MainActivity.this, homepage.class);
                    startActivity(startIntent);
                    finish();
                }
                else {

                    // FirebaseAuth.getInstance().getCurrentUser().delete();

                }
            }
        };



        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent startIntent = new Intent(MainActivity.this, homepage.class);
            startActivity(startIntent);
            finish();
        } else {
            // No user is signed in
          }*/

          mloginbtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  loginuser();

              }
          });







        mbtnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent(MainActivity.this,Signup_with_email.class);
               startActivity(i);
            }
        });

        mlearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j=new Intent(MainActivity.this,guide.class);
                startActivity(j);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //Permission call

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }





    public void loginuser() {


        //final String phone=PhoneNumberUtils.formatNumber(mphonefield.getText().toString());
        //final String phone = mphonefield.getText().toString();
        final String email=memailaddress.getText().toString().trim();
        final String password = mpassfield.getText().toString();

        /*if (phone.isEmpty()) {

            mphonefield.setError("Mobile No is required");
            mphonefield.requestFocus();
            return;
        }*/


        if (email.isEmpty()) {

            memailaddress.setError("Email is Required");
            memailaddress.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            memailaddress.setError("Please enter Valid email");
            memailaddress.requestFocus();
            return;

        }


        if (password.isEmpty()) {

            mpassfield.setError("Password is Required");
            mpassfield.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mpassfield.setError("At least 6 characters");
            mpassfield.requestFocus();
            return;

        }


        /*PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                MainActivity.this,
                mcallbacks

        );


        mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };*/


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful()){


                    Intent intent=new Intent(MainActivity.this,homepage.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(intent);
                    finish();

                }else {

                    Snackbar.make(mainLayout, "You are not registered", Snackbar.LENGTH_SHORT)
                            .show();

                    //Toast.makeText(MainActivity.this,"You are not registered",Toast.LENGTH_SHORT).show();
                    // Toast.makeText(signin1.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    /*public void login(){

        final String phone=mphonefield.getText().toString();
        final String password=mpassfield.getText().toString();

        if (phone.isEmpty()) {

            mphonefield.setError("Mobile No is required");
            mphonefield.requestFocus();
            return;
        }

        if (password.isEmpty()) {

            mpassfield.setError("Password is Required");
            mpassfield.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mpassfield.setError("At least 6 characters");
            mpassfield.requestFocus();
            return;

        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                MainActivity.this,
                mcallbacks

        );




        mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };

    }*/



   /*private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            FirebaseUser user = task.getResult().getUser();
                            Intent m=new Intent(MainActivity.this,homepage.class);
                            startActivity(m);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }*/

   @Override
    public void onBackPressed() {

       moveTaskToBack(true);
    }


}
