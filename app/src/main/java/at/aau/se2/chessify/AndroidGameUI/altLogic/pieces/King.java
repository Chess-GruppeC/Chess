package at.aau.se2.chessify.AndroidGameUI.altLogic.pieces;

import java.util.ArrayList;

import at.aau.se2.chessify.AndroidGameUI.altLogic.board.ChessBoard;
import at.aau.se2.chessify.AndroidGameUI.altLogic.board.Location;


public class King extends ChessPiece{

    public King(PieceColour colour) {
        this.pieceValue=1000;
        this.colour=colour;
    }

    public King() {

    }

    @Override
    public ArrayList<Location> getLegalMoves(ChessBoard board) {
        ArrayList<Location> legalMoveTargetList = new ArrayList<>();

        Location pieceLocation=board.getLocationOf(this);

        //check location around the king
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if(board.isWithinBounds(i,j)){

                    if(board.getPieceAtLocation(new Location(i, j))==null) {    //if empty space, add move option to legal moves
                        legalMoveTargetList.add(new Location(i, j));
                    }else
                        //don't need that middle part because nothing happens, can just leave it out cause else-if
                    /*if(board.getPieceAtLocation(new Location(i, j)).getColour()==this.getColour()){
                                                                         //if space is occupied from friendly piece, don't add
                    }else*/
                    if(board.getPieceAtLocation(new Location(i, j)).getColour()!=this.getColour()){
                        legalMoveTargetList.add(new Location(i,j));
                                                                         //if space is occupied from enemy piece, give option to take
                    }

                }
            }
        }

        return legalMoveTargetList;
    }
}
