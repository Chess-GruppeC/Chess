package at.aau.se2.chessify.chessLogicTests.board;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import at.aau.se2.chessify.chess_logic.board.ChessBoard;
import at.aau.se2.chessify.chess_logic.board.Location;
import at.aau.se2.chessify.chess_logic.board.Move;
import at.aau.se2.chessify.chess_logic.pieces.Bishop;
import at.aau.se2.chessify.chess_logic.pieces.ChessPiece;
import at.aau.se2.chessify.chess_logic.pieces.Knight;
import at.aau.se2.chessify.chess_logic.pieces.Pawn;
import at.aau.se2.chessify.chess_logic.pieces.PieceColour;
import at.aau.se2.chessify.chess_logic.pieces.Queen;
import at.aau.se2.chessify.chess_logic.pieces.Rook;

public class PerformMoveOnBoardTest {
    ChessBoard chessBoard;

    /*@Test
    public void testConstructor() {
        ChessBoard actualChessBoard = new ChessBoard();
        actualChessBoard.setGameBoard(new ChessPiece[][]{new ChessPiece[]{new Bishop(PieceColour.BLACK)}});
        ChessPiece[][] expectedGameBoard = ChessBoard.gameboard;
        assertSame(expectedGameBoard, actualChessBoard.getGameBoard());
    }
    */

    @Before
    public void testInitialization(){
        chessBoard = new ChessBoard();
    }

    @Test
    public void testConstructor2() {
        assertEquals(8, (new ChessBoard()).getGameBoard().length);
    }

