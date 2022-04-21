package at.aau.se2.chessify.AndroidGameUI;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class BoardView extends Fragment {

    private static final String TAG = "BoardFragment";

    public BoardView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called.");
        return getBoard();
    }

    private TableLayout getBoard() {
        Log.d(TAG, "getBoard: called.");
        TableLayout board = new TableLayout(getContext());

        for (int i=1; i<=8; i++) {
            TableRow row = new TableRow(getContext());

            for (int j=1; j<=8; j++) {
                TextView square = new TextView(getContext());
                square.setHeight(ChessActivity.screenwidth / 8);
                square.setWidth(ChessActivity.screenwidth / 8);
                square.setId(i * 10 + j);
                square.setTag(i * 10 + j);

                if ((i + j) % 2 != 0) {
                    square.setBackgroundColor(Color.parseColor("#cccccc"));
                }
                else {
                    square.setBackgroundColor(Color.parseColor("#f2f2f2"));
                }

                row.addView(square);
            }
            board.addView(row);
        }

        return board;
    }
}