package at.aau.se2.chessify.chessLogicTests.board;

import static org.junit.Assert.*;

import org.junit.Test;

import at.aau.se2.chessify.chessLogic.board.ChessBoard;
import at.aau.se2.chessify.chessLogic.board.Location;
import at.aau.se2.chessify.chessLogic.board.Move;
import at.aau.se2.chessify.chessLogic.pieces.Bishop;
import at.aau.se2.chessify.chessLogic.pieces.ChessPiece;
import at.aau.se2.chessify.chessLogic.pieces.Knight;
import at.aau.se2.chessify.chessLogic.pieces.Pawn;
import at.aau.se2.chessify.chessLogic.pieces.PieceColour;
import at.aau.se2.chessify.chessLogic.pieces.Queen;
import at.aau.se2.chessify.chessLogic.pieces.Rook;

public class PerformMoveOnBoardTest {

    /*@Test
    public void testConstructor() {
        ChessBoard actualChessBoard = new ChessBoard();
        actualChessBoard.setGameBoard(new ChessPiece[][]{new ChessPiece[]{new Bishop(PieceColour.BLACK)}});
        ChessPiece[][] expectedGameBoard = ChessBoard.gameboard;
        assertSame(expectedGameBoard, actualChessBoard.getGameBoard());
    }
    */


    @Test
    public void testConstructor2() {
        assertEquals(8, (new ChessBoard()).getGameBoard().length);
    }

    @Test
    public void testPerformMoveOnBoard() throws IllegalArgumentException {
        ChessBoard chessBoard = new ChessBoard();
        Location from = new Location(1, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard2() throws IllegalArgumentException {
        ChessBoard chessBoard = new ChessBoard();
        Location from = new Location(8, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard3() throws IllegalArgumentException {
        ChessBoard chessBoard = new ChessBoard();
        Location from = new Location(0, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard4() throws IllegalArgumentException {
        ChessBoard chessBoard = new ChessBoard();
        Location from = new Location(-1, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard5() throws IllegalArgumentException {
        ChessBoard chessBoard = new ChessBoard();
        Location from = new Location(1, 8);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard6() throws IllegalArgumentException {
        ChessBoard chessBoard = new ChessBoard();
        Location from = new Location(1, -1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard7() throws IllegalArgumentException {
        ChessBoard chessBoard = new ChessBoard();
        Location from = new Location(1, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(8, 1))));
    }

    @Test
    public void testGetLocationOf() {
        ChessBoard chessBoard = new ChessBoard();
        assertNull(chessBoard.getLocationOf(new Bishop(PieceColour.BLACK)));
    }

    @Test
    public void testGetLocationOf2() {
        Location actualLocationOf = (new ChessBoard()).getLocationOf(null);
        assertEquals(0, actualLocationOf.getColumn());
        assertEquals(2, actualLocationOf.getRow());
    }


    @Test
    public void testGetPieceAtLocation() {
        ChessBoard chessBoard = new ChessBoard();
        assertTrue(chessBoard.getPieceAtLocation(new Location(1, 1)) instanceof at.aau.se2.chessify.chessLogic.pieces.Pawn);
    }

    @Test
    public void testIsWithinBounds() {
        assertTrue((new ChessBoard()).isWithinBounds(1, 1));
        assertFalse((new ChessBoard()).isWithinBounds(8, 1));
        assertFalse((new ChessBoard()).isWithinBounds(-1, 1));
        assertFalse((new ChessBoard()).isWithinBounds(1, 8));
        assertFalse((new ChessBoard()).isWithinBounds(1, -1));
    }

    @Test
    public void testIsWithinBounds2() {
        ChessBoard chessBoard = new ChessBoard();
        assertTrue(chessBoard.isWithinBounds(new Location(1, 1)));
    }

    @Test
    public void testIsWithinBounds3() {
        ChessBoard chessBoard = new ChessBoard();
        assertFalse(chessBoard.isWithinBounds(new Location(8, 1)));
    }

    @Test
    public void testIsWithinBounds4() {
        ChessBoard chessBoard = new ChessBoard();
        assertFalse(chessBoard.isWithinBounds(new Location(-1, 1)));
    }

    @Test
    public void testIsWithinBounds5() {
        ChessBoard chessBoard = new ChessBoard();
        assertFalse(chessBoard.isWithinBounds(new Location(1, 8)));
    }

    @Test
    public void testIsWithinBounds6() {
        ChessBoard chessBoard = new ChessBoard();
        assertFalse(chessBoard.isWithinBounds(new Location(1, -1)));
    }

    @Test
    public void testTransformationAfterMove(){
        ChessBoard chessBoard = new ChessBoard();
        ChessPiece[][] manuallyGeneratedBoard = setupTransformationTest();
        chessBoard.setGameBoard(manuallyGeneratedBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = setupTransformationTestResult();



    }

    public ChessPiece[][] setupTransformationTest(){
        ChessPiece[][] manuallyGeneratedBoard = new ChessPiece[8][8];

        manuallyGeneratedBoard[1][3]=new Pawn(PieceColour.WHITE);
        manuallyGeneratedBoard[0][2]=new Knight(PieceColour.BLACK);

        manuallyGeneratedBoard[6][3]=new Pawn(PieceColour.WHITE);
        manuallyGeneratedBoard[7][2]=new Rook(PieceColour.BLACK);

        return manuallyGeneratedBoard;
    }

    public ChessPiece[][] setupTransformationTestResult(){
        ChessPiece[][] manuallyGeneratedResultBoard = new ChessPiece[8][8];

        manuallyGeneratedResultBoard[0][3]=new Queen(PieceColour.WHITE);
        manuallyGeneratedResultBoard[0][2]=new Knight(PieceColour.BLACK);

        manuallyGeneratedResultBoard[7][3]=new Queen(PieceColour.WHITE);
        manuallyGeneratedResultBoard[7][2]=new Rook(PieceColour.BLACK);

        return manuallyGeneratedResultBoard;
    }

}