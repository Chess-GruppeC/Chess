package at.aau.se2.chessify.AndroidGameUI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import at.aau.se2.chessify.Game;
import at.aau.se2.chessify.R;
import at.aau.se2.chessify.chessLogic.board.ChessBoard;
import at.aau.se2.chessify.chessLogic.board.Location;
import at.aau.se2.chessify.chessLogic.board.Move;
import at.aau.se2.chessify.chessLogic.pieces.Bishop;
import at.aau.se2.chessify.chessLogic.pieces.ChessPiece;
import at.aau.se2.chessify.chessLogic.pieces.King;
import at.aau.se2.chessify.chessLogic.pieces.Knight;
import at.aau.se2.chessify.chessLogic.pieces.Pawn;
import at.aau.se2.chessify.chessLogic.pieces.PieceColour;
import at.aau.se2.chessify.chessLogic.pieces.Queen;
import at.aau.se2.chessify.chessLogic.pieces.Rook;
import at.aau.se2.chessify.network.LifeCycleObserver;
import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.network.dto.GameDataDTO;
import at.aau.se2.chessify.network.dto.PlayerDTO;
import at.aau.se2.chessify.util.Helper;
import io.reactivex.disposables.Disposable;

public class BoardView extends AppCompatActivity implements View.OnClickListener {

    private int currentProgress = 0;
    private int buffer;
    ProgressBar specialMoveBar;
    TextView smbCount;
    ImageView soundButton;
    Button executeSMB;
    private TextView currentPlayerInfo;

    private final TextView[][] boardView = new TextView[8][8];
    private final TextView[][] boardViewBackground = new TextView[8][8];

    private Location onClickedPosition = new Location(0, 0);
    private ChessPiece selectedPiece;
    private boolean isPieceSelected = false;
    ChessBoard chessBoard = new ChessBoard();
    ArrayList<Location> legalMoveList = new ArrayList<>();

    private boolean specialMoveActivated = false;
    private WebSocketClient client;

    private Disposable gameUpdateDisposable, getGameStateDisposable;
    private ObjectMapper objectMapper;

    private String gameId;

    private int destroyedPieceValue = 0;

    private String playerName;

    private PlayerDTO nextPlayer;

    private MediaPlayer pieceCaptured;
    private MediaPlayer pieceMoved;

    private PieceColour colour;

    private boolean isAnyPieceSelected;

