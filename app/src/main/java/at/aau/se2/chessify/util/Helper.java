package at.aau.se2.chessify.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Helper {

    public static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getSharedPreferencesWithContext(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean getBackgroundSound(Context context){
       return getSharedPreferences(context).getBoolean("Background Sound", true);
    }

    public static void setBackgroundSound (Context context, boolean sound){
        getSharedPreferences(context).edit().putBoolean("Background Sound", sound).apply();
    }

    public static String getPlayerName (Context context){
        return getSharedPreferences(context).getString("Player", "Name");
    }

    public static void setPlayerName (Context context, String type){
        getSharedPreferences(context).edit().putString("Player", type).apply();
    }


}
