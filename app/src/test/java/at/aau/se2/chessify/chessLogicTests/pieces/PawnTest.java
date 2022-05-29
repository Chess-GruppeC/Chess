package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import at.aau.se2.chessify.chessLogic.board.*;
import at.aau.se2.chessify.chessLogic.pieces.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PawnTest {
    private Pawn testPawn;
    private ChessBoard testChessBoard;
    private ChessPiece[][] manuallyGeneratedTestBoard;

    @Before
    public void initializeTestChessBoard(){
        testChessBoard=new ChessBoard();
        manuallyGeneratedTestBoard=new ChessPiece[8][8];
    }

    @Test
    public void getLegalMovesPawnBLACKTest(){
        //setup board
        testChessBoard=initializeTestChessboardPawnBLACK();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesPawnBLACK();
        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardPawnBLACK(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.BLACK);
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[3][4]=testPawn;

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesPawnBLACK(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,4));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesPawnBLACKTestEnemy(){
        //setup board
        testChessBoard=initializeTestChessboardPawnBLACKEnemy();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesPawnBLACKEnemy();
        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardPawnBLACKEnemy(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testPawn;
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[4][3]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[4][5]=new Pawn(PieceColour.WHITE);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesPawnBLACKEnemy(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));
        possibleMovesArray.add(new Location(4,5));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesPawnBLACKTestAlly(){
        //setup board
        testChessBoard=initializeTestChessboardPawnBLACKAlly();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesPawnBLACKAlly();
        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardPawnBLACKAlly(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testPawn;
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[4][3]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[4][5]=new Pawn(PieceColour.BLACK);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesPawnBLACKAlly(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        //empty because no moves are possible

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesPawnWHITETest(){
        //setup board
        testChessBoard=initializeTestChessboardPawnWHITE();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesPawnWHITE();
        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardPawnWHITE(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.WHITE);
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[3][4]=testPawn;

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesPawnWHITE(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesPawnWHITETestEnemy(){
        //setup board
        testChessBoard=initializeTestChessboardPawnWHITEEnemy();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesPawnWHITEEnemy();
        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardPawnWHITEEnemy(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][4]=testPawn;
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[2][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[2][3]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[2][5]=new Pawn(PieceColour.BLACK);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesPawnWHITEEnemy(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,3));
        possibleMovesArray.add(new Location(2,5));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesPawnWHITETestAlly(){
        //setup board
        testChessBoard=initializeTestChessboardPawnWHITEAlly();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesPawnWHITEAlly();
        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardPawnWHITEAlly(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][4]=testPawn;
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[2][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[2][3]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[2][5]=new Pawn(PieceColour.WHITE);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesPawnWHITEAlly(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        //empty because no moves are possible

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesPawnBLACKTestNotMoved(){
        //setup board
        testChessBoard=initializeTestChessboardPawnBLACKNotMoved();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesPawnBLACKNotMoved();
        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardPawnBLACKNotMoved(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testPawn;

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesPawnBLACKNotMoved(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,4));
        possibleMovesArray.add(new Location(5,4));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesPawnWHITETestNotMoved(){
        //setup board
        testChessBoard=initializeTestChessboardPawnWHITENotMoved();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesPawnWHITENotMoved();
        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardPawnWHITENotMoved(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][4]=testPawn;

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesPawnWHITENotMoved(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(1,4));

        return possibleMovesArray;
    }

}