package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import at.aau.se2.chessify.chessLogic.board.*;
import at.aau.se2.chessify.chessLogic.pieces.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RookTest {
    private Rook testRook;
    private ChessBoard testChessBoard;
    private ChessPiece[][] manuallyGeneratedTestBoard;

    @Before
    public void initializeTestChessBoard(){
        testChessBoard=new ChessBoard();
        manuallyGeneratedTestBoard=new ChessPiece[8][8];
    }

    @Test
    public void getLegalMovesRookTest(){
        //setup board
        testChessBoard=initializeTestChessboardRook();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesRook();
        ArrayList<Location> legalMovesMethodArray = testRook.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardRook(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testRook= new Rook(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testRook;

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesRook(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(1,4));
        possibleMovesArray.add(new Location(0,4));

        possibleMovesArray.add(new Location(4,4));
        possibleMovesArray.add(new Location(5,4));
        possibleMovesArray.add(new Location(6,4));
        possibleMovesArray.add(new Location(7,4));

        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,2));
        possibleMovesArray.add(new Location(3,1));
        possibleMovesArray.add(new Location(3,0));

        possibleMovesArray.add(new Location(3,5));
        possibleMovesArray.add(new Location(3,6));
        possibleMovesArray.add(new Location(3,7));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesRookTestEnemy(){
        //setup board
        testChessBoard=initializeTestChessboardRookEnemy();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesRookEnemy();
        ArrayList<Location> legalMovesMethodArray = testRook.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardRookEnemy(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testRook= new Rook(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testRook;
        testChessBoard.getGameBoard()[1][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][0]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][6]=new Pawn(PieceColour.WHITE);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesRookEnemy(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));
        possibleMovesArray.add(new Location(1,4));

        possibleMovesArray.add(new Location(4,4));

        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,2));
        possibleMovesArray.add(new Location(3,1));
        possibleMovesArray.add(new Location(3,0));

        possibleMovesArray.add(new Location(3,5));
        possibleMovesArray.add(new Location(3,6));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesRookTestAlly(){
        //setup board
        testChessBoard=initializeTestChessboardRookAlly();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesRookAlly();
        ArrayList<Location> legalMovesMethodArray = testRook.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardRookAlly(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testRook= new Rook(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testRook;
        testChessBoard.getGameBoard()[1][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][0]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][6]=new Pawn(PieceColour.BLACK);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesRookAlly(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));

        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,2));
        possibleMovesArray.add(new Location(3,1));

        possibleMovesArray.add(new Location(3,5));

        return possibleMovesArray;
    }

}
