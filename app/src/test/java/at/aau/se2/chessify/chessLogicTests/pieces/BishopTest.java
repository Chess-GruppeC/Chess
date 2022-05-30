package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import at.aau.se2.chessify.chessLogic.board.*;
import at.aau.se2.chessify.chessLogic.pieces.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BishopTest {
    private Bishop testBishop;
    private ChessBoard testChessBoard;
    private ChessPiece[][] manuallyGeneratedTestBoard;

    @Before
    public void initializeTestChessBoard(){
        testChessBoard=new ChessBoard();
        manuallyGeneratedTestBoard=new ChessPiece[8][8];
    }

    @Test
    public void getLegalMovesBishopTest(){
        //setup board
        testChessBoard=initializeTestChessboardBishop();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesBishop();
        ArrayList<Location> legalMovesMethodArray = testBishop.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private ChessBoard initializeTestChessboardBishop(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testBishop= new Bishop(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testBishop;
        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesBishop(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

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
    public void getLegalMovesBishopTestEnemy(){
        //setup board
        testChessBoard=initializeTestChessboardBishopEnemy();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesBishopEnemy();
        ArrayList<Location> legalMovesMethodArray = testBishop.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardBishopEnemy(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testBishop= new Bishop(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testBishop;
        testChessBoard.getGameBoard()[1][2]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[0][7]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[4][5]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[5][2]=new Pawn(PieceColour.WHITE);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesBishopEnemy(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

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
    public void getLegalMovesBishopTestAlly(){
        //setup board
        testChessBoard=initializeTestChessboardBishopAlly();

        //get locations manually and with method
        ArrayList<Location> possibleMovesArray = setLegalMovesBishopAlly();
        ArrayList<Location> legalMovesMethodArray = testBishop.getLegalMoves(testChessBoard);

        //compare location lists
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());
        for(Location loc : possibleMovesArray){
            assertTrue(loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertTrue(loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private ChessBoard initializeTestChessboardBishopAlly(){
        ChessBoard testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testBishop= new Bishop(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testBishop;
        testChessBoard.getGameBoard()[1][2]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[0][7]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[4][5]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[5][2]=new Pawn(PieceColour.BLACK);

        return testChessBoard;
    }

    private ArrayList<Location> setLegalMovesBishopAlly(){
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(4,3));

        possibleMovesArray.add(new Location(2,5));
        possibleMovesArray.add(new Location(1,6));

        possibleMovesArray.add(new Location(2,3));

        return possibleMovesArray;
    }

}
