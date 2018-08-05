package com.example.bijay.work1;

import Retrofit.IGoogleApiservices;
import Retrofit.RetrofitApiInterface;
import Retrofit.RetrofitClient;

/**
 * Created by Bijay on 5/19/2018.
 */

public class Common {

    private static final String GOOGlE_API_URL="https://maps.googleapis.com/";
    public static IGoogleApiservices getGoogleAPIservice(){
        return RetrofitClient.getClient(GOOGlE_API_URL).create(IGoogleApiservices.class);
    }

    public static RetrofitApiInterface getRetrofitApiInterface(){
        return RetrofitClient.getClient(GOOGlE_API_URL).create(RetrofitApiInterface.class);
    }


}