    @Test
    public void testPerformMoveOnBoard() throws IllegalArgumentException {
        chessBoard = new ChessBoard();
        Location from = new Location(1, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard2() throws IllegalArgumentException {
        chessBoard = new ChessBoard();
        Location from = new Location(8, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard3() throws IllegalArgumentException {
        chessBoard = new ChessBoard();
        Location from = new Location(0, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard4() throws IllegalArgumentException {
        chessBoard = new ChessBoard();
        Location from = new Location(-1, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard5() throws IllegalArgumentException {
        chessBoard = new ChessBoard();
        Location from = new Location(1, 8);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard6() throws IllegalArgumentException {
        chessBoard = new ChessBoard();
        Location from = new Location(1, -1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(1, 1))));
    }

    @Test
    public void testPerformMoveOnBoard7() throws IllegalArgumentException {
        chessBoard = new ChessBoard();
        Location from = new Location(1, 1);

        assertThrows(IllegalArgumentException.class,
                () -> chessBoard.performMoveOnBoard(new Move(from, new Location(8, 1))));
    }

    @Test
    public void testGetLocationOf() {
        chessBoard = new ChessBoard();
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
        chessBoard = new ChessBoard();
        assertTrue(chessBoard.getPieceAtLocation(new Location(1, 1)) instanceof at.aau.se2.chessify.chess_logic.pieces.Pawn);
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
        chessBoard = new ChessBoard();
        assertTrue(chessBoard.isWithinBounds(new Location(1, 1)));
    }

    @Test
    public void testIsWithinBounds3() {
        chessBoard = new ChessBoard();
        assertFalse(chessBoard.isWithinBounds(new Location(8, 1)));
    }

    @Test
    public void testIsWithinBounds4() {
        chessBoard = new ChessBoard();
        assertFalse(chessBoard.isWithinBounds(new Location(-1, 1)));
    }

    @Test
    public void testIsWithinBounds5() {
        chessBoard = new ChessBoard();
        assertFalse(chessBoard.isWithinBounds(new Location(1, 8)));
    }

    @Test
    public void testIsWithinBounds6() {
        chessBoard = new ChessBoard();
        assertFalse(chessBoard.isWithinBounds(new Location(1, -1)));
    }

    @Test
    public void testTransformationAfterMove(){
        chessBoard = new ChessBoard();
        ChessPiece[][] manuallyGeneratedBoard = setupTransformationTest();
        chessBoard.setGameBoard(manuallyGeneratedBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = setupTransformationTestResult();

        Move move = new Move (new Location(1,3),new Location(0,3));
        chessBoard.performMoveOnBoard(move);
        move = new Move (new Location(6,3),new Location(7,3));
        chessBoard.performMoveOnBoard(move);

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                Location loc = new Location(i,j);
                if(chessBoard.getPieceAtLocation(loc)!=null) {
                    assertEquals(chessBoard.getPieceAtLocation(loc).getClass(),
                            manuallyGeneratedResultBoard[i][j].getClass());
                    assertEquals(chessBoard.getPieceAtLocation(loc).getColour(),
                            manuallyGeneratedResultBoard[i][j].getColour());
                }
            }
        }

    }

    public ChessPiece[][] setupTransformationTest(){
        ChessPiece[][] manuallyGeneratedBoard = new ChessPiece[8][8];

        manuallyGeneratedBoard[1][3]=new Pawn(PieceColour.WHITE);
        manuallyGeneratedBoard[0][2]=new Knight(PieceColour.BLACK);

        manuallyGeneratedBoard[6][3]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[7][2]=new Rook(PieceColour.WHITE);

        return manuallyGeneratedBoard;
    }

    public ChessPiece[][] setupTransformationTestResult(){
        ChessPiece[][] manuallyGeneratedResultBoard = new ChessPiece[8][8];

        manuallyGeneratedResultBoard[0][3]=new Queen(PieceColour.WHITE);
        manuallyGeneratedResultBoard[0][2]=new Knight(PieceColour.BLACK);

        manuallyGeneratedResultBoard[7][3]=new Queen(PieceColour.BLACK);
        manuallyGeneratedResultBoard[7][2]=new Rook(PieceColour.WHITE);

        return manuallyGeneratedResultBoard;
    }

    public void compareBoardStates(ChessPiece[][] boardExpected, ChessPiece[][] boardActual){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                Location loc = new Location(i,j);
                if(boardExpected[i][j]!=null) {
                    assertEquals(boardExpected[i][j].getClass(),
                            boardActual[i][j].getClass());
                    assertEquals(boardExpected[i][j].getColour(),
                            boardActual[i][j].getColour());
                }
            }
        }
    }

    private ChessPiece[][] initializeBasicMoveSetup() {
        ChessPiece[][] manuallyGeneratedBoard = new ChessPiece[8][8];

        manuallyGeneratedBoard[0][1]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[0][4]=new Rook(PieceColour.BLACK);
        manuallyGeneratedBoard[2][5]=new Knight(PieceColour.WHITE);
        manuallyGeneratedBoard[0][7]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[2][2]=new Queen(PieceColour.BLACK);
        manuallyGeneratedBoard[3][4]=new Bishop(PieceColour.WHITE);

        return manuallyGeneratedBoard;
    }

    @Test
    public void performMoveTestBasicMoveCaptureQueen(){
        ChessPiece[][] manuallyGeneratedInitialBoard = initializeBasicMoveSetup();
        chessBoard.setGameBoard(manuallyGeneratedInitialBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = initializeBasicMoveResultQueen();
        chessBoard.performMoveOnBoard(new Move(new Location(2,2),new Location(2,5)));

        compareBoardStates(chessBoard.getGameBoard(), manuallyGeneratedResultBoard);
    }

    private ChessPiece[][] initializeBasicMoveResultQueen() {
        ChessPiece[][] manuallyGeneratedBoard = new ChessPiece[8][8];

        manuallyGeneratedBoard[0][1]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[0][4]=new Rook(PieceColour.BLACK);

        manuallyGeneratedBoard[0][7]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[2][5]=new Queen(PieceColour.BLACK);
        manuallyGeneratedBoard[3][4]=new Bishop(PieceColour.WHITE);

        return manuallyGeneratedBoard;
    }

    @Test
    public void performMoveTestIllegalMoveQueen(){
        ChessPiece[][] manuallyGeneratedInitialBoard = initializeBasicMoveSetup();
        chessBoard.setGameBoard(manuallyGeneratedInitialBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = initializeBasicMoveSetup();
        try {
            chessBoard.performMoveOnBoard(new Move(new Location(2, 2), new Location(0, 4)));
        }catch (IllegalArgumentException e){
            assertEquals("Please perform a legal move", e.getMessage());
        }

        compareBoardStates(chessBoard.getGameBoard(), manuallyGeneratedResultBoard);
    }

    @Test
    public void performMoveTestBasicMoveCaptureKnight(){
        ChessPiece[][] manuallyGeneratedInitialBoard = initializeBasicMoveSetup();
        chessBoard.setGameBoard(manuallyGeneratedInitialBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = initializeBasicMoveResultKnight();
        chessBoard.performMoveOnBoard(new Move(new Location(2,5),new Location(0,4)));

        compareBoardStates(chessBoard.getGameBoard(), manuallyGeneratedResultBoard);
    }

    private ChessPiece[][] initializeBasicMoveResultKnight() {
        ChessPiece[][] manuallyGeneratedBoard = new ChessPiece[8][8];

        manuallyGeneratedBoard[0][1]=new Pawn(PieceColour.BLACK);

        manuallyGeneratedBoard[0][4]=new Knight(PieceColour.WHITE);
        manuallyGeneratedBoard[0][7]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[2][2]=new Queen(PieceColour.BLACK);
        manuallyGeneratedBoard[3][4]=new Bishop(PieceColour.WHITE);

        return manuallyGeneratedBoard;
    }

    @Test
    public void performMoveTestIllegalMoveKnight(){
        ChessPiece[][] manuallyGeneratedInitialBoard = initializeBasicMoveSetup();
        chessBoard.setGameBoard(manuallyGeneratedInitialBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = initializeBasicMoveSetup();
        try {
            chessBoard.performMoveOnBoard(new Move(new Location(2, 5), new Location(3, 4)));
        }catch (IllegalArgumentException e){
            assertEquals("Please perform a legal move", e.getMessage());
        }

        compareBoardStates(chessBoard.getGameBoard(), manuallyGeneratedResultBoard);
    }

    @Test
    public void performMoveTestBasicMoveCaptureBishop(){
        ChessPiece[][] manuallyGeneratedInitialBoard = initializeBasicMoveSetup();
        chessBoard.setGameBoard(manuallyGeneratedInitialBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = initializeBasicMoveResultBishop();
        chessBoard.performMoveOnBoard(new Move(new Location(3,4),new Location(0,1)));

        compareBoardStates(chessBoard.getGameBoard(), manuallyGeneratedResultBoard);
    }

    private ChessPiece[][] initializeBasicMoveResultBishop() {
        ChessPiece[][] manuallyGeneratedBoard = new ChessPiece[8][8];


        manuallyGeneratedBoard[0][4]=new Rook(PieceColour.BLACK);
        manuallyGeneratedBoard[2][5]=new Knight(PieceColour.WHITE);
        manuallyGeneratedBoard[0][7]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[2][2]=new Queen(PieceColour.BLACK);
        manuallyGeneratedBoard[0][1]=new Bishop(PieceColour.WHITE);

        return manuallyGeneratedBoard;
    }

    @Test
    public void performMoveTestIllegalMoveBishop(){
        ChessPiece[][] manuallyGeneratedInitialBoard = initializeBasicMoveSetup();
        chessBoard.setGameBoard(manuallyGeneratedInitialBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = initializeBasicMoveSetup();
        try {
            chessBoard.performMoveOnBoard(new Move(new Location(3, 4), new Location(2, 5)));
        }catch (IllegalArgumentException e){
            assertEquals("Please perform a legal move", e.getMessage());
        }

        compareBoardStates(chessBoard.getGameBoard(), manuallyGeneratedResultBoard);
    }

    @Test
    public void performMoveTestBasicMoveCaptureRook(){
        ChessPiece[][] manuallyGeneratedInitialBoard = initializeBasicMoveSetup();
        chessBoard.setGameBoard(manuallyGeneratedInitialBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = initializeBasicMoveResultRook();
        chessBoard.performMoveOnBoard(new Move(new Location(0,4),new Location(3,4)));

        compareBoardStates(chessBoard.getGameBoard(), manuallyGeneratedResultBoard);
    }

    private ChessPiece[][] initializeBasicMoveResultRook() {
        ChessPiece[][] manuallyGeneratedBoard = new ChessPiece[8][8];

        manuallyGeneratedBoard[0][1]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[3][4]=new Rook(PieceColour.BLACK);
        manuallyGeneratedBoard[2][5]=new Knight(PieceColour.WHITE);
        manuallyGeneratedBoard[0][7]=new Pawn(PieceColour.BLACK);
        manuallyGeneratedBoard[2][2]=new Queen(PieceColour.BLACK);


        return manuallyGeneratedBoard;
    }

    @Test
    public void performMoveTestIllegalMoveRook(){
        ChessPiece[][] manuallyGeneratedInitialBoard = initializeBasicMoveSetup();
        chessBoard.setGameBoard(manuallyGeneratedInitialBoard);

        ChessPiece[][] manuallyGeneratedResultBoard = initializeBasicMoveSetup();
        try {
            chessBoard.performMoveOnBoard(new Move(new Location(0, 4), new Location(0, 1)));
        }catch (IllegalArgumentException e){
            assertEquals("Please perform a legal move", e.getMessage());
        }

        compareBoardStates(chessBoard.getGameBoard(), manuallyGeneratedResultBoard);
    }

}