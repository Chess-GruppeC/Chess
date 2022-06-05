package at.aau.se2.chessify.AndroidGameUI;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.network.dto.GameDataDTO;
import at.aau.se2.chessify.network.dto.PlayerDTO;
import at.aau.se2.chessify.util.Helper;
import io.reactivex.disposables.Disposable;


public class BoardView extends AppCompatActivity implements View.OnClickListener {

    private int currentProgress = 0;
    private int Buffer;
    ProgressBar specialMoveBar;
    TextView SMBCount;
    ImageView Soundbutton;
    Button ExecuteSMB;
    private TextView currentPlayerInfo;


    public TextView[][] BoardView = new TextView[8][8];
    public TextView[][] BoardViewBackground = new TextView[8][8];

    public Location onClickedPosition = new Location(0, 0);
    public ChessPiece selectedPiece;
    public boolean isPieceSelected = false;
    ChessBoard chessBoard = new ChessBoard();
    ArrayList<Location> legalMoveList = new ArrayList<>();
    ChessBoard savedChessBoard = new ChessBoard();
    ArrayList<Location[][]> LastMove = new ArrayList<Location[][]>();
    public int countMoves;

    private boolean specialMoveActivated = false;
    private WebSocketClient client;

    private Disposable gameUpdateDisposable, getGameStateDisposable;
    private ObjectMapper objectMapper;

    private TextView textView_gameId;

    private String gameId;

    private int destroyedPieceValue = 0;

    private String playerName;

    private PlayerDTO nextPlayer;

    private MediaPlayer PieceCaptured;
    private MediaPlayer PieceMoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_board);

        textView_gameId = findViewById(R.id.tV_game_id);
        currentPlayerInfo = findViewById(R.id.current_player_info);

        initializeBoard();

        objectMapper = new ObjectMapper();
        client = WebSocketClient.getInstance(Helper.getUniquePlayerName(this));

        gameId = Helper.getGameId(this);
        playerName = Helper.getUniquePlayerName(this);
        textView_gameId.setText("#".concat(gameId));

        getGameStateDisposable = client.fetchGameState(gameId)
                .subscribe(lastState -> parseChessBoardAndRefresh(lastState.getPayload()),
                        throwable -> runOnUiThread(() -> Toast.makeText(getBaseContext(), "An error occurred", Toast.LENGTH_SHORT).show()));

        gameUpdateDisposable = client.receiveGameUpdates(gameId)
                .subscribe(update -> {
                    parseChessBoardAndRefresh(update.getPayload());
                    refreshSpecialMoveBar();
                    if (!getGameStateDisposable.isDisposed())
                        getGameStateDisposable.dispose();
                }, throwable -> runOnUiThread(() -> Toast.makeText(getBaseContext(), "An error occurred", Toast.LENGTH_SHORT).show()));

        SMBCount = findViewById(R.id.textViewSMBCount);
        Soundbutton = findViewById(R.id.btn_sound_BoardView);
        ExecuteSMB = findViewById(R.id.btn_execute_SMB);
        ExecuteSMB.setVisibility(View.INVISIBLE);
        Helper.setBackgroundSound(this, false);
        Helper.stopMusicBackground(this);
        //Helper.playGameSound(this);
        //Helper.setGameSound(this, true);

        SpecialMoveBar();
        SMBCount.setText(currentProgress + " | " + specialMoveBar.getMax());

        Soundbutton.setOnClickListener(view -> {
            if (Helper.getGameSound(this)) {
                Soundbutton.setImageResource(R.drawable.volume_off_white);
                Helper.setGameSound(this, false);
                Helper.stopGameSound(this);

            } else {
                Helper.playGameSound(this);
                Soundbutton.setImageResource(R.drawable.volume_on_white);
                Helper.setGameSound(this, true);
                Soundbutton.setSoundEffectsEnabled(true);

            }
        });

        ExecuteSMB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ToDO perform SpecialMove --> Atomic Move

                // --> Executes SMB Bar - sets progress to remaining buffer
                specialMoveActivated = true;
                currentProgress = 0;
                specialMoveBar.setProgress(currentProgress);
