package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;

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

        initializeTestChessboardPawnBLACK();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,4));

        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private void initializeTestChessboardPawnBLACK(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.BLACK);
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[3][4]=testPawn;

    }

    @Test
    public void getLegalMovesPawnBLACKTestEnemy(){

        initializeTestChessboardPawnBLACKEnemy();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));
        possibleMovesArray.add(new Location(4,5));


        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardPawnBLACKEnemy(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testPawn;
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[4][3]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[4][5]=new Pawn(PieceColour.WHITE);
    }

    @Test
    public void getLegalMovesPawnBLACKTestAlly(){

        initializeTestChessboardPawnBLACKAlly();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardPawnBLACKAlly(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testPawn;
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[4][3]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[4][5]=new Pawn(PieceColour.BLACK);
    }

    @Test
    public void getLegalMovesPawnWHITETest(){

        initializeTestChessboardPawnWHITE();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));

        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private void initializeTestChessboardPawnWHITE(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.WHITE);
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[3][4]=testPawn;

    }

    @Test
    public void getLegalMovesPawnWHITETestEnemy(){

        initializeTestChessboardPawnWHITEEnemy();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,3));
        possibleMovesArray.add(new Location(2,5));


        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardPawnWHITEEnemy(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][4]=testPawn;
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[2][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[2][3]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[2][5]=new Pawn(PieceColour.BLACK);
    }

    @Test
    public void getLegalMovesPawnWHITETestAlly(){

        initializeTestChessboardPawnWHITEAlly();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardPawnWHITEAlly(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][4]=testPawn;
        testPawn.setMoved(true);
        testChessBoard.getGameBoard()[2][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[2][3]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[2][5]=new Pawn(PieceColour.WHITE);
    }

    @Test
    public void getLegalMovesPawnBLACKTestNotMoved(){

        initializeTestChessboardPawnBLACKNotMoved();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,4));
        possibleMovesArray.add(new Location(5,4));

        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private void initializeTestChessboardPawnBLACKNotMoved(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testPawn;

    }

    @Test
    public void getLegalMovesPawnWHITETestNotMoved(){

        initializeTestChessboardPawnWHITENotMoved();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(1,4));

        ArrayList<Location> legalMovesMethodArray = testPawn.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private void initializeTestChessboardPawnWHITENotMoved(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testPawn= new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][4]=testPawn;

    }

}