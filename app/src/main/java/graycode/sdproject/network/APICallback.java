package graycode.sdproject.network;


import android.content.Context;

import graycode.sdproject.dto.UserDTO;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public abstract  class APICallback<T>  implements retrofit2.Callback<T> {
    private Context mCtx;

    private APICallback(){
    }

    public APICallback(Context ctx ){
        this.mCtx =ctx;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if(response.isSuccessful()) {
            UserDTO userDTO =(UserDTO)response.body();
            if( userDTO!= null && userDTO.status==true){
                onResponseSuccess(userDTO);
            }else{
                onResponseErrors(userDTO);
            }
        }/*else if(response.code()==401 || response.code()==403){
            //auth token expired . Redirect to Login 
        }*/else {
            onResponseFailure(response.message());
        }
    }

    public abstract void onResponseFailure(String message);

    public abstract void onResponseErrors(UserDTO userDTO) ;

    public abstract void onResponseSuccess(UserDTO userDTO) ;

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onResponseFailure("OOPS . There is a Problem !");
    }




}
