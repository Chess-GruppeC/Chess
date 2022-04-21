package at.aau.se2.chessify.AndroidGameUI;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.R;

public class ChessActivity extends AppCompatActivity {

    private static final String TAG = "ChessActivity";

    public static int screenwidth;
    public static int screenheight;
    private static String mPlayerTurn;

    private BoardView board;


    public static String getPlayerTurn() {
        return mPlayerTurn;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        initNewGame();
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






}