package graycode.sdproject.network;

import java.util.Map;

import graycode.sdproject.dto.UserDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public interface APIInterface {

    @GET
    Call<UserDTO> getUserDetails(@Url String url , @QueryMap(encoded = true) Map<String , String > queries);
}
