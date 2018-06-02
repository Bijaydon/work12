package Retrofit;

import com.example.bijay.work1.google_places.myplaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Bijay on 5/19/2018.
 */

public interface IGoogleApiservices {

    @GET
    Call<myplaces> getNearByPlaces (@Url String url);
}
