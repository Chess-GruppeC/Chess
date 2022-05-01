package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;

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

        initializeTestChessboardQueen();
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


        ArrayList<Location> legalMovesMethodArray = testQueen.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private void initializeTestChessboardQueen(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testQueen= new Queen(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testQueen;

    }

    @Test
    public void getLegalMovesQueenTestEnemy(){

        initializeTestChessboardQueenEnemy();
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


        ArrayList<Location> legalMovesMethodArray = testQueen.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardQueenEnemy(){
        testChessBoard=new ChessBoard();
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
    }

    @Test
    public void getLegalMovesQueenTestAlly(){

        initializeTestChessboardQueenAlly();
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


        ArrayList<Location> legalMovesMethodArray = testQueen.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardQueenAlly(){
        testChessBoard=new ChessBoard();
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
    }

}
