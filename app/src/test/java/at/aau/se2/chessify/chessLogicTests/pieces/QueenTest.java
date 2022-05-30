package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import at.aau.se2.chessify.chessLogic.board.*;
import at.aau.se2.chessify.chessLogic.pieces.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class QueenTest {
    private Queen testQueen;
    private ChessBoard testChessBoard;
    private ChessPiece[][] manuallyGeneratedTestBoard;

    @Before
    public void initializeTestChessBoard(){
        testChessBoard=new ChessBoard();
        manuallyGeneratedTestBoard=new ChessPiece[8][8];
    }

    @Test
    public void getLegalMovesQueenTest(){
        //setup board
        testChessBoard=initializeTestChessboardQueen();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesQueen();
        ArrayList<Location> legalMovesMethodArray = testQueen.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardQueen(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testQueen= new Queen(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testQueen;

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesQueen(){
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


        possibleMovesArray.add(new Location(4,3));
        possibleMovesArray.add(new Location(5,2));
        possibleMovesArray.add(new Location(6,1));
        possibleMovesArray.add(new Location(7,0));

        possibleMovesArray.add(new Location(2,5));
        possibleMovesArray.add(new Location(1,6));
        possibleMovesArray.add(new Location(0,7));

        possibleMovesArray.add(new Location(2,3));
        possibleMovesArray.add(new Location(1,2));
        possibleMovesArray.add(new Location(0,1));

        possibleMovesArray.add(new Location(4,5));
        possibleMovesArray.add(new Location(5,6));
        possibleMovesArray.add(new Location(6,7));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesQueenTestEnemy(){
        //setup board
        testChessBoard=initializeTestChessboardQueenEnemy();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesQueenEnemy();
        ArrayList<Location> legalMovesMethodArray = testQueen.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardQueenEnemy(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testQueen= new Queen(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testQueen;
        testChessBoard.getGameBoard()[1][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][0]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][6]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[1][2]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[0][7]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[4][5]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[5][2]=new Pawn(PieceColour.WHITE);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesQueenEnemy(){
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


        possibleMovesArray.add(new Location(4,3));
        possibleMovesArray.add(new Location(5,2));

        possibleMovesArray.add(new Location(2,5));
        possibleMovesArray.add(new Location(1,6));
        possibleMovesArray.add(new Location(0,7));

        possibleMovesArray.add(new Location(2,3));
        possibleMovesArray.add(new Location(1,2));


        possibleMovesArray.add(new Location(4,5));

        return possibleMovesArray;
    }

    @Test
    public void getLegalMovesQueenTestAlly(){
        //setup board
        testChessBoard=initializeTestChessboardQueenAlly();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesQueenAlly();
        ArrayList<Location> legalMovesMethodArray = testQueen.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardQueenAlly(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testQueen= new Queen(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testQueen;
        testChessBoard.getGameBoard()[1][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][0]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][6]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[1][2]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[0][7]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[4][5]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[5][2]=new Pawn(PieceColour.BLACK);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesQueenAlly(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));

        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,2));
        possibleMovesArray.add(new Location(3,1));

        possibleMovesArray.add(new Location(3,5));


        possibleMovesArray.add(new Location(4,3));

        possibleMovesArray.add(new Location(2,5));
        possibleMovesArray.add(new Location(1,6));

        possibleMovesArray.add(new Location(2,3));

        return possibleMovesArray;
    }

}