//                ExecuteSMB.setVisibility(View.INVISIBLE);
                SMBCount.setText(Buffer + " | " + specialMoveBar.getMax());
                currentProgress = Buffer;
                specialMoveBar.setProgress(currentProgress);
                ExecuteSMB.setText("ACTIVE");
                ExecuteSMB.setBackgroundResource(R.drawable.custom_button_lobby_join_success);
                Buffer = 0;
            }
        });

    }


    private void initializeBoard() {

        BoardView[0][0] = (TextView) findViewById(R.id.R00);
        BoardViewBackground[0][0] = (TextView) findViewById(R.id.R000);
        BoardView[1][0] = (TextView) findViewById(R.id.R10);
        BoardViewBackground[1][0] = (TextView) findViewById(R.id.R010);
        BoardView[2][0] = (TextView) findViewById(R.id.R20);
        BoardViewBackground[2][0] = (TextView) findViewById(R.id.R020);
        BoardView[3][0] = (TextView) findViewById(R.id.R30);
        BoardViewBackground[3][0] = (TextView) findViewById(R.id.R030);
        BoardView[4][0] = (TextView) findViewById(R.id.R40);
        BoardViewBackground[4][0] = (TextView) findViewById(R.id.R040);
        BoardView[5][0] = (TextView) findViewById(R.id.R50);
        BoardViewBackground[5][0] = (TextView) findViewById(R.id.R050);
        BoardView[6][0] = (TextView) findViewById(R.id.R60);
        BoardViewBackground[6][0] = (TextView) findViewById(R.id.R060);
        BoardView[7][0] = (TextView) findViewById(R.id.R70);
        BoardViewBackground[7][0] = (TextView) findViewById(R.id.R070);

        BoardView[0][1] = (TextView) findViewById(R.id.R01);
        BoardViewBackground[0][1] = (TextView) findViewById(R.id.R001);
        BoardView[1][1] = (TextView) findViewById(R.id.R11);
        BoardViewBackground[1][1] = (TextView) findViewById(R.id.R011);
        BoardView[2][1] = (TextView) findViewById(R.id.R21);
        BoardViewBackground[2][1] = (TextView) findViewById(R.id.R021);
        BoardView[3][1] = (TextView) findViewById(R.id.R31);
        BoardViewBackground[3][1] = (TextView) findViewById(R.id.R031);
        BoardView[4][1] = (TextView) findViewById(R.id.R41);
        BoardViewBackground[4][1] = (TextView) findViewById(R.id.R041);
        BoardView[5][1] = (TextView) findViewById(R.id.R51);
        BoardViewBackground[5][1] = (TextView) findViewById(R.id.R051);
        BoardView[6][1] = (TextView) findViewById(R.id.R61);
        BoardViewBackground[6][1] = (TextView) findViewById(R.id.R061);
        BoardView[7][1] = (TextView) findViewById(R.id.R71);
        BoardViewBackground[7][1] = (TextView) findViewById(R.id.R071);

        BoardView[0][2] = (TextView) findViewById(R.id.R02);
        BoardViewBackground[0][2] = (TextView) findViewById(R.id.R002);
        BoardView[1][2] = (TextView) findViewById(R.id.R12);
        BoardViewBackground[1][2] = (TextView) findViewById(R.id.R012);
        BoardView[2][2] = (TextView) findViewById(R.id.R22);
        BoardViewBackground[2][2] = (TextView) findViewById(R.id.R022);
        BoardView[3][2] = (TextView) findViewById(R.id.R32);
        BoardViewBackground[3][2] = (TextView) findViewById(R.id.R032);
        BoardView[4][2] = (TextView) findViewById(R.id.R42);
        BoardViewBackground[4][2] = (TextView) findViewById(R.id.R042);
        BoardView[5][2] = (TextView) findViewById(R.id.R52);
        BoardViewBackground[5][2] = (TextView) findViewById(R.id.R052);
        BoardView[6][2] = (TextView) findViewById(R.id.R62);
        BoardViewBackground[6][2] = (TextView) findViewById(R.id.R062);
        BoardView[7][2] = (TextView) findViewById(R.id.R72);
        BoardViewBackground[7][2] = (TextView) findViewById(R.id.R072);

        BoardView[0][3] = (TextView) findViewById(R.id.R03);
        BoardViewBackground[0][3] = (TextView) findViewById(R.id.R003);
        BoardView[1][3] = (TextView) findViewById(R.id.R13);
        BoardViewBackground[1][3] = (TextView) findViewById(R.id.R013);
        BoardView[2][3] = (TextView) findViewById(R.id.R23);
        BoardViewBackground[2][3] = (TextView) findViewById(R.id.R023);
        BoardView[3][3] = (TextView) findViewById(R.id.R33);
        BoardViewBackground[3][3] = (TextView) findViewById(R.id.R033);
        BoardView[4][3] = (TextView) findViewById(R.id.R43);
        BoardViewBackground[4][3] = (TextView) findViewById(R.id.R043);
        BoardView[5][3] = (TextView) findViewById(R.id.R53);
        BoardViewBackground[5][3] = (TextView) findViewById(R.id.R053);
        BoardView[6][3] = (TextView) findViewById(R.id.R63);
        BoardViewBackground[6][3] = (TextView) findViewById(R.id.R063);
        BoardView[7][3] = (TextView) findViewById(R.id.R73);
        BoardViewBackground[7][3] = (TextView) findViewById(R.id.R073);

        BoardView[0][4] = (TextView) findViewById(R.id.R04);
        BoardViewBackground[0][4] = (TextView) findViewById(R.id.R004);
        BoardView[1][4] = (TextView) findViewById(R.id.R14);
        BoardViewBackground[1][4] = (TextView) findViewById(R.id.R014);
        BoardView[2][4] = (TextView) findViewById(R.id.R24);
        BoardViewBackground[2][4] = (TextView) findViewById(R.id.R024);
        BoardView[3][4] = (TextView) findViewById(R.id.R34);
        BoardViewBackground[3][4] = (TextView) findViewById(R.id.R034);
        BoardView[4][4] = (TextView) findViewById(R.id.R44);
        BoardViewBackground[4][4] = (TextView) findViewById(R.id.R044);
        BoardView[5][4] = (TextView) findViewById(R.id.R54);
        BoardViewBackground[5][4] = (TextView) findViewById(R.id.R054);
        BoardView[6][4] = (TextView) findViewById(R.id.R64);
        BoardViewBackground[6][4] = (TextView) findViewById(R.id.R064);
        BoardView[7][4] = (TextView) findViewById(R.id.R74);
        BoardViewBackground[7][4] = (TextView) findViewById(R.id.R074);

        BoardView[0][5] = (TextView) findViewById(R.id.R05);
        BoardViewBackground[0][5] = (TextView) findViewById(R.id.R005);
        BoardView[1][5] = (TextView) findViewById(R.id.R15);
        BoardViewBackground[1][5] = (TextView) findViewById(R.id.R015);
        BoardView[2][5] = (TextView) findViewById(R.id.R25);
        BoardViewBackground[2][5] = (TextView) findViewById(R.id.R025);
        BoardView[3][5] = (TextView) findViewById(R.id.R35);
        BoardViewBackground[3][5] = (TextView) findViewById(R.id.R035);
        BoardView[4][5] = (TextView) findViewById(R.id.R45);
        BoardViewBackground[4][5] = (TextView) findViewById(R.id.R045);
        BoardView[5][5] = (TextView) findViewById(R.id.R55);
        BoardViewBackground[5][5] = (TextView) findViewById(R.id.R055);
        BoardView[6][5] = (TextView) findViewById(R.id.R65);
        BoardViewBackground[6][5] = (TextView) findViewById(R.id.R065);
        BoardView[7][5] = (TextView) findViewById(R.id.R75);
        BoardViewBackground[7][5] = (TextView) findViewById(R.id.R075);

        BoardView[0][6] = (TextView) findViewById(R.id.R06);
        BoardViewBackground[0][6] = (TextView) findViewById(R.id.R006);
        BoardView[1][6] = (TextView) findViewById(R.id.R16);
        BoardViewBackground[1][6] = (TextView) findViewById(R.id.R016);
        BoardView[2][6] = (TextView) findViewById(R.id.R26);
        BoardViewBackground[2][6] = (TextView) findViewById(R.id.R026);
        BoardView[3][6] = (TextView) findViewById(R.id.R36);
        BoardViewBackground[3][6] = (TextView) findViewById(R.id.R036);
        BoardView[4][6] = (TextView) findViewById(R.id.R46);
        BoardViewBackground[4][6] = (TextView) findViewById(R.id.R046);
        BoardView[5][6] = (TextView) findViewById(R.id.R56);
        BoardViewBackground[5][6] = (TextView) findViewById(R.id.R056);
        BoardView[6][6] = (TextView) findViewById(R.id.R66);
        BoardViewBackground[6][6] = (TextView) findViewById(R.id.R066);
        BoardView[7][6] = (TextView) findViewById(R.id.R76);
        BoardViewBackground[7][6] = (TextView) findViewById(R.id.R076);

        BoardView[0][7] = (TextView) findViewById(R.id.R07);
        BoardViewBackground[0][7] = (TextView) findViewById(R.id.R007);
        BoardView[1][7] = (TextView) findViewById(R.id.R17);
        BoardViewBackground[1][7] = (TextView) findViewById(R.id.R017);
        BoardView[2][7] = (TextView) findViewById(R.id.R27);
        BoardViewBackground[2][7] = (TextView) findViewById(R.id.R027);
        BoardView[3][7] = (TextView) findViewById(R.id.R37);
        BoardViewBackground[3][7] = (TextView) findViewById(R.id.R037);
        BoardView[4][7] = (TextView) findViewById(R.id.R47);
        BoardViewBackground[4][7] = (TextView) findViewById(R.id.R047);
        BoardView[5][7] = (TextView) findViewById(R.id.R57);
        BoardViewBackground[5][7] = (TextView) findViewById(R.id.R057);
        BoardView[6][7] = (TextView) findViewById(R.id.R67);
        BoardViewBackground[6][7] = (TextView) findViewById(R.id.R067);
        BoardView[7][7] = (TextView) findViewById(R.id.R77);
        BoardViewBackground[7][7] = (TextView) findViewById(R.id.R077);

        initializePieces();
        // setAltBoard();
    }

    private void initializePieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece chessPiece = chessBoard.getPieceAtLocation(new Location(i, j));

                if (chessPiece != null) {
                    if (chessPiece.getClass() == Pawn.class && chessPiece.getColour() == PieceColour.BLACK) {
                        BoardView[i][j].setBackgroundResource(R.drawable.black_pawn);
                    }
                    if (chessPiece.getClass() == Pawn.class && chessPiece.getColour() == PieceColour.WHITE) {
                        BoardView[i][j].setBackgroundResource(R.drawable.white_pawn);
                    }
                    if (chessPiece.getClass() == Rook.class && chessPiece.getColour() == PieceColour.BLACK) {
                        BoardView[i][j].setBackgroundResource(R.drawable.black_rook);
                    }
                    if (chessPiece.getClass() == Rook.class && chessPiece.getColour() == PieceColour.WHITE) {
                        BoardView[i][j].setBackgroundResource(R.drawable.white_rook);
                    }
                    if (chessPiece.getClass() == Bishop.class && chessPiece.getColour() == PieceColour.BLACK) {
                        BoardView[i][j].setBackgroundResource(R.drawable.black_bishop);
                    }
                    if (chessPiece.getClass() == Bishop.class && chessPiece.getColour() == PieceColour.WHITE) {
                        BoardView[i][j].setBackgroundResource(R.drawable.white_bishop);
                    }
                    if (chessPiece.getClass() == Knight.class && chessPiece.getColour() == PieceColour.BLACK) {
                        BoardView[i][j].setBackgroundResource(R.drawable.black_knight);
                    }
                    if (chessPiece.getClass() == Knight.class && chessPiece.getColour() == PieceColour.WHITE) {
                        BoardView[i][j].setBackgroundResource(R.drawable.white_knight);
                    }
                    if (chessPiece.getClass() == King.class && chessPiece.getColour() == PieceColour.BLACK) {
                        BoardView[i][j].setBackgroundResource(R.drawable.black_king);
                    }
                    if (chessPiece.getClass() == King.class && chessPiece.getColour() == PieceColour.WHITE) {
                        BoardView[i][j].setBackgroundResource(R.drawable.white_king);
                    }
                    if (chessPiece.getClass() == Queen.class && chessPiece.getColour() == PieceColour.BLACK) {
                        BoardView[i][j].setBackgroundResource(R.drawable.black_queen);
                    }
                    if (chessPiece.getClass() == Queen.class && chessPiece.getColour() == PieceColour.WHITE) {
                        BoardView[i][j].setBackgroundResource(R.drawable.white_queen);
                    }
                } else {
                    BoardView[i][j].setBackgroundResource(android.R.color.transparent);
                }
            }
        }

    }

    private void setAltBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

            }
        }


    }

    @Override
    public void onClick(View view) {
        PieceCaptured = MediaPlayer.create(this, R.raw.piece_captured);
        PieceMoved = MediaPlayer.create(this, R.raw.piece_move_two);
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
        }

        if (chessBoard.getPieceAtLocation(onClickedPosition) != null) {
            if (legalMoveList.size() == 0 || !onClickedPosition.checkIfLocationIsPartOfList(legalMoveList)) {
                selectedPiece = chessBoard.getPieceAtLocation(onClickedPosition);
                isPieceSelected = true;
            }
        }
        /*
        if (selectedPiece != null) {
            isPieceSelected = true;
        }

            /*
            if (!isPieceSelected) {
                chessBoard.getPieceAtLocation(onClickedPosition);
                BoardViewBackground[onClickedPosition.getRow()][onClickedPosition.getColumn()].setBackgroundResource(R.color.select);
                isPieceSelected = true;
            }else {
                chessBoard.setLocationTo(chessBoard.getPieceAtLocation(onClickedPosition), onClickedPosition);
                BoardView[onClickedPosition.getRow()][onClickedPosition.getColumn()].setBackgroundResource(R.drawable.white_queen);
                isPieceSelected = false;
            }
             */
        if (isPieceSelected) {
            resetColour();
            BoardViewBackground[onClickedPosition.getRow()][onClickedPosition.getColumn()].setBackgroundResource(R.color.select);
            legalMoveList = selectedPiece.getLegalMoves(chessBoard);
            for (Location loc : legalMoveList) {
                BoardViewBackground[loc.getRow()][loc.getColumn()].setBackgroundResource(R.color.highlight_moves);
            }
            isPieceSelected = false;
        } else {
            for (Location loc : legalMoveList) {
                if (loc.compareLocation(onClickedPosition)) {
                    resetColour();
                    Move move = new Move(chessBoard.getLocationOf(selectedPiece), loc);
                    try {
                        ChessBoard copyOfChessBoard = chessBoard.copy();
                        List<Location> destroyedLocations = null;
                        if(!specialMoveActivated) {
                            destroyedPieceValue = copyOfChessBoard.performMoveOnBoard(move);
                        } else {
                            destroyedLocations = copyOfChessBoard.performAtomicMove(move);
                            ExecuteSMB.setVisibility(View.INVISIBLE);
                            ExecuteSMB.setText("Execute");
                            ExecuteSMB.setBackgroundResource(R.drawable.custom_button_smb);
                            specialMoveActivated = false;
                        }
                        GameDataDTO<ChessBoard> data = new GameDataDTO<>(copyOfChessBoard);
                        data.setDestroyedLocationsByAtomicMove(destroyedLocations);
                        String gameDataJsonString = objectMapper.writeValueAsString(data);
                        client.sendGameUpdate(gameId, gameDataJsonString);
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(getBaseContext(), "An error occurred", Toast.LENGTH_SHORT).show());
                        destroyedPieceValue = 0;
                    }

                    break;
                }
            }
            legalMoveList = new ArrayList<>();
        }

    }

    //method:
    //TODO: safe board
    //TODO: undo
    //TODO: choice
    //TODO: set color at allowed position
    //TODO: is King in danger

    // --> SpecialMoveBar
    public void SpecialMoveBar() {
        MediaPlayer SMB = MediaPlayer.create(this, R.raw.smb_activate);
        specialMoveBar = findViewById(R.id.special_move_bar);
        specialMoveBar.setProgress(currentProgress);
        specialMoveBar.setMax(5);
        SMBCount.setText(currentProgress + " | " + specialMoveBar.getMax());

        if (currentProgress >= specialMoveBar.getMax()) {
            Buffer = currentProgress - specialMoveBar.getMax();
            Helper.playSMB_BarSound(this);
            SMB.start();
            ExecuteSMB.setVisibility(View.VISIBLE);
        }
    }

    public void resetColour() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    BoardViewBackground[i][j].setBackgroundResource(R.color.dark_square);
                } else {
                    BoardViewBackground[i][j].setBackgroundResource(R.color.light_square);
                }
            }
        }
    }

    private void animateAtomicHits(List<Location> destroyedLocations) {
        if(destroyedLocations == null) {
            return;
        }
        AnimatorSet animationSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (Location destroyedLoc : destroyedLocations) {
            View view = BoardViewBackground[destroyedLoc.getRow()][destroyedLoc.getColumn()];
            int colorFrom = getColor(R.color.atomic_hit);
            int colorTo;
            if ((destroyedLoc.getRow() + destroyedLoc.getColumn()) % 2 == 0) {
                colorTo = getColor(R.color.dark_square);
            } else {
                colorTo = getColor(R.color.light_square);
            }
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    view.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
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
        nextPlayer = gameData.getNextPlayer();     // TODO Show on UI
        List<Location> atomicHits = gameData.getDestroyedLocationsByAtomicMove();
        runOnUiThread(() -> {
            initializePieces();
            animateAtomicHits(atomicHits);
            showCurrentPlayerInfo(nextPlayer.getName());
        });
    }

    private void showCurrentPlayerInfo(String nextPlayerName) {
        Log.d("PLAYER", "SHow current");
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
                PieceCaptured.start();
                currentProgress = currentProgress + destroyedPieceValue;
                runOnUiThread(this::SpecialMoveBar);
            } else {
                PieceMoved.start();
            }
            destroyedPieceValue = 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // --> update Soundsymbol
        if (Helper.getGameSound(this)) {
            Soundbutton.setImageResource(R.drawable.volume_on_white);
        } else {
            Soundbutton.setImageResource(R.drawable.volume_off_white);
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


}
