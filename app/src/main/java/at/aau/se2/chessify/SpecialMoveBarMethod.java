package at.aau.se2.chessify;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import at.aau.se2.chessify.AndroidGameUI.BoardView;
import at.aau.se2.chessify.util.Helper;

public class SpecialMoveBarMethod {

    // --> update SpecialMoveBar
    public static void refreshSpecialMoveBar(BoardView boardView) {
        if (!boardView.nextPlayer.getName().equals(boardView.playerName)) {
            // --> update SpecialMoveBar Progress
            if (boardView.destroyedPieceValue > 0) {
                boardView.PieceCaptured.start();
                boardView.currentProgress = boardView.currentProgress + boardView.destroyedPieceValue;
                boardView.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int mBuffer = SpecialMoveBarMethod.SpecialMoveBar(boardView, boardView.specialMoveBar, boardView.SMBCount, boardView.ExecuteSMB, boardView.currentProgress);
                        if (mBuffer != -1) {
                            boardView.Buffer = mBuffer;
                        }
                    }
                });
                //  runOnUiThread(this::SpecialMoveBar);
            } else {
                boardView.PieceMoved.start();
            }
            boardView.destroyedPieceValue = 0;
        }
    }

    // --> SpecialMoveBar
    public static int SpecialMoveBar(Activity activity, ProgressBar specialMoveBar, TextView SMBCount, Button ExecuteSMB, int currentProgress) {
        MediaPlayer SMB = MediaPlayer.create(activity, R.raw.smb_activate);
        specialMoveBar.setProgress(currentProgress);
        specialMoveBar.setMax(5);
        SMBCount.setText(currentProgress + " | " + specialMoveBar.getMax());

        if (currentProgress >= specialMoveBar.getMax()) {
            int Buffer = currentProgress - specialMoveBar.getMax();
            Helper.playSMB_BarSound(activity);
            SMB.start();
            ExecuteSMB.setVisibility(View.VISIBLE);
            return Buffer;
        }

        return -1;
    }
}
