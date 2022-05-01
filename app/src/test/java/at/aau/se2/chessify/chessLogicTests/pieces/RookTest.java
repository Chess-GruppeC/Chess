package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;

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

        initializeTestChessboardRook();
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


        ArrayList<Location> legalMovesMethodArray = testRook.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private void initializeTestChessboardRook(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testRook= new Rook(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testRook;

    }

    @Test
    public void getLegalMovesRookTestEnemy(){

        initializeTestChessboardRookEnemy();
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


        ArrayList<Location> legalMovesMethodArray = testRook.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardRookEnemy(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testRook= new Rook(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testRook;
        testChessBoard.getGameBoard()[1][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][0]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[3][6]=new Pawn(PieceColour.WHITE);
    }

    @Test
    public void getLegalMovesRookTestAlly(){

        initializeTestChessboardRookAlly();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(2,4));

        possibleMovesArray.add(new Location(3,3));
        possibleMovesArray.add(new Location(3,2));
        possibleMovesArray.add(new Location(3,1));

        possibleMovesArray.add(new Location(3,5));


        ArrayList<Location> legalMovesMethodArray = testRook.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardRookAlly(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testRook= new Rook(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testRook;
        testChessBoard.getGameBoard()[1][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][0]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[4][4]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][6]=new Pawn(PieceColour.BLACK);
    }

}
