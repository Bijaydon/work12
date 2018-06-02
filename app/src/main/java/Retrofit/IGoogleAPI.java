package Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Bijay on 4/26/2018.
 */

public interface IGoogleAPI {

    @GET
    Call<String> getPath(@Url String url);
}
