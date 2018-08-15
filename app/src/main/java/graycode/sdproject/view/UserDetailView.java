package graycode.sdproject.view;

import graycode.sdproject.dto.UserDTO;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public interface UserDetailView {
    void onResponseSuccess(UserDTO userDTO);
    void onResponseFailure(String message);

}
