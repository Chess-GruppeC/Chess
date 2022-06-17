package at.aau.se2.chessify.chess_logic.pieces;

import at.aau.se2.chessify.chess_logic.board.ChessBoard;
import at.aau.se2.chessify.chess_logic.board.Location;

import java.util.ArrayList;

public class Queen extends ChessPiece implements ChessMoveInterface{

    public Queen() {
        // empty constructor needed for JSON parsing
    }

    public Queen(PieceColour colour) {
        this.colour=colour;
        this.pieceValue=9;
    }

    @Override
    public ArrayList<Location> getLegalMoves(ChessBoard board) {
        ArrayList<Location> legalMoveDiagonalList = ChessMoveInterface.super.getDiagonalMoves(board, this);
        ArrayList<Location> legalMoveHorrizontalAndVerticalList = ChessMoveInterface.super.getHorizontalAndVerticalMoves(board, this);

        ArrayList<Location> legalMoveTargetList=new ArrayList<>();
        legalMoveTargetList.addAll(legalMoveDiagonalList);
        legalMoveTargetList.addAll(legalMoveHorrizontalAndVerticalList);

        return legalMoveTargetList;
    }
}
