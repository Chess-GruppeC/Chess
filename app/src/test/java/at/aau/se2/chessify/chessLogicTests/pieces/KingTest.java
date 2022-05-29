package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        //setup board
        testChessBoard=initializeTestChessboardKing();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesKing();
        ArrayList<Location> legalMovesMethodArray = testKing.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardKing(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKing= new King(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKing;

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesKing(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));
        possibleMovesArray.add(new Location(2,5));
        possibleMovesArray.add(new Location(2,3));
        possibleMovesArray.add(new Location(4,5));

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,5));
        possibleMovesArray.add(new Location(4,4));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesKingTestEnemy(){
        //setup board
        testChessBoard=initializeTestChessboardKingEnemy();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesKingEnemy();
        ArrayList<Location> legalMovesMethodArray = testKing.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardKingEnemy(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKing= new King(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKing;
        testChessBoard.getGameBoard()[2][3]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[2][5]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[5][2]=new Pawn(PieceColour.WHITE);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesKingEnemy(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));
        possibleMovesArray.add(new Location(2,5));
        possibleMovesArray.add(new Location(2,3));
        possibleMovesArray.add(new Location(4,5));

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,5));
        possibleMovesArray.add(new Location(4,4));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesKingTestAlly(){
        //setup board
        testChessBoard=initializeTestChessboardKingAlly();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesKingAlly();
        ArrayList<Location> legalMovesMethodArray = testKing.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardKingAlly(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKing= new King(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKing;
        testChessBoard.getGameBoard()[2][3]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[2][5]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[5][2]=new Pawn(PieceColour.BLACK);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesKingAlly(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));


        possibleMovesArray.add(new Location(4,5));

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,5));

        return possibleMovesArray;
    }

}