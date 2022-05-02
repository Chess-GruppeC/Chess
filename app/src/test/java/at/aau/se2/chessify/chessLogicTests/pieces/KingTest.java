package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;

import at.aau.se2.chessify.chessLogic.board.*;
import at.aau.se2.chessify.chessLogic.pieces.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class KingTest {
    private King testKing;
    private ChessBoard testChessBoard;
    private ChessPiece[][] manuallyGeneratedTestBoard;

    @Before
    public void initializeTestChessBoard(){
        testChessBoard=new ChessBoard();
        manuallyGeneratedTestBoard=new ChessPiece[8][8];
    }

    @Test
    public void getLegalMovesKingTest(){

        initializeTestChessboardKing();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));
        possibleMovesArray.add(new Location(2,5));
        possibleMovesArray.add(new Location(2,3));
        possibleMovesArray.add(new Location(4,5));

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,5));
        possibleMovesArray.add(new Location(4,4));


        ArrayList<Location> legalMovesMethodArray = testKing.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private void initializeTestChessboardKing(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKing= new King(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKing;

    }

    @Test
    public void getLegalMovesKingTestEnemy(){

        initializeTestChessboardKingEnemy();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));
        possibleMovesArray.add(new Location(2,5));
        possibleMovesArray.add(new Location(2,3));
        possibleMovesArray.add(new Location(4,5));

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,5));
        possibleMovesArray.add(new Location(4,4));


        ArrayList<Location> legalMovesMethodArray = testKing.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardKingEnemy(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKing= new King(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKing;
        testChessBoard.getGameBoard()[2][3]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[2][5]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[5][2]=new Pawn(PieceColour.WHITE);
    }

    @Test
    public void getLegalMovesKingTestAlly(){

        initializeTestChessboardKingAlly();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));


        possibleMovesArray.add(new Location(4,5));

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,5));


        ArrayList<Location> legalMovesMethodArray = testKing.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardKingAlly(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKing= new King(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKing;
        testChessBoard.getGameBoard()[2][3]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[2][5]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[5][2]=new Pawn(PieceColour.BLACK);
    }

}