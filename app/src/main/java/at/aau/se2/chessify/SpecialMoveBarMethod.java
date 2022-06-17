package at.aau.se2.chessify;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import at.aau.se2.chessify.util.Helper;

public class SpecialMoveBarMethod {
    public static int SpecialMoveBar(Activity activity, ProgressBar specialMoveBar, TextView SMBCount, Button ExecuteSMB, int currentProgress) {
        MediaPlayer SMB = MediaPlayer.create(activity, R.raw.smb_activate);
        specialMoveBar = activity.findViewById(R.id.special_move_bar);
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
