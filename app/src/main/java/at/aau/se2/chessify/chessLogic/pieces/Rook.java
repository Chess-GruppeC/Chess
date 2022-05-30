package at.aau.se2.chessify.chessLogic.pieces;

import at.aau.se2.chessify.chessLogic.board.ChessBoard;
import at.aau.se2.chessify.chessLogic.board.Location;

import java.util.ArrayList;

public class Rook extends ChessPiece implements ChessMoveInterface{

    public Rook(PieceColour colour) {
        this.pieceValue=5;
        this.colour=colour;
    }

    @Override
    public ArrayList<Location> getLegalMoves(ChessBoard board) {
        ArrayList<Location> legalMoveTargetList = ChessMoveInterface.super.getHorizontalAndVerticalMoves(board, this);

        return legalMoveTargetList;
    }
}
