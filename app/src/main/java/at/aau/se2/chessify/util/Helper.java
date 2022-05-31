package at.aau.se2.chessify.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;

import at.aau.se2.chessify.R;

public class Helper {

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getSharedPreferencesWithContext(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    // --> Sound Menu
    public static boolean getBackgroundSound(Context context) {
        return getSharedPreferences(context).getBoolean("Background Sound", true);
    }

    public static void setBackgroundSound(Context context, boolean sound) {
        getSharedPreferences(context).edit().putBoolean("Background Sound", sound).apply();
    }

    // --> Sound SBM Bar
    public static boolean getSMB_BarSound(Context context) {
        return getSharedPreferences(context).getBoolean("Background Sound", true);
    }

    public static void setSMB_BarSound(Context context, boolean sound) {
        getSharedPreferences(context).edit().putBoolean("Background Sound", sound).apply();
    }

    // --> Sound Game
    public static boolean getGameSound(Context context) {
        return getSharedPreferences(context).getBoolean("Background Sound", true);
    }

    public static void setGameSound(Context context, boolean sound) {
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

    // --> Player ID
    public static void setGameId(Context context, String gameId) {
        getSharedPreferences(context).edit().putString("GAME_ID", gameId).apply();
    }

    public static String getGameId(Context context) {
        return getSharedPreferences(context).getString("GAME_ID", null);
    }

    static MediaPlayer mediaPlayer_Menu;
    static MediaPlayer mediaPlayer_Game;
    static MediaPlayer SMB_Bar;

    // --> Sound SMB Bar
    public static void playSMB_BarSound(Activity context) {
        try {
            SMB_Bar = MediaPlayer.create(context, R.raw.smb_activate);
            SMB_Bar.start();
            //SMB_Bar.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopSMB_BarSound(Activity context) {
        try {
            if (SMB_Bar != null) {
                SMB_Bar.stop();
                SMB_Bar.release();
                SMB_Bar = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --> Sound Menu
    public static void playMusicBackground(Activity context) {
        try {
            mediaPlayer_Menu = MediaPlayer.create(context, R.raw.backgroundmusic);
            mediaPlayer_Menu.start();
            mediaPlayer_Menu.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusicBackground(Activity context) {
        try {
            if (mediaPlayer_Menu != null) {
                mediaPlayer_Menu.stop();
                mediaPlayer_Menu.release();
                mediaPlayer_Menu = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --> Sound Game
    public static void playGameSound(Activity context) {
        try {
            mediaPlayer_Game = MediaPlayer.create(context, R.raw.ticking);
            mediaPlayer_Game.start();
            mediaPlayer_Game.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopGameSound(Activity context) {
        try {
            if (mediaPlayer_Game != null) {
                mediaPlayer_Game.stop();
                mediaPlayer_Game.release();
                mediaPlayer_Game = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
