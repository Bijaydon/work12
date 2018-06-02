package com.example.bijay.work1.signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bijay.work1.R;
import com.example.bijay.work1.firedatabase.Model;
import com.example.bijay.work1.homepage.homepage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.TimeUnit;

public class verification extends AppCompatActivity {
    TextView mt_num,mt_uid,mt_status,merrorfield;
    Button mbtn_send,mbtn_refresh;
    DatabaseReference Ueser;
    String phonenum,id,picurl;
    EditText mcodefield;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private LinearLayout msendlayout,mverifylayout;
    RelativeLayout mdonlayout;
    private ProgressBar msendprogress,mverifyprogress;
    private int btntype=0;

    StorageReference storageref;
    StorageReference desertRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mcodefield=findViewById(R.id.codefield);
        merrorfield=findViewById(R.id.errorfield);
        mt_num=findViewById(R.id.t_num);
        mt_uid=findViewById(R.id.t_uid);
        mt_status=findViewById(R.id.t_status);
       // mbtn_send=findViewById(R.id.btn_send);
        mbtn_refresh=findViewById(R.id.btn_refresh);


        msendlayout=findViewById(R.id.sendlayout);
        mverifylayout=findViewById(R.id.verifylayout);
        mdonlayout=findViewById(R.id.donlayout);
        msendprogress=(ProgressBar)findViewById(R.id.sendprogress);
        mverifyprogress=(ProgressBar)findViewById(R.id.verifyprogress);

        mAuth=FirebaseAuth.getInstance();




        Ueser= FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/User");

        setinfo();


        mbtn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btntype == 0) {

                    //mbtn_send.setEnabled(false);
                    msendprogress.setVisibility(View.VISIBLE);
                    mbtn_refresh.setEnabled(false);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phonenum,
                            60,
                            TimeUnit.SECONDS,
                            verification.this,
                            mcallbacks

                    );
                }else {

                    mbtn_refresh.setEnabled(true);
                    mverifyprogress.setVisibility(View.VISIBLE);

                    String verificationcode=mcodefield.getText().toString();
                    if (verificationcode.isEmpty()) {
                        mcodefield.setError("Enter code first !!");
                        mcodefield.requestFocus();
                        return;
                    }
                    mbtn_refresh.setEnabled(true);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationcode);
                    signInWithPhoneAuthCredential(credential);
                    Snackbar.make(mdonlayout,"Code sent to"+phonenum,Snackbar.LENGTH_SHORT);

                }
            }

        });

        mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                merrorfield.setText("There was some error in Verification !!");
                merrorfield.setVisibility(View.VISIBLE);
                deleteuser();
                Intent nm=new Intent(verification.this,signup.class);
                startActivity(nm);
                finish();


            }


            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {


                // Save verification ID and resending token so we can use them later

                mVerificationId = verificationId;
                mResendToken = token;

                btntype=1;



                    msendprogress.setVisibility(View.INVISIBLE);
                    //mverifyprogress.setVisibility(View.VISIBLE);
                    mverifylayout.setVisibility(View.VISIBLE);

                    mbtn_refresh.setText("PROCEED");
                    mbtn_refresh.setEnabled(true);
                    //if(mcodefield.getText().toString().isEmpty()){


                    // merrorfield.setText("Please Enter Code first !!");
                    //merrorfield.setVisibility(View.VISIBLE);


                    // ...
                }


        };





    }

    /*private void resendVerificationCode(String phonenum,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenum,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mcallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }*/





    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information


                           // mverifyprogress.setVisibility(View.INVISIBLE);
                            Snackbar.make(mdonlayout,"Sign up Successful!!",Snackbar.LENGTH_SHORT);
                            FirebaseUser user = task.getResult().getUser();
                            Snackbar.make(mdonlayout,"Sign up Successful!!",Snackbar.LENGTH_SHORT);
                            Intent l=new Intent(verification.this,homepage.class);
                            startActivity(l);
                            finish();
                            // ...
                        } else {

                            // Sign in failed, display a message and update the UI
                            //merrorfield.setText("There was some error in logging in !!");
                            merrorfield.setText("Invalid Verification Code !!");
                            merrorfield.setVisibility(View.VISIBLE);
                            deleteuser();
                            Intent n=new Intent(verification.this,signup.class);
                            startActivity(n);
                            finish();



                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void deleteuser(){

       StorageReference profilepic= FirebaseStorage.getInstance().getReferenceFromUrl(picurl);
        profilepic.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                //Log.d(TAG, "onSuccess: deleted file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                //Log.d(TAG, "onFailure: did not delete file");
            }
        });


        Ueser.child(id).removeValue();




    }

    /*private void deleteuser() {

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(verification.this,signup.class));
                            finish();
                        }
                    }
                });
    }*/



    private void setinfo() {



            Ueser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Model model=new Model();
                    model.setMobile(ds.getValue(Model.class).getMobile());
                    model.setId(ds.getValue(Model.class).getId());
                    model.setName(ds.getValue(Model.class).getName());
                    model.setMimageurl(ds.getValue(Model.class).getMimageurl());
                    id = model.getId();
                    phonenum=model.getMobile();
                    picurl=model.getMimageurl();
                    mt_uid.setText(new StringBuilder("UID : ").append(model.getId()));
                    mt_num.setText(new StringBuilder("Mobile No : ").append(phonenum));
                    mt_status.setText(new StringBuilder("Name : ").append(model.getName()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)

                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quiting Smatr city")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    moveTaskToBack(true);

                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


}
