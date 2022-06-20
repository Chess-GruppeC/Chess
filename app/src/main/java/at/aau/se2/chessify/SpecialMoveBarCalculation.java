package at.aau.se2.chessify;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import at.aau.se2.chessify.AndroidGameUI.BoardView;
import at.aau.se2.chessify.util.Helper;

public class SpecialMoveBarCalculation {

    // --> calculate currentProgress
    public static int calculateProgress() {
        return BoardView.currentProgress = BoardView.currentProgress + BoardView.destroyedPieceValue;
    }

    // --> calculate remaining buffer
    public static int calculateBuffer() {
        return BoardView.buffer = BoardView.currentProgress - BoardView.specialMoveBar.getMax();
    }
}
