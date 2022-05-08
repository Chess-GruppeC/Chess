package at.aau.se2.chessify.chessLogic.pieces;

import at.aau.se2.chessify.chessLogic.board.ChessBoard;
import at.aau.se2.chessify.chessLogic.board.Location;

import java.util.ArrayList;

public class Rook extends ChessPiece{

    public Rook(PieceColour colour) {
        this.pieceValue=5;
        this.colour=colour;
    }

    @Override
    public ArrayList<Location> getLegalMoves(ChessBoard board) {
        ArrayList<Location> legalMoveTargetList = new ArrayList<>();

        Location pieceLocation=board.getLocationOf(this);

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