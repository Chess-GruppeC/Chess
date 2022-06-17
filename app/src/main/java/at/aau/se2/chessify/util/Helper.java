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
    private static final String BACKGROUND_SOUND = "Background Sound";
    private static final String PLAYER = "Player";
    public static final String PLAYER_ID = "PLAYER_ID";

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    // --> Sound Menu
    public static boolean getBackgroundSound(Context context) {
        return getSharedPreferences(context).getBoolean(BACKGROUND_SOUND, true);
    }

    public static void setBackgroundSound(Context context, boolean sound) {
        getSharedPreferences(context).edit().putBoolean(BACKGROUND_SOUND, sound).apply();
    }

    // --> Sound Game
    public static boolean getGameSound(Context context) {
        return getSharedPreferences(context).getBoolean(BACKGROUND_SOUND, true);
    }

    public static void setGameSound(Context context, boolean sound) {
        getSharedPreferences(context).edit().putBoolean(BACKGROUND_SOUND, sound).apply();

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
        return getSharedPreferences(context).getString(PLAYER, "Name");
    }

    public static void setPlayerName(Context context, String type) {
        getSharedPreferences(context).edit().putString(PLAYER, type).apply();
    }

    public static String getUniquePlayerName(Context context) {
        return getSharedPreferences(context).getString(PLAYER, "Name") + "#" + getPlayerId(context);
    }

    // --> Player ID
    public static void setGameId(Context context, String gameId) {
        getSharedPreferences(context).edit().putString("GAME_ID", gameId).apply();
    }

    public static String getGameId(Context context) {
        return getSharedPreferences(context).getString("GAME_ID", null);
    }

    static MediaPlayer mediaPlayerMenu;
    static MediaPlayer mediaPlayerGame;
    static MediaPlayer smbBar;

    // --> Sound SMB Bar
    public static void playSmbBarSound(Activity context) {
        try {
            smbBar = MediaPlayer.create(context, R.raw.smb_activate);
            smbBar.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --> Sound Menu
    public static void playMusicBackground(Activity context) {
        try {
            mediaPlayerMenu = MediaPlayer.create(context, R.raw.backgroundmusic);
            mediaPlayerMenu.start();
            mediaPlayerMenu.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusicBackground() {
        try {
            if (mediaPlayerMenu != null) {
                mediaPlayerMenu.stop();
                mediaPlayerMenu.release();
                mediaPlayerMenu = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --> Sound Game
    public static void playGameSound(Activity context) {
        try {
            mediaPlayerGame = MediaPlayer.create(context, R.raw.ticking);
            mediaPlayerGame.start();
            mediaPlayerGame.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopGameSound() {
        try {
            if (mediaPlayerGame != null) {
                mediaPlayerGame.stop();
                mediaPlayerGame.release();
                mediaPlayerGame = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPlayerId(Context context) {
        String id = getSharedPreferences(context).getString(PLAYER_ID, null);
        if (id == null) {
            String randomId = UUID.randomUUID().toString().substring(0, 5);
            getSharedPreferences(context).edit().putString(PLAYER_ID, randomId).apply();
        }
        return getSharedPreferences(context).getString(PLAYER_ID, null);
    }

    public static void setPlayerColour(Context context, PieceColour colour) {
        getSharedPreferences(context).edit().putString("PLAYER_COLOUR", colour.name()).apply();
    }

    public static PieceColour getPlayerColour(Context context) {
        String val = getSharedPreferences(context).getString("PLAYER_COLOUR", null);
        if (val == null)
            return null;
        return PieceColour.valueOf(val);
    }

    public static void addGameIfNotExists(Context context, Game game) throws JsonProcessingException {
        if (game.getGameId() == null)
            return;

        List<Game> games = getGameList(context);
        Optional<String> gameId = games.stream().map(Game::getGameId).filter(id -> id.equals(game.getGameId())).findFirst();
        if (gameId.isPresent()) {
            return;
        }

        games.add(0, game);
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
        if (opponentJsonStr == null)
            return null;
        return objectMapper.readValue(opponentJsonStr, PlayerDTO.class);
    }

    public static int getWinCount(Context context) {
        return getSharedPreferences(context).getInt("WIN_COUNT", 0);
    }

    public static void incrementWinCount(Context context) {
        int winCount = getWinCount(context);
        getSharedPreferences(context).edit().putInt("WIN_COUNT", ++winCount).apply();
    }

    public static int getLossCount(Context context) {
        return getSharedPreferences(context).getInt("LOSS_COUNT", 0);
    }

    public static void incrementLossCount(Context context) {
        int lossCount = getLossCount(context);
        getSharedPreferences(context).edit().putInt("LOSS_COUNT", ++lossCount).apply();
    }
}
