package graycode.sdproject.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import graycode.sdproject.dto.User;
import graycode.sdproject.dto.UserDTO;
import graycode.sdproject.manager.UserConnectionManager;
import graycode.sdproject.network.APICallback;
import graycode.sdproject.view.UserDetailView;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public class UserDetailPresenter {
    public UserDetailView userDetailView;

    public UserDetailPresenter(UserDetailView view) {
        this.userDetailView = view;
    }

    public void getUserDetails(Context context, int offset, int limit) {
        UserConnectionManager.getUserDetails(context, offset, limit, new APICallback<UserDTO>(context) {

            @Override
            public void onResponseSuccess(UserDTO userDTO) {
                if(userDTO==null || userDTO.data==null)
                    return;
                UserDetailView view = userDetailView;
                if (view == null)
                    return;
                view.onResponseSuccess(userDTO);
            }

            @Override
            public void onResponseFailure(String message) {
                UserDetailView view = userDetailView;
                if (view == null)
                    return;
                view.onResponseFailure(message);
            }

            @Override
            public void onResponseErrors(UserDTO userDTO) {
                UserDetailView view = userDetailView;
                if (view == null)
                    return;
                if(userDTO.message==null)
                    view.onResponseFailure(userDTO.message);
            }

        });


    }


}
