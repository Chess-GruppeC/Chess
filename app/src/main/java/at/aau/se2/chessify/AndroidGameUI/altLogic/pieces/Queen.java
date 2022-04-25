package at.aau.se2.chessify.AndroidGameUI.altLogic.pieces;

import java.util.ArrayList;

import at.aau.se2.chessify.AndroidGameUI.altLogic.board.ChessBoard;
import at.aau.se2.chessify.AndroidGameUI.altLogic.board.Location;


public class Queen extends Bishop{

    public Queen(PieceColour colour) {
        this();
        this.pieceValue=9;
        this.colour=colour;

    }

    public Queen() {
    }

    @Override
    public ArrayList<Location> getLegalMoves(ChessBoard board) {
        //copy bishop
        ArrayList<Location> legalMoveTargetList = super.getLegalMoves(board);

        Location pieceLocation=board.getLocationOf(this);

        //copy rook
        //check up
        for(int i=pieceLocation.getRow(), j=pieceLocation.getColumn()-1;j>=0;j--){

            if(board.getPieceAtLocation(new Location(i, j))==null) {    //if empty space, add move option to legal moves
                legalMoveTargetList.add(new Location(i, j));
            }else
            if(board.getPieceAtLocation(new Location(i, j)).getColour()==this.getColour()){
                break;                                                 //if space is occupied from friendly piece, break
            }else
            if(board.getPieceAtLocation(new Location(i, j)).getColour()!=this.getColour()){
                legalMoveTargetList.add(new Location(i,j));
                break;                                                 //if space is occupied from enemy piece, give option to take and break
            }

        }

        //check down
        for(int i=pieceLocation.getRow(), j=pieceLocation.getColumn()+1;j<8;j++){

            if(board.getPieceAtLocation(new Location(i, j))==null) {    //if empty space, add move option to legal moves
                legalMoveTargetList.add(new Location(i, j));
            }else
            if(board.getPieceAtLocation(new Location(i, j)).getColour()==this.getColour()){
                break;                                                 //if space is occupied from friendly piece, break
            }else
            if(board.getPieceAtLocation(new Location(i, j)).getColour()!=this.getColour()){
                legalMoveTargetList.add(new Location(i,j));
                break;                                                 //if space is occupied from enemy piece, give option to take and break
            }

        }

        //check left
        for(int i=pieceLocation.getRow()-1, j=pieceLocation.getColumn();i>=0;i--){

            if(board.getPieceAtLocation(new Location(i, j))==null) {    //if empty space, add move option to legal moves
                legalMoveTargetList.add(new Location(i, j));
            }else
            if(board.getPieceAtLocation(new Location(i, j)).getColour()==this.getColour()){
                break;                                                 //if space is occupied from friendly piece, break
            }else
            if(board.getPieceAtLocation(new Location(i, j)).getColour()!=this.getColour()){
                legalMoveTargetList.add(new Location(i,j));
                break;                                                 //if space is occupied from enemy piece, give option to take and break
            }

        }

        //check right
        for(int i=pieceLocation.getRow()+1, j=pieceLocation.getColumn();i<8;i++){

            if(board.getPieceAtLocation(new Location(i, j))==null) {    //if empty space, add move option to legal moves
                legalMoveTargetList.add(new Location(i, j));
            }else
            if(board.getPieceAtLocation(new Location(i, j)).getColour()==this.getColour()){
                break;                                                 //if space is occupied from friendly piece, break
            }else
            if(board.getPieceAtLocation(new Location(i, j)).getColour()!=this.getColour()){
                legalMoveTargetList.add(new Location(i,j));
                break;                                                 //if space is occupied from enemy piece, give option to take and break
            }

        }


        return legalMoveTargetList;
    }
}
