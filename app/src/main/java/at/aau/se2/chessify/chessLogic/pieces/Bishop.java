package at.aau.se2.chessify.chessLogic.pieces;

import at.aau.se2.chessify.chessLogic.board.ChessBoard;
import at.aau.se2.chessify.chessLogic.board.Location;

import java.util.ArrayList;

public class Bishop extends ChessPiece implements ChessMoveInterface{

    public Bishop(PieceColour colour) {
        this.pieceValue=3;
        this.colour=colour;
    }

    @Override
    public ArrayList<Location> getLegalMoves(ChessBoard board) {
        ArrayList<Location> legalMoveTargetList = ChessMoveInterface.super.getDiagonalMoves(board, this);

        return legalMoveTargetList;
    }
}
