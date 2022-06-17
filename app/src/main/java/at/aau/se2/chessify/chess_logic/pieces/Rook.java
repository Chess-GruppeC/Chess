package at.aau.se2.chessify.chess_logic.pieces;

import at.aau.se2.chessify.chess_logic.board.ChessBoard;
import at.aau.se2.chessify.chess_logic.board.Location;

import java.util.ArrayList;

public class Rook extends ChessPiece implements ChessMoveInterface{

    public Rook() {}

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
