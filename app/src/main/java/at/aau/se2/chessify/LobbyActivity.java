package at.aau.se2.chessify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.util.Helper;

public class LobbyActivity extends AppCompatActivity {

    Button JoinGame;
    Button CreateGame;
    ImageView SoundLobby;
    ImageView Back;
    TextView TextViewBack;
    TextView viewGameID;
    EditText inputGameID;
    private Button btnStartGame;
    private TextView viewGameIdLabel;
    private ImageView iconCopy;
    ImageView Wallpaper;

    private WebSocketClient webSocketClient;

    private boolean triedToJoinAGame = false;
    private final Handler enterIdHandler = new Handler(Looper.getMainLooper());

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;
    private ListView gamesList;
    private GamesAdapter gamesAdapter;
    private TextView tVlossCount;
    private TextView tVwinCount;

    float offsetX = 0, offsetY = 0;
    float startX, startY;
    float startXArrow, startYArrow;

    int arrowState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lobby);

        JoinGame = findViewById(R.id.btn_JoinGame);
        CreateGame = findViewById(R.id.btn_CreateGame);
        Back = findViewById(R.id.ArrowBacklobby);
        TextViewBack = findViewById(R.id.textView2);
        viewGameID = findViewById(R.id.textView_viewGameID);
        inputGameID = findViewById(R.id.PlaintextEnterGameID);
        SoundLobby = findViewById(R.id.btn_sound_lobby);
        btnStartGame = findViewById(R.id.btn_startGame);
        viewGameIdLabel = findViewById(R.id.textView_GameIdLabel);
        iconCopy = findViewById(R.id.icon_copy);
        Wallpaper = (ImageView) findViewById(R.id.imageView3);

        webSocketClient = WebSocketClient.getInstance(Helper.getUniquePlayerName(this));

        btnStartGame.setOnClickListener(view -> startDiceActivity());
        CreateGame.setOnClickListener(getCreateGameClickListener());
        viewGameID.setOnClickListener(getCopyToClipboardClickListener());
        iconCopy.setOnClickListener(getCopyToClipboardClickListener());
        inputGameID.addTextChangedListener(getIdTextChangedListener());
        JoinGame.setOnClickListener(getJoinGameClickListener());

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToMainActivity();
            }
        });

        TextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToMainActivity();
            }
        });

        SoundLobby.setOnClickListener(view ->

        {
            if (Helper.getBackgroundSound(this)) {
                SoundLobby.setImageResource(R.drawable.volume_off_white);
                Helper.setBackgroundSound(this, false);
                Helper.stopMusicBackground(this);
            } else {
                Helper.playMusicBackground(this);
                SoundLobby.setImageResource(R.drawable.volume_on_white);
                Helper.setBackgroundSound(this, true);
            }
        });


        String gameId = Helper.getGameId(this);
        if (gameId != null) {
            Dialog dialog = createFinishGameDialog(gameId);
            dialog.show();
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return; // the following feature is only available in portrait mode
        }

        LayoutInflater li = LayoutInflater.from(this);
        View listView = li.inflate(R.layout.listview_games, null);

        gamesList = listView.findViewById(R.id.games_list_view);
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

        navigationView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                offsetX = navigationView.getX() - startX;
                offsetY = navigationView.getY() - startY;
                arrow.setX(startXArrow + offsetX);
                arrow.setY(startYArrow + offsetY);

                if (arrowState == 1 && arrow.getX() <= startXArrow + 50) {
                    arrow.animate().rotation(0).setDuration(300).start();
                    arrowState = 0;
                }

            }
        });

        arrow.setOnClickListener((view -> drawerLayout.openDrawer(Gravity.LEFT)));

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

        View listHeader = li.inflate(R.layout.listview_header, null);
        ImageView delete = listHeader.findViewById(R.id.icon_delete);
        delete.setOnClickListener((view) -> createDeleteGameListDialog().show());
        gamesList.addHeaderView(listHeader, null, false);
        tVlossCount = listHeader.findViewById(R.id.games_list_statistic_losses);
        tVwinCount = listHeader.findViewById(R.id.games_list_statistic_winnings);

        List<Game> games;
        try {
            games = Helper.getGameList(this);
        } catch (JsonProcessingException jsonProcessingException) {
            TextView headerLabel = listHeader.findViewById(R.id.games_list_header);
            headerLabel.setText("Error parsing data");
            gamesList.addHeaderView(listHeader, null, false);
            gamesAdapter = new GamesAdapter(getApplicationContext(), new ArrayList<>());
            gamesList.setAdapter(gamesAdapter);
            navigationView.addView(listView);
            return;
        }

        gamesAdapter = new GamesAdapter(getApplicationContext(), games);
        gamesList.setAdapter(gamesAdapter);
        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Game game = (Game) adapterView.getAdapter().getItem(i);
                Helper.setGameId(getApplicationContext(), game.getGameId());
                enterGame(game.getGameId());
            }
        });
        gamesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                createDeleteSingleGameDialog(i).show();
                return true;
            }
        });
        navigationView.addView(listView);
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
        //Intent intentgetBack = new Intent(this, MainActivity.class);
        //startActivity(intentgetBack);
        onBackPressed();
    }

    public void startDiceActivity() {
        Intent intentDiceActivity = new Intent(this, DiceActivity.class);
        startActivity(intentDiceActivity);

    }

    private void showToast(String text) {
        runOnUiThread(() ->
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show());
    }

    private void enableJoinGame() {
        runOnUiThread(() -> {
            JoinGame.setEnabled(true);
            JoinGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button_lobby_join_success));
            JoinGame.setTextColor(Color.BLACK);
        });
        triedToJoinAGame = true;
    }

    private void disableJoinGame() {
        runOnUiThread(() -> {
            JoinGame.setEnabled(false);
            JoinGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button_lobby_join_error));
            JoinGame.setTextColor(Color.WHITE);
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
        setInvisible(CreateGame);
        setVisible(viewGameID);
        setVisible(viewGameIdLabel);
        setVisible(btnStartGame);
        setVisible(iconCopy);
    }

    private void showNetworkError() {
        showToast("Network error");
//        WebSocketClient.reconnectWithPlayerName(Helper.getUniquePlayerName(getBaseContext()));
//        webSocketClient = WebSocketClient.getInstance(Helper.getUniquePlayerName(getBaseContext()));
    }

    @Override
    public void onResume() {
        super.onResume();

        // --> update Soundsymbol
        if (Helper.getBackgroundSound(this)) {
            SoundLobby.setImageResource(R.drawable.volume_on_white);
        } else {
            SoundLobby.setImageResource(R.drawable.volume_off_white);
        }

        // --> Update Color Scheme
        if (Helper.getDarkmode(this)) {
            Wallpaper.setImageResource(R.drawable.wallpaper1_material_min);
            CreateGame.setBackground(getDrawable(R.drawable.custom_button1));
            btnStartGame.setBackground(getDrawable(R.drawable.custom_button1));
            JoinGame.setBackground(getDrawable(R.drawable.custom_button1));
        } else {
            Wallpaper.setImageResource(R.drawable.wallpaper1_material_min_dark);
            CreateGame.setBackground(getDrawable(R.drawable.custom_button2));
            btnStartGame.setBackground(getDrawable(R.drawable.custom_button2));
            JoinGame.setBackground(getDrawable(R.drawable.custom_button2));
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

    private void updateGamesList(List<Game> updatedList) throws JsonProcessingException {
        gamesAdapter.updateAdapter(updatedList);
        int winCount = 0;
        int lossCount = 0;
        for (Game g : updatedList) {
            if (g.getStatus() != Game.STATUS_RUNNING)
                if (g.isWinner()) {
                    winCount++;
                } else {
                    lossCount++;
                }
        }
        String lossCountStr = "Lost: " + lossCount;
        String winCountStr = "Won: " + winCount;
        tVlossCount.setText(lossCountStr);
        tVwinCount.setText(winCountStr);
    }

    private Dialog createDeleteGameListDialog() {
        final String[] gameStatusArray = new String[2];
        gameStatusArray[Game.STATUS_RUNNING] = "Running";
        gameStatusArray[Game.STATUS_FINISHED] = "Finished";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final boolean[] selected = new boolean[2];

        builder.setMultiChoiceItems(gameStatusArray, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                selected[i] = b;
            }
        });
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

    private Dialog createFinishGameDialog(String gameId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to resume your last game?");
        builder.setPositiveButton("Ok", (dialog, id) -> {
            enterGame(gameId);
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
        });
        return builder.create();
    }

    private void enterGame(String gameId) {
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