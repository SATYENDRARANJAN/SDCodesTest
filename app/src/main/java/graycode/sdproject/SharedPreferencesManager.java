package graycode.sdproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public class SharedPreferencesManager {

    public interface  KEYS{
        public static String KEY_OFFSET ="OFFSET";
        public static String KEY_LIMIT ="LIMIT";
    }

    private static SharedPreferencesManager sInstance;
    private  SharedPreferences mPrefs;
    private  SharedPreferences.Editor mEditor;


    private SharedPreferencesManager(Context context){
        mPrefs = context.getApplicationContext().getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }

    public static SharedPreferencesManager getInstance(Context context){
        if(sInstance ==null){
            sInstance =  new SharedPreferencesManager(context);
        }
        return sInstance;
    }

    public  int getOffset(){
        return mPrefs.getInt(KEYS.KEY_OFFSET,0);
    }

    public  void setOffset(int offset){
        mEditor.putInt(KEYS.KEY_OFFSET,offset).commit();
    }


    public int getLimit(){
        return  mPrefs.getInt(KEYS.KEY_LIMIT,10);
    }

    public void  setLimit(int limit ){
        mEditor.putInt(KEYS.KEY_LIMIT,limit).commit();
    }

}
