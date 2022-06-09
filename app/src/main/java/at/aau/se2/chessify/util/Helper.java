package at.aau.se2.chessify.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import at.aau.se2.chessify.Game;
import at.aau.se2.chessify.R;
import at.aau.se2.chessify.chessLogic.pieces.PieceColour;
import at.aau.se2.chessify.network.dto.PlayerDTO;

public class Helper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

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

    public static String getPlayerId(Context context) {
        String id = getSharedPreferences(context).getString("PLAYER_ID", null);
        if (id == null) {
            String randomId = UUID.randomUUID().toString().substring(0, 5);
            getSharedPreferences(context).edit().putString("PLAYER_ID", randomId).apply();
        }
        return getSharedPreferences(context).getString("PLAYER_ID", null);
    }

    public static void setPlayerColour(Context context, PieceColour colour) {
        getSharedPreferences(context).edit().putString("PLAYER_COLOUR", colour.name()).apply();
    }

    public static PieceColour getPlayerColour(Context context) {
        return PieceColour.valueOf(getSharedPreferences(context).getString("PLAYER_COLOUR", null));
    }

    public static void addGameIfNotExists(Context context, Game game) throws JsonProcessingException {
        List<Game> games = getGameList(context);
        Optional<String> gameId = games.stream().map(Game::getGameId).filter(id -> id.equals(game.getGameId())).findFirst();
        if (gameId.isPresent()) {
            return;
        }

        games.add(game);
        writeGamesList(context, games);
    }

    public static List<Game> deleteGame(Context context, int index) throws JsonProcessingException {
        List<Game> games = getGameList(context);
        games.remove(index - 1);
        writeGamesList(context, games);
        return games;
    }

    public static void writeGamesList(Context context, List<Game> games) throws JsonProcessingException {
        String gamesJsonStr = objectMapper.writeValueAsString(games);
        getSharedPreferences(context).edit().putString("GAMES_LIST", gamesJsonStr).apply();
    }

    public static List<Game> clearGamesList(Context context, int status) throws JsonProcessingException {
        List<Game> games = getGameList(context);
        if (status == Game.DEFAULT) {
            games = new ArrayList<>();
            writeGamesList(context, games);
            return games;
        }

        List<Game> toDelete = new ArrayList<>();
        games.forEach(game -> {
            if (game.getStatus() == status) {
                toDelete.add(game);
            }
        });

        games.removeAll(toDelete);
        writeGamesList(context, games);
        return games;
    }

    public static List<Game> getGameList(Context context) throws JsonProcessingException {
        String gamesListJsonStr = getSharedPreferences(context).getString("GAMES_LIST", null);
        if (gamesListJsonStr == null) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(gamesListJsonStr, new TypeReference<List<Game>>() {
        });
    }

    public static void setOpponent(Context context, PlayerDTO opponent) throws JsonProcessingException {
        String opponentJsonStr = objectMapper.writeValueAsString(opponent);
        getSharedPreferences(context).edit().putString("OPPONENT", opponentJsonStr).apply();
    }

    public static PlayerDTO getOpponent(Context context) throws JsonProcessingException {
        String opponentJsonStr = getSharedPreferences(context).getString("OPPONENT", null);
        return objectMapper.readValue(opponentJsonStr, PlayerDTO.class);
    }
}
