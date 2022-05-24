package at.aau.se2.chessify.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

import java.util.UUID;

import at.aau.se2.chessify.R;

public class Helper {

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getSharedPreferencesWithContext(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    // --> Sound
    public static boolean getBackgroundSound(Context context) {
        return getSharedPreferences(context).getBoolean("Background Sound", true);
    }

    public static void setBackgroundSound(Context context, boolean sound) {
        getSharedPreferences(context).edit().putBoolean("Background Sound", sound).apply();
    }


    // --> Vibrations
    public static boolean getVibration(Context context) {
        return getSharedPreferences(context).getBoolean("Device Vibration", true);
    }

    public static void setVibration(Context context, boolean vibration) {
        getSharedPreferences(context).edit().putBoolean("Device Vibration", vibration).apply();
    }


    // --> Dark Mode
    public static boolean getDarkmode(Context context) {
        return getSharedPreferences(context).getBoolean("Darkmode", true);
    }

    public static void setDarkmode(Context context, boolean darkmode) {
        getSharedPreferences(context).edit().putBoolean("Darkmode", darkmode).apply();
    }


    // --> Player Name
    public static String getPlayerName(Context context) {
        return getSharedPreferences(context).getString("Player", "Name");
    }

    public static void setPlayerName(Context context, String type) {
        getSharedPreferences(context).edit().putString("Player", type).apply();
    }

    public static String getUniquePlayerName(Context context) {
        return getSharedPreferences(context).getString("Player", "Name") + "#" + getPlayerId(context);
    }

    // --> Player ID
    public static void setGameId(Context context, String gameId) {
        getSharedPreferences(context).edit().putString("GAME_ID", gameId).apply();
    }

    public static String getGameId(Context context) {
        return getSharedPreferences(context).getString("GAME_ID", null);
    }

    static MediaPlayer mediaPlayer;

    public static void playMusicBackground(Activity context) {
        try {
            mediaPlayer = MediaPlayer.create(context, R.raw.backgroundmusic);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusicBackground(Activity context) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPlayerId(Context context) {
        String id = getSharedPreferences(context).getString("PLAYER_ID", null);
        if(id == null) {
            String randomId = UUID.randomUUID().toString().substring(0, 5);
            getSharedPreferences(context).edit().putString("PLAYER_ID", randomId).apply();
        }
        return getSharedPreferences(context).getString("PLAYER_ID", null);
    }

}
