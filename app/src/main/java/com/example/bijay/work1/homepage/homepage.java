package com.example.bijay.work1.homepage;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
//import android.app.Fragment;
import android.content.DialogInterface;
import android.location.Location;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bijay.work1.ListActivity;
import com.example.bijay.work1.Recycler_adapter;
import com.example.bijay.work1.locations_info.add_locations_info;
import com.example.bijay.work1.nav_options.About1;
import com.example.bijay.work1.Common;
import com.example.bijay.work1.nav_options.Events1;
import com.example.bijay.work1.MainActivity;
import com.example.bijay.work1.R;
import com.example.bijay.work1.nav_options.Setting1;
import com.example.bijay.work1.firedatabase.Model;
import com.example.bijay.work1.google_places.Results;
import com.example.bijay.work1.google_places.myplaces;
import com.example.bijay.work1.profile.Profile;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

import java.io.IOException;
import java.util.List;

import Retrofit.IGoogleApiservices;
import Retrofit.RetrofitApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;

public class homepage extends AppCompatActivity implements OnMapReadyCallback,
 GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    RecyclerView recyclerView;


    //Play Services
    private static final int MY_PERMISSION_REQUEST_CODE= 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    long PROXIMITY_RADIUS = 15000;
    double latitude,longitude;


    private static int UPDATE_INTERBAL = 5000;
    private static int FASTEST_INTERval = 3000;
    private static int DISPLACEMENT = 10;

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DrawerLayout mdrawer;
    ActionBarDrawerToggle mtoggle;
    NavigationView mnavbar;
    FrameLayout mframe;
    EditText msearchfield;
    ImageView mpro;
    TextView musername,musermob;
    DatabaseReference Ueser;
    StorageReference storageReference;
    LinearLayout mprofile;
    String uid,databaseid;
    ImageButton mmainsearch, mbtnsearch,mbtnback,mbtnreback,mbtnnext,mhospital,mschools,mrestaurants,mbar,mhotel,mhostel,mpharmacies,mambulance,mpizza,mcafe,
    mbank,matm,mgas_station,mparking,mcarwash,mtaxi,mhaircut,mshowroom,mgarage,mcasino,mcinema,mgym,mshopmall,mlodge,mconsultancy,mswimming,mfutsall;
    RelativeLayout moption_relative,mreoption_relative;

    GeoFire geoFire;
    Marker mCurrentUser;
    View mapView;
    IGoogleApiservices mservice;
    RetrofitApiInterface mservicelist;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



        mframe=findViewById(R.id.frame);
        moption_relative=findViewById(R.id.option_relative);
        mreoption_relative=findViewById(R.id.reoption_relative);
        mmainsearch=findViewById(R.id.mainsearchbtn);
        msearchfield=findViewById(R.id.searchfield);
        mbtnsearch=findViewById(R.id.btnsearch);
        mbtnback=findViewById(R.id.btnback);
        mbtnreback=findViewById(R.id.btnreback);
        mbtnnext=findViewById(R.id.btnnext);

        mhospital=findViewById(R.id.B_hospitals);
        mschools=findViewById(R.id.B_schools);
        mrestaurants=findViewById(R.id.B_restaurants);
        mbar=findViewById(R.id.B_bar);
        mpharmacies=findViewById(R.id.B_pharmacies);
        mpizza=findViewById(R.id.B_pizza);
        mambulance=findViewById(R.id.B_ambulance);
        mhostel=findViewById(R.id.B_hostel);
        mhotel=findViewById(R.id.B_hotels);
        mcafe=findViewById(R.id.B_cafe);
        mbank=findViewById(R.id.B_bank);
        matm=findViewById(R.id.B_atm);
        mgas_station=findViewById(R.id.B_pump);
        mparking=findViewById(R.id.B_park);
        mcarwash=findViewById(R.id.B_carwash);
        mtaxi=findViewById(R.id.B_taxi);
        mhaircut=findViewById(R.id.B_haircut);
        mshowroom=findViewById(R.id.B_showroom);
        mcasino=findViewById(R.id.B_casino);
        mgarage=findViewById(R.id.B_garage);
        mcinema=findViewById(R.id.B_cinema);
        mgym=findViewById(R.id.B_gym);
        mshopmall=findViewById(R.id.B_mall);
        mlodge=findViewById(R.id.B_lodge);
        mconsultancy=findViewById(R.id.B_consultancies);
        mswimming=findViewById(R.id.B_swimming);
        mfutsall=findViewById(R.id.futsall);






        mdrawer=findViewById(R.id.drawer);
        mtoggle=new ActionBarDrawerToggle(this,mdrawer,R.string.open,R.string.close);
        mdrawer.addDrawerListener(mtoggle);
        mnavbar=findViewById(R.id.navbar);
        View headerlayout=mnavbar.getHeaderView(0);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(mnavbar);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()== null) {
                    Intent backIntent=new Intent(homepage.this,MainActivity.class);
                    backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(backIntent);
                    finish();

                }
            }
        };





        // Map
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);


        //Request runtime permission
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checklocationpermission();
        }*/



        Ueser= FirebaseDatabase.getInstance().getReferenceFromUrl("https://work1-1da82.firebaseio.com/User");
        mAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("Photos");



        mpro=(ImageView)headerlayout.findViewById(R.id.pro);
        mprofile=(LinearLayout)headerlayout.findViewById(R.id.profile);
        musername=(TextView)headerlayout.findViewById(R.id.username);
        musermob=(TextView)headerlayout.findViewById(R.id.usermob);

        mservice= Common.getGoogleAPIservice();
        mservicelist=Common.getRetrofitApiInterface();



        setinform();
        mbtnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moption_relative.setVisibility(View.VISIBLE);
            }
        });

        mbtnsearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent places=new Intent(homepage.this,add_locations_info.class);
                startActivity(places);

                return false;
            }
        });


        mbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moption_relative.setVisibility(View.INVISIBLE);

            }
        });

        mbtnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mreoption_relative.setVisibility(View.VISIBLE);

            }
        });

        mbtnreback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mreoption_relative.setVisibility(View.INVISIBLE);

            }
        });



        mprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent profile=new Intent(homepage.this,Profile.class);
                startActivity(profile);
            }
        });


        setUpLocation();

        mhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("hospital");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();



               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String hospital = "hospital";
                String url = getUrl(latitude, longitude, hospital);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();*/
            }
        });

        mhospital.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                showhospitaldialog();
                return false;
            }
        });

        mschools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("college");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Colleges", Toast.LENGTH_SHORT).show();

               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String school = "school";
                String url = getUrl(latitude, longitude, school);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Schools", Toast.LENGTH_SHORT).show();*/

            }
        });

        mpharmacies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("pharmacy");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Pharmacies", Toast.LENGTH_SHORT).show();


               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String pharmacy = "pharmacy";
                String url = getUrl(latitude, longitude, pharmacy);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Pharmacies", Toast.LENGTH_SHORT).show();*/

            }
        });



        mambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             nearbyplace("ambulance");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby ambulances service", Toast.LENGTH_SHORT).show();

                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String ambulance = "ambulance";
                String url = getUrl(latitude, longitude, ambulance);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Ambulance services", Toast.LENGTH_SHORT).show();*/

            }
        });

        mconsultancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("consultancy");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby consultancies", Toast.LENGTH_SHORT).show();

                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String travel_agency= "travel_agency";
                String url = getUrl(latitude, longitude, travel_agency);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Travel Agencies", Toast.LENGTH_SHORT).show();  */
            }
        });

        mrestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nearbyplace("restaurant");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show();


                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String restaurant = "restaurant";
                String url = getUrl(latitude, longitude, restaurant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show();*/

            }
        });

        mrestaurants.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                getMenu("restaurant");

                return false;
            }
        });






        mhostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("hostel");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby hostels", Toast.LENGTH_SHORT).show();

                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String hostel = "hostel";
                String url = getUrl(latitude, longitude, hostel);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Hostels", Toast.LENGTH_SHORT).show();*/

            }
        });

        mhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nearbyplace("hotel");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Hotels", Toast.LENGTH_SHORT).show();


                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String hotel= "hotel";
                String url = getUrl(latitude, longitude, hotel);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Hotels", Toast.LENGTH_SHORT).show();*/

            }
        });

        mbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nearbyplace("bar");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Bars", Toast.LENGTH_SHORT).show();


               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String bar = "bar";
                String url = getUrl(latitude, longitude, bar);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Bars", Toast.LENGTH_SHORT).show();*/

            }
        });

      mpizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            nearbyplace("pizza store");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Bars", Toast.LENGTH_SHORT).show();


                /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String pizza = "pizza";
                String url = getUrl(latitude, longitude, pizza);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Pizza Stores", Toast.LENGTH_SHORT).show();*/

            }
        });

        mcafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nearbyplace("cafe");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Cafes", Toast.LENGTH_SHORT).show();


               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String cafe = "cafe";
                String url = getUrl(latitude, longitude, cafe);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Cafes", Toast.LENGTH_SHORT).show();*/

            }
        });

        mlodge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nearbyplace("lodging");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Lodges", Toast.LENGTH_SHORT).show();


                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String lodging = "lodging";
                String url = getUrl(latitude, longitude, lodging);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Lodges", Toast.LENGTH_SHORT).show();*/

            }
        });

        mshopmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("shopping_mall");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Shopping malls", Toast.LENGTH_SHORT).show();



                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String shopping_mall = "shopping_mall";
                String url = getUrl(latitude, longitude, shopping_mall);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Shopping Malls", Toast.LENGTH_SHORT).show();*/

            }
        });

        mcinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("movie_theater");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Cinema Halls", Toast.LENGTH_SHORT).show();

                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String movie_theater = "movie_theater";
                String url = getUrl(latitude, longitude, movie_theater);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Cinema Halls", Toast.LENGTH_SHORT).show();*/

            }
        });

        mgym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("gym");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Gym halls", Toast.LENGTH_SHORT).show();

                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String gym = "gym";
                String url = getUrl(latitude, longitude, gym);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Gym Halls", Toast.LENGTH_SHORT).show();*/

            }
        });

        mcasino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("casino");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Casinos", Toast.LENGTH_SHORT).show();

                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                moption_relative.setVisibility(View.INVISIBLE);
                String casino = "casino";
                String url = getUrl(latitude, longitude, casino);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Casinos", Toast.LENGTH_SHORT).show();*/

            }
        });

        mbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nearbyplace("bank");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Banks", Toast.LENGTH_SHORT).show();

                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);

                String bank = "bank";
                String url = getUrl(latitude, longitude, bank);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Banks", Toast.LENGTH_SHORT).show();*/

            }
        });


        matm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("atm");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby ATMs", Toast.LENGTH_SHORT).show();


                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                String atm = "atm";
                String url = getUrl(latitude, longitude, atm);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby ATMs", Toast.LENGTH_SHORT).show();*/

            }
        });

        mgas_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("fuel");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Fuel Pumps", Toast.LENGTH_SHORT).show();


                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                String gas_station = "gas_station";
                String url = getUrl(latitude, longitude, gas_station);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Fuel Pumps", Toast.LENGTH_SHORT).show();*/

            }
        });

        mgarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("workshop");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby workshops", Toast.LENGTH_SHORT).show();



               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                String car_repair = "car_repair";
                String url = getUrl(latitude, longitude, car_repair);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Garages", Toast.LENGTH_SHORT).show();*/

            }
        });

        mparking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("parking");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Parking Areas", Toast.LENGTH_SHORT).show();

                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                String parking  = "parking";
                String url = getUrl(latitude, longitude, parking);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Parking Areas", Toast.LENGTH_SHORT).show();*/

            }
        });

        mcarwash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("car wash");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Car Wash Areas", Toast.LENGTH_SHORT).show();


               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                String car_wash = "car_wash";
                String url = getUrl(latitude, longitude, car_wash);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Carwash Areas", Toast.LENGTH_SHORT).show();*/

            }
        });

        mtaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("taxi");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Taxi Stands", Toast.LENGTH_SHORT).show();


                /*mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                String taxi_stand = "taxi_stand";
                String url = getUrl(latitude, longitude, taxi_stand);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Taxi stands", Toast.LENGTH_SHORT).show();*/

            }
        });


        mshowroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("showroom");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby Showrooms", Toast.LENGTH_SHORT).show();


               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                String car_dealer = "car_dealer";
                String url = getUrl(latitude, longitude, car_dealer);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Showrooms", Toast.LENGTH_SHORT).show();*/

            }
        });

        mhaircut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearbyplace("hair cut");
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby HAir Cares", Toast.LENGTH_SHORT).show();


               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();
                mreoption_relative.setVisibility(View.INVISIBLE);
                moption_relative.setVisibility(View.INVISIBLE);
                String hair_care = "hair_care";
                String url = getUrl(latitude, longitude, hair_care);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Hair cares", Toast.LENGTH_SHORT).show();*/

            }
        });

        mswimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nearbyplace("swimming pool");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby swimming pools", Toast.LENGTH_SHORT).show();


               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();

                moption_relative.setVisibility(View.INVISIBLE);
                String swimming_pool = "swimming_pool";
                String url = getUrl(latitude, longitude, swimming_pool);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Swimming pools", Toast.LENGTH_SHORT).show();*/

            }
        });


        mfutsall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nearbyplace("futsal");
                moption_relative.setVisibility(View.INVISIBLE);
                Toast.makeText(homepage.this, "Showing Nearby futsals", Toast.LENGTH_SHORT).show();


               /* mMap.clear();
                Object dataTransfer[] = new Object[2];
                GetNearbyplacesData getNearbyPlacesData = new GetNearbyplacesData();

                moption_relative.setVisibility(View.INVISIBLE);
                String swimming_pool = "swimming_pool";
                String url = getUrl(latitude, longitude, swimming_pool);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(homepage.this, "Showing Nearby Swimming pools", Toast.LENGTH_SHORT).show();*/

            }
        });







    }


    /*private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyDbYb5986R2ZwCYBEetIVwgbZ-gz10QYRg");

        Log.d("MapActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }*/




    private void nearbyplace(final String placeType){
        mMap.clear();
        String url=getUrl(latitude,longitude,placeType);
        mservice.getNearByPlaces(url)
                .enqueue(new Callback<myplaces>() {
                    @Override
                    public void onResponse(Call<myplaces> call, Response<myplaces> response) {
                        if(response.isSuccessful()){
                            for(int i=0;i<response.body().getResults().length;i++){
                                MarkerOptions markerOptions=new MarkerOptions();
                                Results googlePlace=response.body().getResults()[i];
                                double lat = Double.parseDouble( googlePlace.getGeometry().getLocation().getLat());
                                double lng = Double.parseDouble( googlePlace.getGeometry().getLocation().getLng());
                                String placeName=googlePlace.getName();
                                String vicinity=googlePlace.getVicinity();
                                LatLng latLng=new LatLng(lat,lng);
                                markerOptions.position(latLng);
                                markerOptions.title(placeName + " : "+ vicinity);
                                if(placeType.equals("hospital"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hospital));

                                else if(placeType.equals("pharmacy"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pharmacy));

                                else if(placeType.equals("restaurant"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurant));

                                else if(placeType.equals("college"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_school));

                                else if(placeType.equals("consultancy"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_consultancies));

                                else if(placeType.equals("hotel"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hotel));

                                else if(placeType.equals("cafe"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cafe));

                                else if(placeType.equals("bar"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_bar));

                                else if(placeType.equals("lodging"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_lodging));

                                else if(placeType.equals("shopping_mall"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_mall));

                                else if(placeType.equals("cinema"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cinema));

                                else if(placeType.equals("gym"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_gym));

                                else if(placeType.equals("casino"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_casino));

                                else if(placeType.equals("bank"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_bank));

                                else if(placeType.equals("atm"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_atm));

                                else if(placeType.equals("fuel"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_gasstation));

                                else if(placeType.equals("workshop"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_garage));

                                else if(placeType.equals("parking"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_parking));

                                else if(placeType.equals("car wash"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_carwash));

                                else if(placeType.equals("taxi"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_taxi));

                                else if(placeType.equals("showroom"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_showroom));

                                else if(placeType.equals("hair cut"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_haircare));

                                else if(placeType.equals("ambulance"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_ambulance));

                                else if(placeType.equals("swimming pool"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_swimming));

                                else if(placeType.equals("futsal"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_futsal));

                                else if(placeType.equals("pizza store"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pizza));

                                else if(placeType.equals("hostel"))
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hostel));




                                else
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));


                                mMap.addMarker(markerOptions);
                                //Move Camera
                                mMap.moveCamera(newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<myplaces> call, Throwable t) {

                    }
                });

    }



    private void getMenu(final String placeType){
        String url=getUrl(latitude,longitude,placeType);
        mservicelist.getMenuJson(url).enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {



                Recycler_adapter recycler_adapter = new Recycler_adapter(homepage.this,response.body());
                //  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(homepage.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManager);
                recyclerView.setHasFixedSize(true);

                //recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recycler_adapter);
                recycler_adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {

            }
        });


    }

    private String getUrl(double latitude,double longitude,String placeType){

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&keyword="+placeType);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyAsEdfrZ7qJ-sMdTHs7RQAk78PsfScpjqk");

        Log.d("MapActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
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


                Intent b =new Intent(homepage.this,homepage.class);
               startActivity(b);
                break;
            case R.id.event:
                //fragmentClass=Events.class;
                mdrawer.closeDrawers();
               // menuItem.setChecked(true);


                Intent eventintent =new Intent(homepage.this,Events1.class);
                startActivity(eventintent);
                break;

            case R.id.settings:
                //fragmentClass=Setting.class;
                mdrawer.closeDrawers();
                //menuItem.setChecked(true);
                //getSupportActionBar().setTitle(menuItem.getTitle());

               Intent settingintent =new Intent(homepage.this,Setting1.class);
                startActivity(settingintent);

                break;
            case R.id.logout:
              //fragmentClass=Logout.class;
               //logout();

                FirebaseAuth.getInstance().signOut();
                finish();
                break;

            case R.id.about:
                //fragmentClass=About.class;
                mdrawer.closeDrawers();
                //menuItem.setChecked(true);
               // getSupportActionBar().setTitle(menuItem.getTitle());

                Intent aboutintent =new Intent(homepage.this,About1.class);
                startActivity(aboutintent);

                break;


            default:
                //menuItem.setChecked(true);
                //getSupportActionBar().setTitle(menuItem.getTitle());
                //fragmentClass=Home.class;

                Intent d =new Intent(homepage.this,homepage.class);
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

    private void showhospitaldialog() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Hospital");
        dialog.setMessage("what do u want?");
        LayoutInflater inflater = LayoutInflater.from(this);
        View mlongpress = inflater.inflate(R.layout.long_press, null);

        final MaterialEditText msearch_hospital=mlongpress.findViewById(R.id.search_hospital);
        final ImageButton mhospital_serachbtn=mlongpress.findViewById(R.id.hospital_searchbtn);

        mhospital_serachbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (msearch_hospital.getText().toString().trim().isEmpty()) {
                    msearch_hospital.setError("Hospital Name Required !!");
                    msearch_hospital.requestFocus();
                    return;
                }
            }
        });
        dialog.setView(mlongpress);

       dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
           }
       });

       dialog.show();




    }



    public void onSearch(View view){
        String location=msearchfield.getText().toString();

        if(location.isEmpty()){
            msearchfield.setError("Enter The Location Required !!");
            msearchfield.requestFocus();
            return;

        }
        List<Address>addressList=null;
        if(location!=null || !location.equals("")){

            Geocoder geocoder=new Geocoder(this);
            try {

                addressList=geocoder.getFromLocationName(location,1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(location)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));



            //Move camera
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(),address.getLongitude()),15.0f));

        }else
        {

        }
    }

    public void changemap(View view){
        if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL){

            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }



    public void setinform(){

        Ueser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Model model = new Model();


                /*uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseid = model.getId();

                if ((uid).equals(model.getId())) {*/


                    for (DataSnapshot es: dataSnapshot.getChildren()) {
                        Model model1 = new Model();
                        model1.setMobile(es.getValue(Model.class).getMobile());
                        model1.setName(es.getValue(Model.class).getName());
                        model1.setMimageurl(es.getValue(Model.class).getMimageurl());


                        musermob.setText(model1.getMobile());
                        musername.setText(model1.getName());

                        Picasso.with(homepage.this)
                                .load(model1.getMimageurl())
                                .fit()
                                .centerCrop()
                                .into(mpro);

                    }

            }


                    @Override
                    public void onCancelled (DatabaseError databaseError){

                    }


        });



    }


    //private void logout() {

       // mAuth.signOut();
        //Intent backintent=new Intent(homepage.this,MainActivity.class);
        //backintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(backintent);
       // finish();
   // }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


  /*  public boolean checklocationpermission(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this,new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION

            },MY_PERMISSION_REQUEST_CODE);

            else

                ActivityCompat.requestPermissions(this,new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION

                },MY_PERMISSION_REQUEST_CODE);

            return false;

        }
        return true;
    }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayServices()) {

                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();
                    }

                }
                break;
        }
    }




    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            //Request Runtime permissions
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_CODE);

        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();

                displayLocation();
            }
        }

    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            //Add MArker
            if (mCurrentUser != null)
                mCurrentUser.remove();

            mCurrentUser = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title("Your Location"));



            //Move camera
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));


            Log.d("Update", String.format("Your location has changed: %f/ %f", latitude, longitude));


        } else {
            Log.d("ERROR", "Cannot get your Location");
        }

        //if (mGoogleApiClient!=null){
          //  LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        //}
    }

        private void createLocationRequest(){

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERBAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERval);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        }

        private void buildGoogleApiClient() {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();


        }

        private boolean checkPlayServices() {

            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError((resultCode)))
                    GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICE_RES_REQUEST).show();
                else
                    Toast.makeText(this, "This Device is Not Supported", Toast.LENGTH_SHORT).show();
                finish();
                return (false);
            }
            return (true);

        }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);




        //mMap.setInfoWindowAdapter(new CustomInfoWindow(this));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on left bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(70,0,5,5);

        }


    }





    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, (com.google.android.gms.location.LocationListener) this);



    }



    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();

    }





    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quiting Smart city")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }



}


