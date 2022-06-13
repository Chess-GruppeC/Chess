package at.aau.se2.chessify.chessLogic.pieces;

import at.aau.se2.chessify.chessLogic.board.ChessBoard;
import at.aau.se2.chessify.chessLogic.board.Location;

import java.util.ArrayList;

public class Knight extends ChessPiece implements ChessMoveInterface{

    public Knight() {}

    public Knight(PieceColour colour) {
        this.pieceValue=3;
        this.colour=colour;
    }

    @Override
    public ArrayList<Location> getLegalMoves(ChessBoard board) {
        ArrayList<Location> legalMoveTargetList = new ArrayList<>();

        Location pieceLocation=board.getLocationOf(this);
        int row=pieceLocation.getRow();
        int column=pieceLocation.getColumn();
        int i;
        int j;

        i=row+1;
        j=column+2;
        if(board.isWithinBounds(i, j)){
            Location loc = new Location(i,j);
            addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);
        }

        i=row+2;
        j=column+1;
        if(board.isWithinBounds(i, j)){
            Location loc = new Location(i,j);
            addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);
        }

        i=row+2;
        j=column-1;
        if(board.isWithinBounds(i, j)){
            Location loc = new Location(i,j);
            addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);
        }

        i=row+1;
        j=column-2;
        if(board.isWithinBounds(i, j)){
            Location loc = new Location(i,j);
            addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);
        }

        i=row-1;
        j=column-2;
        if(board.isWithinBounds(i, j)){
            Location loc = new Location(i,j);
            addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);
        }

        i=row-2;
        j=column-1;
        if(board.isWithinBounds(i, j)){
            Location loc = new Location(i,j);
            addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);
        }
        i=row-2;
        j=column+1;
        if(board.isWithinBounds(i, j)){
            Location loc = new Location(i,j);
            addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);
        }

        i=row-1;
        j=column+2;
        if(board.isWithinBounds(i, j)){
            Location loc = new Location(i,j);
            addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);
        }

        return legalMoveTargetList;
    }
}
