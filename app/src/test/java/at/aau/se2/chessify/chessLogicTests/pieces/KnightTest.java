package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import at.aau.se2.chessify.chessLogic.board.*;
import at.aau.se2.chessify.chessLogic.pieces.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class KnightTest {
    private Knight testKnight;
    private ChessBoard testChessBoard;
    private ChessPiece[][] manuallyGeneratedTestBoard;

    @Before
    public void initializeTestChessBoard(){
        testChessBoard=new ChessBoard();
        manuallyGeneratedTestBoard=new ChessPiece[8][8];
    }

    @Test
    public void getLegalMovesKnightTest(){
        //setup board
        testChessBoard=initializeTestChessboardKnight();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesKnight();
        ArrayList<Location> legalMovesMethodArray = testKnight.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardKnight(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKnight= new Knight(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKnight;

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesKnight(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(1,3));
        possibleMovesArray.add(new Location(2,2));
        possibleMovesArray.add(new Location(1,5));
        possibleMovesArray.add(new Location(2,6));

        possibleMovesArray.add(new Location(4,2));
        possibleMovesArray.add(new Location(5,3));
        possibleMovesArray.add(new Location(5,5));
        possibleMovesArray.add(new Location(4,6));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesKnightTestEnemy(){
        //setup board
        testChessBoard=initializeTestChessboardKnightEnemy();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesKnightEnemy();
        ArrayList<Location> legalMovesMethodArray = testKnight.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardKnightEnemy(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKnight= new Knight(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKnight;
        testChessBoard.getGameBoard()[1][3]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[4][6]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[2][6]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[5][5]=new Pawn(PieceColour.WHITE);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesKnightEnemy(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(1,3));
        possibleMovesArray.add(new Location(2,2));
        possibleMovesArray.add(new Location(1,5));
        possibleMovesArray.add(new Location(2,6));

        possibleMovesArray.add(new Location(4,2));
        possibleMovesArray.add(new Location(5,3));
        possibleMovesArray.add(new Location(5,5));
        possibleMovesArray.add(new Location(4,6));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesKnightTestAlly(){
        //setup board
        testChessBoard=initializeTestChessboardKnightAlly();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesKnightAlly();
        ArrayList<Location> legalMovesMethodArray = testKnight.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardKnightAlly(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKnight= new Knight(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKnight;
        testChessBoard.getGameBoard()[1][3]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[4][6]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[2][6]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[5][5]=new Pawn(PieceColour.BLACK);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesKnightAlly(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();


        possibleMovesArray.add(new Location(2,2));
        possibleMovesArray.add(new Location(1,5));


        possibleMovesArray.add(new Location(4,2));
        possibleMovesArray.add(new Location(5,3));

        return possibleMovesArray;
    }

}