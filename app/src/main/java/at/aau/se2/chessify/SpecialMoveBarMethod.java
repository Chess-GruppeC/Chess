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


    public static void refreshSpecialMoveBar(BoardView boardView) {
        if (!boardView.nextPlayer.getName().equals(boardView.playerName)) {
            // --> update SpecialMoveBar Progress
            if (boardView.destroyedPieceValue > 0) {
                boardView.pieceCaptured.start();
                boardView.currentProgress = boardView.currentProgress + boardView.destroyedPieceValue;
                boardView.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int mBuffer = SpecialMoveBarMethod.SpecialMoveBar(boardView, boardView.specialMoveBar, boardView.smbCount, boardView.executeSMB, boardView.currentProgress);
                        if (mBuffer != -1) {
                            boardView.buffer = mBuffer;
                        }
                    }
                });
                //  runOnUiThread(this::SpecialMoveBar);
            } else {
                boardView.pieceMoved.start();
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
            Helper.playSmbBarSound(activity);
            SMB.start();
            ExecuteSMB.setVisibility(View.VISIBLE);
            return Buffer;
        }

        return -1;
    }
}
