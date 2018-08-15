package graycode.sdproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public class Util {

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;

       // return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
