package Retrofit;

import com.example.bijay.work1.google_places.Results;
import com.example.bijay.work1.google_places.myplaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Bijay on 6/7/2018.
 */

public interface RetrofitApiInterface  {

    @GET
    Call<List<Results>> getMenuJson(@Url String url);
}
