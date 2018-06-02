package Retrofit;

import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bijay on 4/26/2018.
 */

public class FCMClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseURL)
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
