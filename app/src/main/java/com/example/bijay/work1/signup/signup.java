package com.example.bijay.work1.signup;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bijay.work1.R;
import com.example.bijay.work1.firedatabase.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

public class signup extends AppCompatActivity  {
    private static final int GALLERY_INTENT = 101;
    //private static final int CAMERA_REQUEST =1;

    EditText mnamefield, memailfield, mmobfield, mpasswordfield, mconfirmfield, maddressfield;
    Button mbtnsubmit;
    Uri uri;
    ProgressDialog mprogress;
    ImageButton mpropic;
    StorageReference profileref;
    String imageUrl;
    TextView msignup;
    RelativeLayout mrelativelayout;
    private LinearLayout mmainlayout;
    RadioButton mradioButton;
    String popic;
    private StorageTask muploading;  //to stop multiple uploading in long press of radiobutton in my case.

    //private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;

    private FirebaseAuth.AuthStateListener mAuthListener;
    //DatabaseReference datauser;
    FirebaseAuth auth;
    DatabaseReference Ueser;


    //(for image capture.....)

    //String mCurrentPhotoPath;

    //private File createImageFile() throws IOException {
// Create an image file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String imageFileName = "JPEG_" + timeStamp + "_";
       // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
       // File image = File.createTempFile(
                //imageFileName,  /* prefix */
                //".jpg",         /* suffix */
               // storageDir      /* directory */
        //);

// Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = image.getAbsolutePath();
       // return image;
    //}

    /*private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                takePictureIntent.setAction(Intent.ACTION_PICK);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                startActivityForResult(Intent.createChooser(takePictureIntent, "Select profile picture"),CAMERA_REQUEST );
            }
        }
    }*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //to hide keypad initially
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mnamefield = findViewById(R.id.namefield);
        memailfield = findViewById(R.id.emailfield);
        mmobfield = findViewById(R.id.mobfield);
        mpasswordfield = findViewById(R.id.passwordfield);
        mconfirmfield = findViewById(R.id.confirmfield);
        maddressfield = findViewById(R.id.addressfield);
        mbtnsubmit = findViewById(R.id.btnsubmit);
        mprogress = new ProgressDialog(this);
        mpropic = findViewById(R.id.propic);
        mmainlayout = findViewById(R.id.mainlayout);
        msignup=findViewById(R.id.signup);
        mradioButton=findViewById(R.id.radioButton);
        mrelativelayout = findViewById(R.id.relativelayout);


        auth = FirebaseAuth.getInstance();
        Ueser = FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/User");



            mradioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(uri !=null) {
                        mpropic.setEnabled(false);
                        mradioButton.setEnabled(false);
                        firebaseimageupload();
                        //galleryAddPic();

                    }

                }
            });

        mbtnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(muploading!=null&& muploading.isInProgress()){

                    msignup.setText("* Image Upload In Progress !!");
                    msignup.requestFocus();

                }else{
                    msignup.setText("* Image Upload successful !!");
                    msignup.requestFocus();
                    register();
                }
            }
        });

        mpropic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimage();
                //dispatchTakePictureIntent();
            }
        });


    }

    public void uploadimage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select profile picture"), GALLERY_INTENT );



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            //setPic();

            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mpropic.setImageBitmap(imageBitmap);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mpropic.setImageBitmap(bitmap);
                //firebaseimageupload();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }//else if (requestCode ==CAMERA_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                //uri = data.getData();

            //Picasso.with(signup.this).load(uri).into(mpropic);
        //}
    }


    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return  mine.getExtensionFromMimeType(cR.getType(uri));
    }


    //Decode a scaled image

   /* private void setPic() {
        // Get the dimensions of the View
        int targetW = mpropic.getWidth();
        int targetH = mpropic.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mpropic.setImageBitmap(bitmap);
    }



//Add captured picture in gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }*/


    private void firebaseimageupload() {

        if (uri != null) {
            profileref= FirebaseStorage.getInstance().getReference("Photos/" + System.currentTimeMillis()+"."+getFileExtension(uri) );//.child(uri.getLastPathSegment());//+ ".jpg");
            final SpotsDialog waitingdialog = new SpotsDialog(signup.this);
            waitingdialog.show();
            muploading=profileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                   waitingdialog.dismiss();

                    imageUrl = taskSnapshot.getDownloadUrl().toString();


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //waitingdialog.dismiss();
                            Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }//else{

           // msignup.setText("Profile picture Required");
           // msignup.requestFocus();
            //return;
        //}


    }

