package bravostudio.nyeni.Custom;

import android.content.Context;
import android.content.SharedPreferences;

import bravostudio.nyeni.R;

/**
 * Created by jouvyap on 7/22/16.
 */
public class SharedPreferencesHelper {

    SharedPreferences sharedPreferences;

    private static final String KEY_IS_LOGGED_IN = "BRAVOSTUDIO.KEY.LOGGEDIN";
    private static final String KEY_USER_LOGGED_IN = "BRAVOSTUDIO.KEY.USERLOGGEDIN";

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string
                .preference_file_key), Context.MODE_PRIVATE);
    }

    public void setLoggedIn(boolean loggedIn){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, loggedIn);
        editor.commit();
    }

    public boolean getIsLoggedIn(){
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setUsernameLoggedIn(String username){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_LOGGED_IN, username);
        editor.commit();
    }

    public String getUsernameLoggedIn(){
        return sharedPreferences.getString(KEY_USER_LOGGED_IN, "NULL");
    }
}
