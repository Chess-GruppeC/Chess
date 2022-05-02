package at.aau.se2.chessify.chessLogicTests.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import at.aau.se2.chessify.chessLogic.board.*;
import at.aau.se2.chessify.chessLogic.pieces.*;

import org.junit.Before;
import org.junit.Test;

public class ChessBoardTest {

    private ChessBoard testChessBoard;
    private ChessPiece[][] manuallyGeneratedTestBoard= new ChessPiece[8][8];

    @Test
    public void testInitializingChessBoard(){
        manuallyGeneratedTestBoard[0][0]=new Rook(PieceColour.BLACK);
        manuallyGeneratedTestBoard[0][7]=new Rook(PieceColour.BLACK);

        manuallyGeneratedTestBoard[0][1]=new Knight(PieceColour.BLACK);
        manuallyGeneratedTestBoard[0][6]=new Knight(PieceColour.BLACK);

        manuallyGeneratedTestBoard[0][2]=new Bishop(PieceColour.BLACK);
        manuallyGeneratedTestBoard[0][5]=new Bishop(PieceColour.BLACK);

        manuallyGeneratedTestBoard[0][3]=new Queen(PieceColour.BLACK);
        manuallyGeneratedTestBoard[0][4]=new King(PieceColour.BLACK);

        for(int i=0; i<8; i++){
            manuallyGeneratedTestBoard[1][i]=new Pawn(PieceColour.BLACK);
        }

        manuallyGeneratedTestBoard[7][0]=new Rook(PieceColour.WHITE);
        manuallyGeneratedTestBoard[7][7]=new Rook(PieceColour.WHITE);

        manuallyGeneratedTestBoard[7][1]=new Knight(PieceColour.WHITE);
        manuallyGeneratedTestBoard[7][6]=new Knight(PieceColour.WHITE);

        manuallyGeneratedTestBoard[7][2]=new Bishop(PieceColour.WHITE);
        manuallyGeneratedTestBoard[7][5]=new Bishop(PieceColour.WHITE);

        manuallyGeneratedTestBoard[7][3]=new Queen(PieceColour.WHITE);
        manuallyGeneratedTestBoard[7][4]=new King(PieceColour.WHITE);

        for(int i=0; i<8; i++){
            manuallyGeneratedTestBoard[6][i]=new Pawn(PieceColour.WHITE);
        }



        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){

                if(testChessBoard.getGameBoard()[i][j]!=null
                && manuallyGeneratedTestBoard[i][j]!=null){

                    assertEquals(manuallyGeneratedTestBoard[i][j].getClass(), testChessBoard.getGameBoard()[i][j].getClass());
                    assertEquals(manuallyGeneratedTestBoard[i][j].getColour(), testChessBoard.getGameBoard()[i][j].getColour());

                }else {

                    assertNull(testChessBoard.getGameBoard()[i][j]);
                    assertNull(manuallyGeneratedTestBoard[i][j]);

                }




            }
        }

    }

    @Before
    public void initializeTestChessBoard(){
        testChessBoard=new ChessBoard();
    }

}