    private Context baseContext;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_board);

        TextView textViewGameId = findViewById(R.id.tV_game_id);
        currentPlayerInfo = findViewById(R.id.current_player_info);

        baseContext = getBaseContext();
        colour = Helper.getPlayerColour(this);

        playerName = Helper.getUniquePlayerName(this);

        initializeBoard();

        objectMapper = new ObjectMapper();

        client = WebSocketClient.getInstance(Helper.getUniquePlayerName(this));

        LifeCycleObserver lifeCycleObserver = new LifeCycleObserver(this);
        lifeCycleObserver.onClientReconnect().subscribe(c -> c.joinGame(gameId).subscribe(message -> {
            if (message.getPayload().equals("1")) {
                runOnUiThread(() -> {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                });
            }
        }));
        getLifecycle().addObserver(lifeCycleObserver);

        gameId = Helper.getGameId(this);

        try {
            PlayerDTO opponent = Helper.getOpponent(baseContext);
            Helper.addGameIfNotExists(this, new Game(opponent, gameId, Game.STATUS_RUNNING, colour));
        } catch (JsonProcessingException jsonProcessingException) {
            // unhandled
        }

        if (gameId != null) {
            textViewGameId.setText("#".concat(gameId));
        }

        getGameStateDisposable = client.fetchGameState(gameId)
                .subscribe(lastState -> parseChessBoardAndRefresh(lastState.getPayload()),
                        throwable -> runOnUiThread(() -> Toast.makeText(baseContext, "An error occurred while fetching the last state", Toast.LENGTH_SHORT).show()));

        gameUpdateDisposable = client.receiveGameUpdates(gameId)
                .subscribe(update -> {
                    parseChessBoardAndRefresh(update.getPayload());
                    refreshSpecialMoveBar();
                    if (!getGameStateDisposable.isDisposed())
                        getGameStateDisposable.dispose();
                }, throwable -> runOnUiThread(() -> Toast.makeText(baseContext, "An error occurred", Toast.LENGTH_SHORT).show()));

        smbCount = findViewById(R.id.textViewSMBCount);
        soundButton = findViewById(R.id.btn_sound_BoardView);
        executeSMB = findViewById(R.id.btn_execute_SMB);
        executeSMB.setVisibility(View.INVISIBLE);
        Helper.setBackgroundSound(this, false);
        Helper.stopMusicBackground(this);

        SpecialMoveBar();
        smbCount.setText(currentProgress + " | " + specialMoveBar.getMax());

        soundButton.setOnClickListener(view -> {
            if (Helper.getGameSound(this)) {
                soundButton.setImageResource(R.drawable.volume_off_white);
                Helper.setGameSound(this, false);
                Helper.stopGameSound(this);

            } else {
                Helper.playGameSound(this);
                soundButton.setImageResource(R.drawable.volume_on_white);
                Helper.setGameSound(this, true);
                soundButton.setSoundEffectsEnabled(true);

            }
        });

        executeSMB.setOnClickListener(v -> {

            // --> Executes SMB Bar - sets progress to remaining buffer
            specialMoveActivated = true;
            currentProgress = 0;
            specialMoveBar.setProgress(currentProgress);
            smbCount.setText(buffer + " | " + specialMoveBar.getMax());
            currentProgress = buffer;
            specialMoveBar.setProgress(currentProgress);
            executeSMB.setText("ACTIVE");
            executeSMB.setBackgroundResource(R.drawable.custom_button_lobby_join_success);
            buffer = 0;
        });

    }


    private void initializeBoard() {

        boardView[0][0] = (TextView) findViewById(R.id.R00);
        boardViewBackground[0][0] = (TextView) findViewById(R.id.R000);
        boardView[1][0] = (TextView) findViewById(R.id.R10);
        boardViewBackground[1][0] = (TextView) findViewById(R.id.R010);
        boardView[2][0] = (TextView) findViewById(R.id.R20);
        boardViewBackground[2][0] = (TextView) findViewById(R.id.R020);
        boardView[3][0] = (TextView) findViewById(R.id.R30);
        boardViewBackground[3][0] = (TextView) findViewById(R.id.R030);
        boardView[4][0] = (TextView) findViewById(R.id.R40);
        boardViewBackground[4][0] = (TextView) findViewById(R.id.R040);
        boardView[5][0] = (TextView) findViewById(R.id.R50);
        boardViewBackground[5][0] = (TextView) findViewById(R.id.R050);
        boardView[6][0] = (TextView) findViewById(R.id.R60);
        boardViewBackground[6][0] = (TextView) findViewById(R.id.R060);
        boardView[7][0] = (TextView) findViewById(R.id.R70);
        boardViewBackground[7][0] = (TextView) findViewById(R.id.R070);

        boardView[0][1] = (TextView) findViewById(R.id.R01);
        boardViewBackground[0][1] = (TextView) findViewById(R.id.R001);
        boardView[1][1] = (TextView) findViewById(R.id.R11);
        boardViewBackground[1][1] = (TextView) findViewById(R.id.R011);
        boardView[2][1] = (TextView) findViewById(R.id.R21);
        boardViewBackground[2][1] = (TextView) findViewById(R.id.R021);
        boardView[3][1] = (TextView) findViewById(R.id.R31);
        boardViewBackground[3][1] = (TextView) findViewById(R.id.R031);
        boardView[4][1] = (TextView) findViewById(R.id.R41);
        boardViewBackground[4][1] = (TextView) findViewById(R.id.R041);
        boardView[5][1] = (TextView) findViewById(R.id.R51);
        boardViewBackground[5][1] = (TextView) findViewById(R.id.R051);
        boardView[6][1] = (TextView) findViewById(R.id.R61);
        boardViewBackground[6][1] = (TextView) findViewById(R.id.R061);
        boardView[7][1] = (TextView) findViewById(R.id.R71);
        boardViewBackground[7][1] = (TextView) findViewById(R.id.R071);

        boardView[0][2] = (TextView) findViewById(R.id.R02);
        boardViewBackground[0][2] = (TextView) findViewById(R.id.R002);
        boardView[1][2] = (TextView) findViewById(R.id.R12);
        boardViewBackground[1][2] = (TextView) findViewById(R.id.R012);
        boardView[2][2] = (TextView) findViewById(R.id.R22);
        boardViewBackground[2][2] = (TextView) findViewById(R.id.R022);
        boardView[3][2] = (TextView) findViewById(R.id.R32);
        boardViewBackground[3][2] = (TextView) findViewById(R.id.R032);
        boardView[4][2] = (TextView) findViewById(R.id.R42);
        boardViewBackground[4][2] = (TextView) findViewById(R.id.R042);
        boardView[5][2] = (TextView) findViewById(R.id.R52);
        boardViewBackground[5][2] = (TextView) findViewById(R.id.R052);
        boardView[6][2] = (TextView) findViewById(R.id.R62);
        boardViewBackground[6][2] = (TextView) findViewById(R.id.R062);
        boardView[7][2] = (TextView) findViewById(R.id.R72);
        boardViewBackground[7][2] = (TextView) findViewById(R.id.R072);

        boardView[0][3] = (TextView) findViewById(R.id.R03);
        boardViewBackground[0][3] = (TextView) findViewById(R.id.R003);
        boardView[1][3] = (TextView) findViewById(R.id.R13);
        boardViewBackground[1][3] = (TextView) findViewById(R.id.R013);
        boardView[2][3] = (TextView) findViewById(R.id.R23);
        boardViewBackground[2][3] = (TextView) findViewById(R.id.R023);
        boardView[3][3] = (TextView) findViewById(R.id.R33);
        boardViewBackground[3][3] = (TextView) findViewById(R.id.R033);
        boardView[4][3] = (TextView) findViewById(R.id.R43);
        boardViewBackground[4][3] = (TextView) findViewById(R.id.R043);
        boardView[5][3] = (TextView) findViewById(R.id.R53);
        boardViewBackground[5][3] = (TextView) findViewById(R.id.R053);
        boardView[6][3] = (TextView) findViewById(R.id.R63);
        boardViewBackground[6][3] = (TextView) findViewById(R.id.R063);
        boardView[7][3] = (TextView) findViewById(R.id.R73);
        boardViewBackground[7][3] = (TextView) findViewById(R.id.R073);

        boardView[0][4] = (TextView) findViewById(R.id.R04);
        boardViewBackground[0][4] = (TextView) findViewById(R.id.R004);
        boardView[1][4] = (TextView) findViewById(R.id.R14);
        boardViewBackground[1][4] = (TextView) findViewById(R.id.R014);
        boardView[2][4] = (TextView) findViewById(R.id.R24);
        boardViewBackground[2][4] = (TextView) findViewById(R.id.R024);
        boardView[3][4] = (TextView) findViewById(R.id.R34);
        boardViewBackground[3][4] = (TextView) findViewById(R.id.R034);
        boardView[4][4] = (TextView) findViewById(R.id.R44);
        boardViewBackground[4][4] = (TextView) findViewById(R.id.R044);
        boardView[5][4] = (TextView) findViewById(R.id.R54);
        boardViewBackground[5][4] = (TextView) findViewById(R.id.R054);
        boardView[6][4] = (TextView) findViewById(R.id.R64);
        boardViewBackground[6][4] = (TextView) findViewById(R.id.R064);
        boardView[7][4] = (TextView) findViewById(R.id.R74);
        boardViewBackground[7][4] = (TextView) findViewById(R.id.R074);

        boardView[0][5] = (TextView) findViewById(R.id.R05);
        boardViewBackground[0][5] = (TextView) findViewById(R.id.R005);
        boardView[1][5] = (TextView) findViewById(R.id.R15);
        boardViewBackground[1][5] = (TextView) findViewById(R.id.R015);
        boardView[2][5] = (TextView) findViewById(R.id.R25);
        boardViewBackground[2][5] = (TextView) findViewById(R.id.R025);
        boardView[3][5] = (TextView) findViewById(R.id.R35);
        boardViewBackground[3][5] = (TextView) findViewById(R.id.R035);
        boardView[4][5] = (TextView) findViewById(R.id.R45);
        boardViewBackground[4][5] = (TextView) findViewById(R.id.R045);
        boardView[5][5] = (TextView) findViewById(R.id.R55);
        boardViewBackground[5][5] = (TextView) findViewById(R.id.R055);
        boardView[6][5] = (TextView) findViewById(R.id.R65);
        boardViewBackground[6][5] = (TextView) findViewById(R.id.R065);
        boardView[7][5] = (TextView) findViewById(R.id.R75);
        boardViewBackground[7][5] = (TextView) findViewById(R.id.R075);

        boardView[0][6] = (TextView) findViewById(R.id.R06);
        boardViewBackground[0][6] = (TextView) findViewById(R.id.R006);
        boardView[1][6] = (TextView) findViewById(R.id.R16);
        boardViewBackground[1][6] = (TextView) findViewById(R.id.R016);
        boardView[2][6] = (TextView) findViewById(R.id.R26);
        boardViewBackground[2][6] = (TextView) findViewById(R.id.R026);
        boardView[3][6] = (TextView) findViewById(R.id.R36);
        boardViewBackground[3][6] = (TextView) findViewById(R.id.R036);
        boardView[4][6] = (TextView) findViewById(R.id.R46);
        boardViewBackground[4][6] = (TextView) findViewById(R.id.R046);
        boardView[5][6] = (TextView) findViewById(R.id.R56);
        boardViewBackground[5][6] = (TextView) findViewById(R.id.R056);
        boardView[6][6] = (TextView) findViewById(R.id.R66);
        boardViewBackground[6][6] = (TextView) findViewById(R.id.R066);
        boardView[7][6] = (TextView) findViewById(R.id.R76);
        boardViewBackground[7][6] = (TextView) findViewById(R.id.R076);

        boardView[0][7] = (TextView) findViewById(R.id.R07);
        boardViewBackground[0][7] = (TextView) findViewById(R.id.R007);
        boardView[1][7] = (TextView) findViewById(R.id.R17);
        boardViewBackground[1][7] = (TextView) findViewById(R.id.R017);
        boardView[2][7] = (TextView) findViewById(R.id.R27);
        boardViewBackground[2][7] = (TextView) findViewById(R.id.R027);
        boardView[3][7] = (TextView) findViewById(R.id.R37);
        boardViewBackground[3][7] = (TextView) findViewById(R.id.R037);
        boardView[4][7] = (TextView) findViewById(R.id.R47);
        boardViewBackground[4][7] = (TextView) findViewById(R.id.R047);
        boardView[5][7] = (TextView) findViewById(R.id.R57);
        boardViewBackground[5][7] = (TextView) findViewById(R.id.R057);
        boardView[6][7] = (TextView) findViewById(R.id.R67);
        boardViewBackground[6][7] = (TextView) findViewById(R.id.R067);
        boardView[7][7] = (TextView) findViewById(R.id.R77);
        boardViewBackground[7][7] = (TextView) findViewById(R.id.R077);

        initializePieces();
    }

    private void initializePieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece chessPiece = chessBoard.getPieceAtLocation(new Location(i, j));

                if (chessPiece != null) {
                    if (chessPiece.getClass() == Pawn.class && chessPiece.getColour() == PieceColour.BLACK) {
                        boardView[i][j].setBackgroundResource(R.drawable.black_pawn);
                    }
                    if (chessPiece.getClass() == Pawn.class && chessPiece.getColour() == PieceColour.WHITE) {
                        boardView[i][j].setBackgroundResource(R.drawable.white_pawn);
                    }
                    if (chessPiece.getClass() == Rook.class && chessPiece.getColour() == PieceColour.BLACK) {
                        boardView[i][j].setBackgroundResource(R.drawable.black_rook);
                    }
                    if (chessPiece.getClass() == Rook.class && chessPiece.getColour() == PieceColour.WHITE) {
                        boardView[i][j].setBackgroundResource(R.drawable.white_rook);
                    }
                    if (chessPiece.getClass() == Bishop.class && chessPiece.getColour() == PieceColour.BLACK) {
                        boardView[i][j].setBackgroundResource(R.drawable.black_bishop);
                    }
                    if (chessPiece.getClass() == Bishop.class && chessPiece.getColour() == PieceColour.WHITE) {
                        boardView[i][j].setBackgroundResource(R.drawable.white_bishop);
                    }
                    if (chessPiece.getClass() == Knight.class && chessPiece.getColour() == PieceColour.BLACK) {
                        boardView[i][j].setBackgroundResource(R.drawable.black_knight);
                    }
                    if (chessPiece.getClass() == Knight.class && chessPiece.getColour() == PieceColour.WHITE) {
                        boardView[i][j].setBackgroundResource(R.drawable.white_knight);
                    }
                    if (chessPiece.getClass() == King.class && chessPiece.getColour() == PieceColour.BLACK) {
                        boardView[i][j].setBackgroundResource(R.drawable.black_king);
                    }
                    if (chessPiece.getClass() == King.class && chessPiece.getColour() == PieceColour.WHITE) {
                        boardView[i][j].setBackgroundResource(R.drawable.white_king);
                    }
                    if (chessPiece.getClass() == Queen.class && chessPiece.getColour() == PieceColour.BLACK) {
                        boardView[i][j].setBackgroundResource(R.drawable.black_queen);
                    }
                    if (chessPiece.getClass() == Queen.class && chessPiece.getColour() == PieceColour.WHITE) {
                        boardView[i][j].setBackgroundResource(R.drawable.white_queen);
                    }
                } else {
                    boardView[i][j].setBackgroundResource(android.R.color.transparent);
                }
            }
        }

    }

    @Override
    public void onClick(View view) {
        pieceCaptured = MediaPlayer.create(this, R.raw.piece_captured);
        pieceMoved = MediaPlayer.create(this, R.raw.piece_move_two);
        //add clicked position
        // if selected ....
        switch (view.getId()) {
            case R.id.R00:
                onClickedPosition = new Location(0, 0);
                break;
            case R.id.R10:
                onClickedPosition.setRow(1);
                onClickedPosition.setColumn(0);
                break;
            case R.id.R20:
                onClickedPosition.setRow(2);
                onClickedPosition.setColumn(0);
                break;
            case R.id.R30:
                onClickedPosition.setRow(3);
                onClickedPosition.setColumn(0);
                break;
            case R.id.R40:
                onClickedPosition.setRow(4);
                onClickedPosition.setColumn(0);
                break;
            case R.id.R50:
                onClickedPosition.setRow(5);
                onClickedPosition.setColumn(0);
                break;
            case R.id.R60:
                onClickedPosition.setRow(6);
                onClickedPosition.setColumn(0);
                break;
            case R.id.R70:
                onClickedPosition.setRow(7);
                onClickedPosition.setColumn(0);
                break;

            case R.id.R01:
                onClickedPosition.setRow(0);
                onClickedPosition.setColumn(1);
                break;
            case R.id.R11:
                onClickedPosition.setRow(1);
                onClickedPosition.setColumn(1);
                break;
            case R.id.R21:
                onClickedPosition.setRow(2);
                onClickedPosition.setColumn(1);
                break;
            case R.id.R31:
                onClickedPosition.setRow(3);
                onClickedPosition.setColumn(1);
                break;
            case R.id.R41:
                onClickedPosition.setRow(4);
                onClickedPosition.setColumn(1);
                break;
            case R.id.R51:
                onClickedPosition.setRow(5);
                onClickedPosition.setColumn(1);
                break;
            case R.id.R61:
                onClickedPosition.setRow(6);
                onClickedPosition.setColumn(1);
                break;
            case R.id.R71:
                onClickedPosition.setRow(7);
                onClickedPosition.setColumn(1);
                break;

            case R.id.R02:
                onClickedPosition.setRow(0);
                onClickedPosition.setColumn(2);
                break;
            case R.id.R12:
                onClickedPosition.setRow(1);
                onClickedPosition.setColumn(2);
                break;
            case R.id.R22:
                onClickedPosition.setRow(2);
                onClickedPosition.setColumn(2);
                break;
            case R.id.R32:
                onClickedPosition.setRow(3);
                onClickedPosition.setColumn(2);
                break;
            case R.id.R42:
                onClickedPosition.setRow(4);
                onClickedPosition.setColumn(2);
                break;
            case R.id.R52:
                onClickedPosition.setRow(5);
                onClickedPosition.setColumn(2);
                break;
            case R.id.R62:
                onClickedPosition.setRow(6);
                onClickedPosition.setColumn(2);
                break;
            case R.id.R72:
                onClickedPosition.setRow(7);
                onClickedPosition.setColumn(2);
                break;

            case R.id.R03:
                onClickedPosition.setRow(0);
                onClickedPosition.setColumn(3);
                break;
            case R.id.R13:
                onClickedPosition.setRow(1);
                onClickedPosition.setColumn(3);
                break;
            case R.id.R23:
                onClickedPosition.setRow(2);
                onClickedPosition.setColumn(3);
                break;
            case R.id.R33:
                onClickedPosition.setRow(3);
                onClickedPosition.setColumn(3);
                break;
            case R.id.R43:
                onClickedPosition.setRow(4);
                onClickedPosition.setColumn(3);
                break;
            case R.id.R53:
                onClickedPosition.setRow(5);
                onClickedPosition.setColumn(3);
                break;
            case R.id.R63:
                onClickedPosition.setRow(6);
                onClickedPosition.setColumn(3);
                break;
            case R.id.R73:
                onClickedPosition.setRow(7);
                onClickedPosition.setColumn(3);
                break;
            case R.id.R04:
                onClickedPosition.setRow(0);
                onClickedPosition.setColumn(4);
                break;
            case R.id.R14:
                onClickedPosition.setRow(1);
                onClickedPosition.setColumn(4);
                break;
            case R.id.R24:
                onClickedPosition.setRow(2);
                onClickedPosition.setColumn(4);
                break;
            case R.id.R34:
                onClickedPosition.setRow(3);
                onClickedPosition.setColumn(4);
                break;
            case R.id.R44:
                onClickedPosition.setRow(4);
                onClickedPosition.setColumn(4);
                break;
            case R.id.R54:
                onClickedPosition.setRow(5);
                onClickedPosition.setColumn(4);
                break;
            case R.id.R64:
                onClickedPosition.setRow(6);
                onClickedPosition.setColumn(4);
                break;
            case R.id.R74:
                onClickedPosition.setRow(7);
                onClickedPosition.setColumn(4);
                break;

            case R.id.R05:
                onClickedPosition.setRow(0);
                onClickedPosition.setColumn(5);
                break;
            case R.id.R15:
                onClickedPosition.setRow(1);
                onClickedPosition.setColumn(5);
                break;
            case R.id.R25:
                onClickedPosition.setRow(2);
                onClickedPosition.setColumn(5);
                break;
            case R.id.R35:
                onClickedPosition.setRow(3);
                onClickedPosition.setColumn(5);
                break;
            case R.id.R45:
                onClickedPosition.setRow(4);
                onClickedPosition.setColumn(5);
                break;
            case R.id.R55:
                onClickedPosition.setRow(5);
                onClickedPosition.setColumn(5);
                break;
            case R.id.R65:
                onClickedPosition.setRow(6);
                onClickedPosition.setColumn(5);
                break;
            case R.id.R75:
                onClickedPosition.setRow(7);
                onClickedPosition.setColumn(5);
                break;

            case R.id.R06:
                onClickedPosition.setRow(0);
                onClickedPosition.setColumn(6);
                break;
            case R.id.R16:
                onClickedPosition.setRow(1);
                onClickedPosition.setColumn(6);
                break;
            case R.id.R26:
                onClickedPosition.setRow(2);
                onClickedPosition.setColumn(6);
                break;
            case R.id.R36:
                onClickedPosition.setRow(3);
                onClickedPosition.setColumn(6);
                break;
            case R.id.R46:
                onClickedPosition.setRow(4);
                onClickedPosition.setColumn(6);
                break;
            case R.id.R56:
                onClickedPosition.setRow(5);
                onClickedPosition.setColumn(6);
                break;
            case R.id.R66:
                onClickedPosition.setRow(6);
                onClickedPosition.setColumn(6);
                break;
            case R.id.R76:
                onClickedPosition.setRow(7);
                onClickedPosition.setColumn(6);
                break;

            case R.id.R07:
                onClickedPosition.setRow(0);
                onClickedPosition.setColumn(7);
                break;
            case R.id.R17:
                onClickedPosition.setRow(1);
                onClickedPosition.setColumn(7);
                break;
            case R.id.R27:
                onClickedPosition.setRow(2);
                onClickedPosition.setColumn(7);
                break;
            case R.id.R37:
                onClickedPosition.setRow(3);
                onClickedPosition.setColumn(7);
                break;
            case R.id.R47:
                onClickedPosition.setRow(4);
                onClickedPosition.setColumn(7);
                break;
            case R.id.R57:
                onClickedPosition.setRow(5);
                onClickedPosition.setColumn(7);
                break;
            case R.id.R67:
                onClickedPosition.setRow(6);
                onClickedPosition.setColumn(7);
                break;
            case R.id.R77:
                onClickedPosition.setRow(7);
                onClickedPosition.setColumn(7);
                break;
            default:
                // not implemented
                break;
        }

        if (chessBoard.getPieceAtLocation(onClickedPosition) != null) {
            if (legalMoveList.size() == 0 || !onClickedPosition.checkIfLocationIsPartOfList(legalMoveList)) {
                selectedPiece = chessBoard.getPieceAtLocation(onClickedPosition);
                isPieceSelected = true;
            }
        }

        if (selectedPiece.getColour() != colour) {
            return;
        }

        if (isPieceSelected) {
            showLegalMoves();
        } else {
            for (Location loc : legalMoveList) {
                if (loc.compareLocation(onClickedPosition)) {
                    resetColour();
                    Move move = new Move(chessBoard.getLocationOf(selectedPiece), loc);
                    try {
                        ChessBoard copyOfChessBoard = chessBoard.copy();
                        List<Location> destroyedLocations = null;
                        if (!specialMoveActivated) {
                            destroyedPieceValue = copyOfChessBoard.performMoveOnBoard(move);
                        } else {
                            destroyedLocations = copyOfChessBoard.performAtomicMove(move);
                            executeSMB.setVisibility(View.INVISIBLE);
                            executeSMB.setText("Execute");
                            executeSMB.setBackgroundResource(R.drawable.custom_button_smb);
                            specialMoveActivated = false;
                        }
                        GameDataDTO<ChessBoard> data = new GameDataDTO<>(copyOfChessBoard);
                        data.setDestroyedLocationsByAtomicMove(destroyedLocations);
                        String gameDataJsonString = objectMapper.writeValueAsString(data);
                        client.sendGameUpdate(gameId, gameDataJsonString);
                        isAnyPieceSelected = false;
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(baseContext, "An error occurred", Toast.LENGTH_SHORT).show());
                        destroyedPieceValue = 0;
                    }

                    break;
                }
            }
            legalMoveList = new ArrayList<>();
        }

    }

    private void showLegalMoves() {
        resetColour();
        isAnyPieceSelected = true;
        boardViewBackground[onClickedPosition.getRow()][onClickedPosition.getColumn()].setBackgroundResource(R.color.select);
        legalMoveList = selectedPiece.getLegalMoves(chessBoard);
        for (Location loc : legalMoveList) {
            boardViewBackground[loc.getRow()][loc.getColumn()].setBackgroundResource(R.color.highlight_moves);
        }
        isPieceSelected = false;
    }

    // --> SpecialMoveBar
    public void SpecialMoveBar() {
        MediaPlayer smb = MediaPlayer.create(this, R.raw.smb_activate);
        specialMoveBar = findViewById(R.id.special_move_bar);
        specialMoveBar.setProgress(currentProgress);
        specialMoveBar.setMax(5);
        smbCount.setText(currentProgress + " | " + specialMoveBar.getMax());

        if (currentProgress >= specialMoveBar.getMax()) {
            buffer = currentProgress - specialMoveBar.getMax();
            Helper.playSMB_BarSound(this);
            smb.start();
            executeSMB.setVisibility(View.VISIBLE);
        }
    }

    public void resetColour() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    boardViewBackground[i][j].setBackgroundResource(R.color.dark_square);
                } else {
                    boardViewBackground[i][j].setBackgroundResource(R.color.light_square);
                }
            }
        }
    }

    private void animateAtomicHits(List<Location> destroyedLocations) {
        if (destroyedLocations == null) {
            return;
        }
        AnimatorSet animationSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (Location destroyedLoc : destroyedLocations) {
            View view = boardViewBackground[destroyedLoc.getRow()][destroyedLoc.getColumn()];
            int colorFrom = getColor(R.color.atomic_hit);
            int colorTo;
            if ((destroyedLoc.getRow() + destroyedLoc.getColumn()) % 2 == 0) {
                colorTo = getColor(R.color.dark_square);
            } else {
                colorTo = getColor(R.color.light_square);
            }
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.addUpdateListener(animator -> view.setBackgroundColor((int) animator.getAnimatedValue()));
            animators.add(colorAnimation);

        }
        animationSet.setDuration(2000);
        animationSet.playTogether(animators);
        animationSet.start();
    }

    private void parseChessBoardAndRefresh(String json) throws JsonProcessingException {
        GameDataDTO<?> gameData = objectMapper.readValue(json, GameDataDTO.class);
        if (gameData.getData() != null) {
            chessBoard = objectMapper.convertValue(gameData.getData(), ChessBoard.class);
        }
        nextPlayer = gameData.getNextPlayer();
        List<Location> atomicHits = gameData.getDestroyedLocationsByAtomicMove();
        runOnUiThread(() -> {
            initializePieces();
            animateAtomicHits(atomicHits);
            showCurrentPlayerInfo(nextPlayer.getName());
            if (chessBoard != null) {
                displayWinnerIfKingDied();

                if (isAnyPieceSelected) {
                    ChessPiece piece = chessBoard.getPieceAtLocation(onClickedPosition);
                    if (piece != null && piece.getColour().equals(colour)) {
                        selectedPiece = piece;
                        showLegalMoves();
                    } else {
                        resetColour();
                    }
                }
            }
        });
    }

    private void displayWinnerIfKingDied() {
        PieceColour pieceColour;
        if ((pieceColour = chessBoard.checkWinner()) != null) {
            displayWinnerNotification();
            try {
                List<Game> games = Helper.getGameList(this);
                for (Game g : games) {
                    if (g.getGameId().equals(Helper.getGameId(this))) {
                        if (g.getStatus() != Game.STATUS_FINISHED) {
                            if (pieceColour.equals(colour)) {
                                g.setWinner(true);
                                Helper.incrementWinCount(this);
                            } else {
                                g.setWinner(false);
                                Helper.incrementLossCount(this);
                            }
                        }
                        g.setStatus(Game.STATUS_FINISHED);
                        break;
                    }
                }
                Helper.writeGamesList(this, games);
            } catch (Exception e) {
                // unhandled
            }

            gameId = null;
            Helper.setGameId(baseContext, gameId);
        }
    }

    private void showCurrentPlayerInfo(String nextPlayerName) {
        String currentPlayerInfoStr;
        if (nextPlayerName.equals(playerName)) {
            currentPlayerInfoStr = "Your move";
        } else {
            currentPlayerInfoStr = "Friend's move";
        }
        currentPlayerInfo.setText(currentPlayerInfoStr);
    }

    private void refreshSpecialMoveBar() {
        if (!nextPlayer.getName().equals(playerName)) {
            // --> update SpecialMoveBar Progress
            if (destroyedPieceValue > 0) {
                pieceCaptured.start();
                currentProgress = currentProgress + destroyedPieceValue;
                runOnUiThread(this::SpecialMoveBar);
            } else {
                pieceMoved.start();
            }
            destroyedPieceValue = 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // --> update Soundsymbol
        if (Helper.getGameSound(this)) {
            soundButton.setImageResource(R.drawable.volume_on_white);
        } else {
            soundButton.setImageResource(R.drawable.volume_off_white);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameUpdateDisposable.dispose();
        getGameStateDisposable.dispose();
        Helper.setGameSound(this, false);
        Helper.stopGameSound(this);
    }


    public void displayWinnerNotification() {
        final Dialog winnerNotificationWindow = new Dialog(at.aau.se2.chessify.AndroidGameUI.BoardView.this);
        winnerNotificationWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        winnerNotificationWindow.setCancelable(true);
        winnerNotificationWindow.setContentView(R.layout.winner_notification_dialog);

        String notifMessage = getNotificationMessage(chessBoard.checkWinner());

        final TextView winnerNotificationText = winnerNotificationWindow.findViewById(R.id.winnerNotifTextView);
        winnerNotificationText.setText(notifMessage);

        winnerNotificationWindow.show();
    }

    public String getNotificationMessage(PieceColour pieceColour) {
        if (pieceColour == PieceColour.BLACK) {
            return "The player with the black pieces won!";
        }
        if (pieceColour == PieceColour.WHITE) {
            return "The player with the white pieces won!";
        }
        if (pieceColour == PieceColour.GREY) {
            return "The game ended in a draw!";
        }
        return "Winning player could not be determined";
    }

}
