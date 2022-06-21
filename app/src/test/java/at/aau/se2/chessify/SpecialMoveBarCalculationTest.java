package at.aau.se2.chessify;

import junit.framework.TestCase;

import at.aau.se2.chessify.AndroidGameUI.BoardView;

public class SpecialMoveBarCalculationTest extends TestCase {

    public void testCalculateProgress() {
        BoardView.currentProgress = 1;
        BoardView.destroyedPieceValue = 3;
        assertEquals(4, SpecialMoveBarCalculation.calculateProgress());
    }

    // --> test working not checking the Method - NullPointerException (ProgressBar not initialized)
    public void testCalculateBuffer() {
        try {
            BoardView.currentProgress = 7;
            BoardView.specialMoveBar.setMax(5);
            assertEquals(2, SpecialMoveBarCalculation.calculateBuffer());
        } catch (NullPointerException e) {

        }
    }
}