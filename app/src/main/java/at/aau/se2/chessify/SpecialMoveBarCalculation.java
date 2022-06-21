package at.aau.se2.chessify;


import at.aau.se2.chessify.AndroidGameUI.BoardView;

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
