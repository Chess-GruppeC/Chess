package at.aau.se2.chessify.chessLogicTests.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import at.aau.se2.chessify.chess_logic.board.*;
import at.aau.se2.chessify.chess_logic.pieces.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ChessBoardTest {

    private ChessBoard testChessBoard;
    private ChessPiece[][] manuallyGeneratedTestBoard = new ChessPiece[8][8];

    @Test
    public void testInitializingChessBoard() {
        initTestBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (testChessBoard.getGameBoard()[i][j] != null
                        && manuallyGeneratedTestBoard[i][j] != null) {

                    assertEquals(manuallyGeneratedTestBoard[i][j].getClass(), testChessBoard.getGameBoard()[i][j].getClass());
                    assertEquals(manuallyGeneratedTestBoard[i][j].getColour(), testChessBoard.getGameBoard()[i][j].getColour());

                } else {

                    assertNull(testChessBoard.getGameBoard()[i][j]);
                    assertNull(manuallyGeneratedTestBoard[i][j]);

                }

            }
        }

    }

    private void initTestBoard() {
        manuallyGeneratedTestBoard[0][0] = new Rook(PieceColour.BLACK);
        manuallyGeneratedTestBoard[0][7] = new Rook(PieceColour.BLACK);

        manuallyGeneratedTestBoard[0][1] = new Knight(PieceColour.BLACK);
        manuallyGeneratedTestBoard[0][6] = new Knight(PieceColour.BLACK);

        manuallyGeneratedTestBoard[0][2] = new Bishop(PieceColour.BLACK);
        manuallyGeneratedTestBoard[0][5] = new Bishop(PieceColour.BLACK);

        manuallyGeneratedTestBoard[0][3] = new Queen(PieceColour.BLACK);
        manuallyGeneratedTestBoard[0][4] = new King(PieceColour.BLACK);

        for (int i = 0; i < 8; i++) {
            manuallyGeneratedTestBoard[1][i] = new Pawn(PieceColour.BLACK);
        }

        manuallyGeneratedTestBoard[7][0] = new Rook(PieceColour.WHITE);
        manuallyGeneratedTestBoard[7][7] = new Rook(PieceColour.WHITE);

        manuallyGeneratedTestBoard[7][1] = new Knight(PieceColour.WHITE);
        manuallyGeneratedTestBoard[7][6] = new Knight(PieceColour.WHITE);

        manuallyGeneratedTestBoard[7][2] = new Bishop(PieceColour.WHITE);
        manuallyGeneratedTestBoard[7][5] = new Bishop(PieceColour.WHITE);

        manuallyGeneratedTestBoard[7][3] = new Queen(PieceColour.WHITE);
        manuallyGeneratedTestBoard[7][4] = new King(PieceColour.WHITE);

        for (int i = 0; i < 8; i++) {
            manuallyGeneratedTestBoard[6][i] = new Pawn(PieceColour.WHITE);
        }
    }

    @Before
    public void initializeTestChessBoard() {
        testChessBoard = new ChessBoard();
    }

    @Test
    public void performAtomicMoveNoPiecesHitTest() {
        initTestBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        Location from = new Location(1, 6);
        List<Location> legalMoves = testChessBoard.getPieceAtLocation(from).getLegalMoves(testChessBoard);
        assertTrue(legalMoves.size() > 0);
        Move move = new Move(from, legalMoves.get(0));
        List<Location> destroyedLocations = testChessBoard.performAtomicMove(move);
        assertNull(destroyedLocations);
    }

    @Test
    public void performAtomicMovePiecesHitTest() {
        initTestBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        Location from = new Location(1, 2);
        manuallyGeneratedTestBoard[from.getRow()][from.getColumn()] = new Pawn(PieceColour.WHITE);
        Location to = new Location(0, 3);
        List<Location> legalMoves = testChessBoard.getPieceAtLocation(from).getLegalMoves(testChessBoard);
        assertTrue(legalMoves.size() > 0);
        Move move = new Move(from, to);
        List<Location> destroyedLocations = testChessBoard.performAtomicMove(move);
        assertEquals(3, destroyedLocations.size());
    }

    @Test
    public void checkWinnerTestNoWinner(){
        assertEquals(null, testChessBoard.checkWinner());
    }

    @Test
    public void checkWinnerTestDraw(){
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        assertEquals(PieceColour.GREY, testChessBoard.checkWinner());
    }

    @Test
    public void checkWinnerTestWhiteWin(){
        manuallyGeneratedTestBoard[5][5]=new King(PieceColour.WHITE);
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        assertEquals(PieceColour.WHITE, testChessBoard.checkWinner());
    }

    @Test
    public void checkWinnerTestBlackWin(){
        manuallyGeneratedTestBoard[5][5]=new King(PieceColour.BLACK);
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        assertEquals(PieceColour.BLACK, testChessBoard.checkWinner());
    }

}
