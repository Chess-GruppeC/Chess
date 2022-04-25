package at.aau.se2.chessify.AndroidGameUI;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.R;

public class ChessActivity extends AppCompatActivity {

    private static final String TAG = "ChessActivity";

    public static int screenwidth;
    public static int screenheight;
    private static String mPlayerTurn;
    private BoardView board;
    private int currentProgress = 0;
    ProgressBar specialMoveBar;


    public static String getPlayerTurn() {
        return mPlayerTurn;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        initNewGame();
        initSpecialmoveBar();
    }

    public void initNewGame() {
        Log.d(TAG, "initNewGame: called.");
        setScreenDimensions();
        initBoard();
    }

    public void setScreenDimensions() {
        screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenheight = Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void initBoard() {
        board = new BoardView();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.board_container, board)
                .commit();
    }


    public void initSpecialmoveBar() {
        specialMoveBar = findViewById(R.id.special_move_bar);
    }


/* Konzept der Special Move Bar
    public void fillSpecialMoveBar(){

        if (piece.getPieceId().contains("bp"))
            currentProgress = currentProgress + 2;
        else if (piece.getPieceId().contains("bn") || piece.getPieceId().contains("bb"))
            currentProgress = currentProgress + 5;
        else if (piece.getPieceId().contains("br"))
            currentProgress = currentProgress + 7;
        else if (piece.getPieceId().contains("bq"))
            currentProgress = currentProgress + 9;

        specialMoveBar.setProgress(currentProgress);
        specialMoveBar.setMax(30);
    }
*/


}