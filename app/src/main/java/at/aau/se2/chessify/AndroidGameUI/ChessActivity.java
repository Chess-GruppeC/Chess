package at.aau.se2.chessify.AndroidGameUI;


import android.app.Activity;
import android.widget.GridView;

import at.aau.se2.chessify.R;

public class ChessActivity extends Activity {
    private boolean isPlayer2 = true;
    private static final Color player2color = Color.BLACK;
//    private player2 player = Player2.getInstance(player2color);
    private long startTime = 150000; // 1min (millisec)

    private Color activeTimer = Color.WHITE;
    private GridView boardView;
    private Square adapter;
    private boolean isGameOver = false;

    private void initBoard() {
        adapter = new Square(this);
        boardView = (GridView) boardView.findViewById(R.id.chessBoard);
        boardView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        boardView.setAdapter(adapter);
    }
    /*void refreshAdapter(Board board) {
        adapter = new Square(this, board);
        boardView.setAdapter(adapter);
    }*/
    private void gameOver(){
        //show GameOver
    }
    boolean isGameOver(){
        return isGameOver;
    }

    boolean player2Move() {
        return isPlayer2 && player2color == activeTimer;
    }

    private class GetPlayer2Move {

    }
    boolean isPlayer2(){
        return isPlayer2;
    }

}