    public void register() {

        final String image=imageUrl;
        String name1 = mnamefield.getText().toString().trim();
        String email_Address1 = memailfield.getText().toString().trim();
        final String mobileno1 = mmobfield.getText().toString().trim();
        String password1 = mpasswordfield.getText().toString().trim();
        String confirm_Password1 = mconfirmfield.getText().toString().trim();
        final String address1 = maddressfield.getText().toString().trim();

        if (uri != null) {
            //mprogress.dismiss();
            // msignup.setError("Profile picture Required");
            //msignup.requestFocus();
            //return;
            //}
            if(!mradioButton.isEnabled()) {


                if (name1.isEmpty()) {
                    mprogress.dismiss();
                    mnamefield.setError("Name is Required");
                    mnamefield.requestFocus();
                    return;
                }


                if (email_Address1.isEmpty()) {
                    mprogress.dismiss();
                    memailfield.setError("Email is Required");
                    memailfield.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email_Address1).matches()) {
                    mprogress.dismiss();
                    memailfield.setError("Please enter Valid email");
                    memailfield.requestFocus();
                    return;

                }

                if (mobileno1.isEmpty()) {
                    mprogress.dismiss();
                    mmobfield.setError("Mobile No is required");
                    mmobfield.requestFocus();
                    return;
                }
                //if (!Patterns.PHONE.matcher(mobileno1).matches() || mobileno1.length() != 10) {
                //mprogress.dismiss();
                //mmobfield.setError("Please enter valid number");
                // mmobfield.requestFocus();
                // return;
                //}

                if (password1.isEmpty()) {
                    mprogress.dismiss();
                    mpasswordfield.setError("Password is Required");
                    mpasswordfield.requestFocus();
                    return;
                }

                if (password1.length() < 6) {
                    mprogress.dismiss();
                    mpasswordfield.setError("At least 6 characters");
                    mpasswordfield.requestFocus();
                    return;
                }

                if (confirm_Password1.isEmpty()) {
                    mprogress.dismiss();
                    mconfirmfield.setError("Confirm your Passsword");
                    mconfirmfield.requestFocus();
                    return;
                }

                if (!(password1.equals(confirm_Password1))) {
                    mprogress.dismiss();
                    mconfirmfield.setError("Password is not confirmed");
                    mconfirmfield.requestFocus();
                    return;
                }

                if (address1.isEmpty()) {
                    mprogress.dismiss();
                    maddressfield.setError("Address is Required");
                    maddressfield.requestFocus();
                    return;
                }else {

                    final SpotsDialog waitingdialog = new SpotsDialog(signup.this);
                    waitingdialog.show();

                    //auth.createUserWithEmailAndPassword(memailfield.getText().toString(),mpasswordfield.getText().toString())
                    //.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    // @Override
                    // public void onSuccess(AuthResult authResult) {

                    //Save to database


                    String id = Ueser.push().getKey();

                    Model model = new Model();
                    model.setAddress(address1);
                    model.setMimageurl(image);
                    model.setEmail(memailfield.getText().toString());
                    model.setName(mnamefield.getText().toString());
                    model.setPassword(mpasswordfield.getText().toString());
                    model.setMobile(mmobfield.getText().toString());
                    model.setId(id);


                    Ueser.child(id).setValue(model);


                    //.addOnSuccessListener(new OnSuccessListener<Void>() {
                    // @Override
                    //public void onSuccess(Void aVoid) {

                    //}
                    // });


                    // }
                    //});
                    // Toast.makeText(Signup1.this, "Please verify your email", Toast.LENGTH_SHORT).show();

                    waitingdialog.dismiss();
                    finish();
                    Toast.makeText(signup.this, " Submitted", Toast.LENGTH_SHORT).show();
                    Intent k = new Intent(signup.this, verification.class);
                    startActivity(k);



                /*auth.createUserWithEmailAndPassword(memailfield.getText().toString(),mpasswordfield.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            //store user in authentication
                            Snackbar.make(mrelativelayout, "Please verify your email", Snackbar.LENGTH_SHORT).show();
                            //finish();
                            Intent intent = new Intent(signup.this, email_verification.class);
                            startActivity(intent);
                            finish();

                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                            //Toast.makeText(Signup1.this, "You are already registered", Toast.LENGTH_SHORT).show();
                            //} else {
                            Snackbar.make(mrelativelayout, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                            //}
                        } else {
                            // If sign in fails, display a message to the user.

                            Snackbar.make(mrelativelayout, "No internet connection", Snackbar.LENGTH_SHORT).show();
                            Snackbar.make(mrelativelayout, "Authentication failed", Snackbar.LENGTH_SHORT).show();

                        }
                    }
                });*/

                }
            }else {

                msignup.setText("* Confirm by clicking (DONE) !!");
                msignup.requestFocus();

            }
        }else {

            msignup.setText("* Profile picture Required !!");
            msignup.requestFocus();
           //return;

        }

    }

}













