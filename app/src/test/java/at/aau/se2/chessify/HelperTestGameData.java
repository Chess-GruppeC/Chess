package at.aau.se2.chessify;

import android.content.Context;
import android.content.SharedPreferences;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import at.aau.se2.chessify.chessLogic.pieces.PieceColour;
import at.aau.se2.chessify.network.dto.PlayerDTO;
import at.aau.se2.chessify.util.Helper;

public class HelperTestGameData {

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor mockEditor;

    private MockedStatic<Helper> helperMockedStatic;

    private Context context;

    @Before
    public void init() {
        context = mock(Context.class);
        sharedPreferences = mock(SharedPreferences.class);
        mockEditor = mock(SharedPreferences.Editor.class);
        helperMockedStatic = mockStatic(Helper.class, Mockito.CALLS_REAL_METHODS);
        helperMockedStatic.when(() -> Helper.getSharedPreferences(context)).thenReturn(sharedPreferences);
        helperMockedStatic.when(() -> Helper.getSharedPreferences(context).edit()).thenReturn(mockEditor);
        helperMockedStatic.when(() -> sharedPreferences.edit()).thenReturn(mockEditor);
    }

    @After
    public void cleanup() {
        helperMockedStatic.close();
    }

    @Test
    public void getSharedPreferencesTest() {
        assertEquals(sharedPreferences, Helper.getSharedPreferences(context));
    }

    @Test
    public void getWinCountTest() {
        when(sharedPreferences.getInt("WIN_COUNT", 0)).thenReturn(10);
        assertEquals(10, Helper.getWinCount(context));
    }


    @Test
    public void getOpponentNullTest() throws JsonProcessingException {
        helperMockedStatic.when(() -> sharedPreferences.getString("OPPONENT", null)).thenReturn(null);
        assertNull(Helper.getOpponent(context));
    }

    @Test
    public void getOpponentTest() throws JsonProcessingException {
        PlayerDTO opponent = new PlayerDTO("Player");
        String json = new ObjectMapper().writeValueAsString(opponent);
        helperMockedStatic.when(() -> sharedPreferences.getString("OPPONENT", null)).thenReturn(json);
        assertEquals(opponent.getName(), Objects.requireNonNull(Helper.getOpponent(context)).getName());
    }

    @Test
    public void getGamesListNullTest() throws JsonProcessingException {
        when(sharedPreferences.getString("GAMES_LIST", null)).thenReturn(null);
        assertEquals(0, Helper.getGameList(context).size());
    }

    @Test
    public void getGamesListTest() throws JsonProcessingException {
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game());
        games.add(new Game());
        String gamesJson = new ObjectMapper().writeValueAsString(games);
        when(sharedPreferences.getString("GAMES_LIST", null)).thenReturn(gamesJson);
        assertEquals(2, Helper.getGameList(context).size());
    }

    @Test
    public void clearGamesListDefaultTest() throws JsonProcessingException {
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game());
        String gamesJson = new ObjectMapper().writeValueAsString(games);
        when(sharedPreferences.getString("GAMES_LIST", null)).thenReturn(gamesJson);
        assertThrows(NullPointerException.class, () -> Helper.clearGamesList(context, Game.DEFAULT));
    }

    @Test
    public void clearGamesListSpecificTest() throws JsonProcessingException {
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game(new PlayerDTO("Player"),"ID", Game.STATUS_FINISHED, PieceColour.BLACK));
        String gamesJson = new ObjectMapper().writeValueAsString(games);
        when(sharedPreferences.getString("GAMES_LIST", null)).thenReturn(gamesJson);
        assertThrows(NullPointerException.class, () -> Helper.clearGamesList(context, Game.STATUS_FINISHED));
    }

    @Test
    public void deleteGamesTest() {
        assertThrows(Exception.class, () -> Helper.deleteGame(context, 0));
    }

    @Test
    public void addGameIfNotExistsNullTest() throws JsonProcessingException {
        Game g = new Game();
        g.setGameId(null);
        Helper.addGameIfNotExists(context, g);
        verify(sharedPreferences, times(0)).edit();
    }

    @Test
    public void addGameIfNotExistsTest() {
        Game g = new Game();
        g.setGameId("ID");
        assertThrows(Exception.class, () -> Helper.addGameIfNotExists(context, g));
    }

}
