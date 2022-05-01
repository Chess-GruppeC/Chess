package at.aau.se2.chessify.chessLogicTests.pieces;

import static org.junit.Assert.assertEquals;

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

        initializeTestChessboardKnight();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(1,3));
        possibleMovesArray.add(new Location(2,2));
        possibleMovesArray.add(new Location(1,5));
        possibleMovesArray.add(new Location(2,6));

        possibleMovesArray.add(new Location(4,2));
        possibleMovesArray.add(new Location(5,3));
        possibleMovesArray.add(new Location(5,5));
        possibleMovesArray.add(new Location(4,6));


        ArrayList<Location> legalMovesMethodArray = testKnight.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }
    }

    private void initializeTestChessboardKnight(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKnight= new Knight(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKnight;

    }

    @Test
    public void getLegalMovesKnightTestEnemy(){

        initializeTestChessboardKnightEnemy();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();

        possibleMovesArray.add(new Location(1,3));
        possibleMovesArray.add(new Location(2,2));
        possibleMovesArray.add(new Location(1,5));
        possibleMovesArray.add(new Location(2,6));

        possibleMovesArray.add(new Location(4,2));
        possibleMovesArray.add(new Location(5,3));
        possibleMovesArray.add(new Location(5,5));
        possibleMovesArray.add(new Location(4,6));


        ArrayList<Location> legalMovesMethodArray = testKnight.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardKnightEnemy(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKnight= new Knight(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKnight;
        testChessBoard.getGameBoard()[1][3]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[4][6]=new Pawn(PieceColour.WHITE);

        testChessBoard.getGameBoard()[2][6]=new Pawn(PieceColour.WHITE);
        testChessBoard.getGameBoard()[5][5]=new Pawn(PieceColour.WHITE);
    }

    @Test
    public void getLegalMovesKnightTestAlly(){

        initializeTestChessboardKnightAlly();
        ArrayList<Location> possibleMovesArray = new ArrayList<>();


        possibleMovesArray.add(new Location(2,2));
        possibleMovesArray.add(new Location(1,5));


        possibleMovesArray.add(new Location(4,2));
        possibleMovesArray.add(new Location(5,3));


        ArrayList<Location> legalMovesMethodArray = testKnight.getLegalMoves(testChessBoard);
        assertEquals(possibleMovesArray.size(), legalMovesMethodArray.size());

        for(Location loc : possibleMovesArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(legalMovesMethodArray));
        }
        for(Location loc : legalMovesMethodArray){
            assertEquals(true, loc.checkIfLocationIsPartOfList(possibleMovesArray));
        }

    }

    private void initializeTestChessboardKnightAlly(){
        testChessBoard=new ChessBoard();
        testChessBoard.setGameBoard(manuallyGeneratedTestBoard);
        testKnight= new Knight(PieceColour.BLACK);
        testChessBoard.getGameBoard()[3][4]=testKnight;
        testChessBoard.getGameBoard()[1][3]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[4][6]=new Pawn(PieceColour.BLACK);

        testChessBoard.getGameBoard()[2][6]=new Pawn(PieceColour.BLACK);
        testChessBoard.getGameBoard()[5][5]=new Pawn(PieceColour.BLACK);
    }

}