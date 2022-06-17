package at.aau.se2.chessify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.DefaultLifecycleObserver;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import at.aau.se2.chessify.AndroidGameUI.BoardView;
import at.aau.se2.chessify.Dice.DiceActivity;
import at.aau.se2.chessify.network.LifeCycleObserver;
import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.util.Helper;

public class LobbyActivity extends AppCompatActivity implements DefaultLifecycleObserver {

    Button joinGame;
    Button createGame;
    ImageView soundLobby;
    ImageView Back;
    TextView textViewBack;
    TextView viewGameID;
    EditText inputGameID;
    private Button btnStartGame;
    private TextView viewGameIdLabel;
    private ImageView iconCopy;
    ImageView wallpaper;

    private WebSocketClient webSocketClient;

    private boolean triedToJoinAGame = false;
    private final Handler enterIdHandler = new Handler(Looper.getMainLooper());

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;
    private GamesAdapter gamesAdapter;
    private TextView lossCountLabel;
    private TextView winCountLabel;

    private float offsetX = 0;
    private float offsetY = 0;
    private float startX;
    private float startY;
    float startXArrow;
    float startYArrow;

    private int arrowState = 0;

    private LifeCycleObserver lifeCycleObserver;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lobby);

        joinGame = findViewById(R.id.btn_JoinGame);
        createGame = findViewById(R.id.btn_CreateGame);
        Back = findViewById(R.id.ArrowBacklobby);
        textViewBack = findViewById(R.id.textView2);
        viewGameID = findViewById(R.id.textView_viewGameID);
        inputGameID = findViewById(R.id.PlaintextEnterGameID);
        soundLobby = findViewById(R.id.btn_sound_lobby);
        btnStartGame = findViewById(R.id.btn_startGame);
        viewGameIdLabel = findViewById(R.id.textView_GameIdLabel);
        iconCopy = findViewById(R.id.icon_copy);
        wallpaper = (ImageView) findViewById(R.id.imageView3);

        String playerName = Helper.getUniquePlayerName(this);
        webSocketClient = WebSocketClient.getInstance(playerName);

        initLifeCycleObserver();

        btnStartGame.setOnClickListener(view -> startDiceActivity());
        createGame.setOnClickListener(getCreateGameClickListener());
        viewGameID.setOnClickListener(getCopyToClipboardClickListener());
        iconCopy.setOnClickListener(getCopyToClipboardClickListener());
        inputGameID.addTextChangedListener(getIdTextChangedListener());
        joinGame.setOnClickListener(getJoinGameClickListener());

        Back.setOnClickListener(view -> getToMainActivity());

        textViewBack.setOnClickListener(view -> getToMainActivity());

        soundLobby.setOnClickListener(view -> {
            if (Helper.getBackgroundSound(this)) {
                soundLobby.setImageResource(R.drawable.volume_off_white);
                Helper.setBackgroundSound(this, false);
                Helper.stopMusicBackground(this);
            } else {
                Helper.playMusicBackground(this);
                soundLobby.setImageResource(R.drawable.volume_on_white);
                Helper.setBackgroundSound(this, true);
            }
        });

        showFinishGameDialog(Helper.getGameId(this));

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            initNavigationDrawer();
            initGamesList();
        }

    }

    private void initNavigationDrawer() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.games_list);
        ImageView arrow = findViewById(R.id.arrow_swipe);

        drawerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startX = navigationView.getX();
                startY = navigationView.getY();
                startXArrow = arrow.getX();
                startYArrow = arrow.getY();
                drawerLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        navigationView.getViewTreeObserver().addOnDrawListener(() -> {

            offsetX = navigationView.getX() - startX;
            offsetY = navigationView.getY() - startY;
            arrow.setX(startXArrow + offsetX);
            arrow.setY(startYArrow + offsetY);

            if (arrowState == 1 && arrow.getX() <= startXArrow + 50) {
                arrow.animate().rotation(0).setDuration(300).start();
                arrowState = 0;
            }

        });

        arrow.setOnClickListener((view -> drawerLayout.openDrawer(GravityCompat.START)));

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                // not implemented
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                arrow.animate().rotation(180).setDuration(300).start();
                arrowState = 1;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // not implemented
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // not implemented
            }
        });
    }

    private void initGamesList() {
        LayoutInflater li = LayoutInflater.from(this);
        View listView = li.inflate(R.layout.listview_games, null);
        ListView gamesList = listView.findViewById(R.id.games_list_view);
        View listHeader = li.inflate(R.layout.listview_header, null);
        ImageView delete = listHeader.findViewById(R.id.icon_delete);
        delete.setOnClickListener(view -> createDeleteGameListDialog().show());
        gamesList.addHeaderView(listHeader, null, false);
        lossCountLabel = listHeader.findViewById(R.id.games_list_statistic_losses);
        winCountLabel = listHeader.findViewById(R.id.games_list_statistic_winnings);

        List<Game> games;
        try {
            games = Helper.getGameList(this);
        } catch (JsonProcessingException jsonProcessingException) {
            TextView headerLabel = listHeader.findViewById(R.id.games_list_header);
            String errorLabel = "Error parsing data";
            headerLabel.setText(errorLabel);
            gamesList.addHeaderView(listHeader, null, false);
            gamesAdapter = new GamesAdapter(getApplicationContext(), new ArrayList<>());
            gamesList.setAdapter(gamesAdapter);
            navigationView.addView(listView);
            return;
        }

        gamesAdapter = new GamesAdapter(getApplicationContext(), games);
        gamesList.setAdapter(gamesAdapter);

        gamesList.setOnItemClickListener((adapterView, view, i, l) -> {
            Game game = (Game) adapterView.getAdapter().getItem(i);
            Context baseContext = getBaseContext();
            Helper.setGameId(baseContext, game.getGameId());
            Helper.setPlayerColour(baseContext, game.getPieceColour());
            enterGame(game.getGameId());
        });

        gamesList.setOnItemLongClickListener((adapterView, view, i, l) -> {
            createDeleteSingleGameDialog(i).show();
            return true;
        });

        navigationView.addView(listView);
    }

    @SuppressLint("CheckResult")
    private void initLifeCycleObserver() {
        lifeCycleObserver = new LifeCycleObserver(getBaseContext());
        lifeCycleObserver.onClientReconnect().subscribe(client ->
                // the websockets client has reconnected, restart this activity to guarantee correct functionality
                runOnUiThread(() -> {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                })
        );
        getLifecycle().addObserver(lifeCycleObserver);
    }

    private void clearGames(int gameStatusFilter) throws JsonProcessingException {
        List<Game> updatedList = Helper.clearGamesList(this, gameStatusFilter);
        updateGamesList(updatedList);
    }

    private void deleteSingleGame(int index) throws JsonProcessingException {
        List<Game> updatedList = Helper.deleteGame(this, index);
        updateGamesList(updatedList);
    }

    private View.OnClickListener getJoinGameClickListener() {
        return view -> startDiceActivity();
    }

    @SuppressLint("CheckResult")
    private View.OnClickListener getCreateGameClickListener() {
        return view -> {
            if (!webSocketClient.isConnected()) {
                showNetworkError();
                return;
            }
            webSocketClient.requestNewGame()
                    .subscribe(gameId -> {
                        String id = gameId.getPayload();
                        runOnUiThread(() -> {
                            enableStartGame();
                            saveGameId(id);
                            viewGameID.setText(id);
                        });
                    }, throwable -> showNetworkError());
        };
    }

    private TextWatcher getIdTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // method ignored
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enterIdHandler.removeCallbacksAndMessages(null);
                if (triedToJoinAGame && (charSequence.length() == 0 || charSequence.length() == 4 || charSequence.length() == 6)) {
                    disableJoinGame();
                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 5) {
                    enterIdHandler.postDelayed(() -> {
                        if (!webSocketClient.isConnected()) {
                            showNetworkError();
                            return;
                        }
                        String input = editable.toString();
                        webSocketClient.joinGame(input).subscribe(response -> {
                            triedToJoinAGame = true;
                            switch (response.getPayload()) {
                                case "1":
                                    saveGameId(input);
                                    enableJoinGame();
                                    break;
                                case "0":
                                    showToast("Game is already full");
                                    disableJoinGame();
                                    break;
                                case "-1":
                                    showToast("Game does not exist");
                                    disableJoinGame();
                                    break;
                                default:
                                    showToast("Server error");
                                    break;
                            }
                        }, throwable -> showNetworkError());
                    }, 50);

                }
            }
        };
    }

    public void getToMainActivity() {
        onBackPressed();
    }

    public void startDiceActivity() {
        getLifecycle().removeObserver(lifeCycleObserver);
        Intent intentDiceActivity = new Intent(this, DiceActivity.class);
        startActivity(intentDiceActivity);

    }

    private void showToast(String text) {
        runOnUiThread(() ->
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show());
    }

    private void enableJoinGame() {
        runOnUiThread(() -> {
            joinGame.setEnabled(true);
            joinGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button_lobby_join_success));
            joinGame.setTextColor(Color.BLACK);
        });
        triedToJoinAGame = true;
    }

    private void disableJoinGame() {
        runOnUiThread(() -> {
            joinGame.setEnabled(false);
            joinGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button_lobby_join_error));
            joinGame.setTextColor(Color.WHITE);
        });
    }

    private View.OnClickListener getCopyToClipboardClickListener() {
        return view -> {
            ClipboardManager clipboard = ContextCompat.getSystemService(getBaseContext(), ClipboardManager.class);
            if (clipboard != null) {
                clipboard.setPrimaryClip(ClipData.newPlainText("", Helper.getGameId(getBaseContext())));
                showToast("Copied Game ID to Clipboard");
            }
        };
    }

    private void saveGameId(String gameId) {
        Helper.setGameId(getBaseContext(), gameId);
    }

    private void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    private void enableStartGame() {
        setInvisible(createGame);
        setVisible(viewGameID);
        setVisible(viewGameIdLabel);
        setVisible(btnStartGame);
        setVisible(iconCopy);
    }

    private void showNetworkError() {
        showToast("Network error");
    }

    @Override
    public void onResume() {
        super.onResume();

        getLifecycle().addObserver(lifeCycleObserver);

        // --> update Soundsymbol
        if (Helper.getBackgroundSound(this)) {
            soundLobby.setImageResource(R.drawable.volume_on_white);
        } else {
            soundLobby.setImageResource(R.drawable.volume_off_white);
        }

        // --> Update Color Scheme
        if (Helper.getDarkmode(this)) {
            wallpaper.setImageResource(R.drawable.wallpaper1_material_min);
            createGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button1));
            btnStartGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button1));
            joinGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button1));
        } else {
            wallpaper.setImageResource(R.drawable.wallpaper1_material_min_dark);
            createGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button2));
            btnStartGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button2));
            joinGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button2));
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            try {
                List<Game> updatedList = Helper.getGameList(this);
                updateGamesList(updatedList);
            } catch (JsonProcessingException jsonProcessingException) {
                // unhandled
            }
        }

    }

    private void updateGamesList(List<Game> updatedList) {
        if (gamesAdapter == null)
            return;
        gamesAdapter.updateAdapter(updatedList);
        String winCount = "Won: " + Helper.getWinCount(this);
        String lossCount = "Lost: " + Helper.getLossCount(this);
        lossCountLabel.setText(lossCount);
        winCountLabel.setText(winCount);
    }

    private Dialog createDeleteGameListDialog() {
        final String[] gameStatusArray = new String[2];
        gameStatusArray[Game.STATUS_RUNNING] = "Running";
        gameStatusArray[Game.STATUS_FINISHED] = "Finished";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final boolean[] selected = new boolean[2];

        builder.setMultiChoiceItems(gameStatusArray, selected, (dialogInterface, i, b) -> selected[i] = b);
        builder.setTitle("Which games do you want to delete?");
        builder.setPositiveButton("Delete", (dialog, id) -> {
            for (int i = 0; i < selected.length; i++) {
                try {
                    if (selected[Game.STATUS_RUNNING] && selected[Game.STATUS_FINISHED]) {
                        clearGames(Game.DEFAULT); // delete all games at once
                        return;
                    }
                    // if only one status selected delete the games with this status
                    if (selected[Game.STATUS_RUNNING]) {
                        clearGames(Game.STATUS_RUNNING);
                    } else if (selected[Game.STATUS_FINISHED]) {
                        clearGames(Game.STATUS_FINISHED);
                    }
                    return;
                } catch (JsonProcessingException jsonProcessingException) {
                    showToast("An error occurred while trying to delete a game");
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
        });
        return builder.create();
    }

    private Dialog createDeleteSingleGameDialog(int indexToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you really want to delete this game?");
        builder.setPositiveButton("Ok", (dialog, id) -> {
            try {
                deleteSingleGame(indexToDelete);
            } catch (JsonProcessingException jsonProcessingException) {
                showToast("An error occurred while trying to delete this game");
            }
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
        });
        return builder.create();
    }

    private void showFinishGameDialog(String gameId) {
        if (gameId == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to resume your last game?");
        builder.setPositiveButton("Ok", (dialog, id) -> enterGame(gameId));
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
        });
        builder.create().show();
    }

    private void enterGame(String gameId) {
        getLifecycle().removeObserver(lifeCycleObserver);
        webSocketClient.joinGame(gameId).subscribe();
        Intent intent = new Intent(this, BoardView.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}