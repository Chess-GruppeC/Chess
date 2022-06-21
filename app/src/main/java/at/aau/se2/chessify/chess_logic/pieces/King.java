package at.aau.se2.chessify.chess_logic.pieces;

import at.aau.se2.chessify.chess_logic.board.ChessBoard;
import at.aau.se2.chessify.chess_logic.board.Location;

import java.util.ArrayList;

public class King extends ChessPiece implements ChessMoveInterface{

    public King() {
        // empty constructor needed for JSON parsing
    }

    public King(PieceColour colour) {
        this.pieceValue=1000;
        this.colour=colour;
    }

    @Override
    public ArrayList<Location> getLegalMoves(ChessBoard board) {
        ArrayList<Location> legalMoveTargetList = new ArrayList<>();

        Location pieceLocation=board.getLocationOf(this);
        int row=pieceLocation.getRow();
        int column=pieceLocation.getColumn();

        //check location around the king
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if(board.isWithinBounds(row+i,column+j)){
                    Location loc = new Location(row+i,column+j);
                    addMovesToLegalMoveListBasicParameters(legalMoveTargetList, loc, board, this);

                }
            }
        }

        return legalMoveTargetList;
    }
}
