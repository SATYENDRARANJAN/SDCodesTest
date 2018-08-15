package graycode.sdproject.manager;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import graycode.sdproject.Constants;
import graycode.sdproject.R;
import graycode.sdproject.dto.UserDTO;
import graycode.sdproject.network.APICallback;
import graycode.sdproject.network.APIUtil;
import retrofit2.Call;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public class UserConnectionManager {

    public static void getUserDetails(Context context ,int offset , int limit ,  APICallback<UserDTO> callback){
        Map<String, String> queryMap = new HashMap<>();
        if(queryMap!=null){
            queryMap.put(Constants.KEY_OFFSET,String.valueOf(offset));
            queryMap.put(Constants.KEY_LIMIT,String.valueOf(limit));
        }
        String url = context.getString(R.string.user_api);
        Call<UserDTO> callObj = APIUtil.getInstance().getApiInterface().getUserDetails(url, queryMap);
        callObj.enqueue(callback);
    }
}
