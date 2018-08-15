package graycode.sdproject.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Satyendra.Ranjan on 13-08-2018.
 */

public class APIUtil {
    private static APIUtil sInstance;
    private static Retrofit mRetrofit;


    public   APIUtil(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://sd2-hiring.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static APIUtil getInstance(){
        if(sInstance == null){
            sInstance = new APIUtil();
        }
        return sInstance;
    }

    public APIInterface getApiInterface() {
        return mRetrofit.create(APIInterface.class);
    }


}
