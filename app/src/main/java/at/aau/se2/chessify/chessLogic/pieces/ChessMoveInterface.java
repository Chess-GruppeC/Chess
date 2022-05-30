package at.aau.se2.chessify.chessLogic.pieces;

import java.util.ArrayList;

import at.aau.se2.chessify.chessLogic.board.ChessBoard;
import at.aau.se2.chessify.chessLogic.board.Location;

public interface ChessMoveInterface {

    default ArrayList<Location> getDiagonalMoves(ChessBoard board, ChessPiece piece) {
        ArrayList<Location> legalMoveTargetList = new ArrayList<>();

        Location pieceLocation=board.getLocationOf(piece);

        //check all 4 directions

        //check up left
        for(int i=pieceLocation.getRow()-1, j=pieceLocation.getColumn()-1; i>=0 && j>=0; i--,j--){

            Location testLocation = new Location(i, j);
            if(!addMovesToLegalMoveListBasicParameters(legalMoveTargetList, testLocation, board, piece)){
                break;
            }
        }

        //check up right
        for(int i=pieceLocation.getRow()-1, j=pieceLocation.getColumn()+1; i>=0 && j<8; i--,j++){

            Location testLocation = new Location(i, j);
            if(!addMovesToLegalMoveListBasicParameters(legalMoveTargetList, testLocation, board, piece)){
                break;
            }
        }

        //check down right
        for(int i=pieceLocation.getRow()+1, j=pieceLocation.getColumn()+1; i<8 && j<8; i++,j++){

            Location testLocation = new Location(i, j);
            if(!addMovesToLegalMoveListBasicParameters(legalMoveTargetList, testLocation, board, piece)){
                break;
            }
        }

        //check down left
        for(int i=pieceLocation.getRow()+1, j=pieceLocation.getColumn()-1; i<8 && j>=0; i++,j--){

            Location testLocation = new Location(i, j);
            if(!addMovesToLegalMoveListBasicParameters(legalMoveTargetList, testLocation, board, piece)){
                break;
            }
        }


        return legalMoveTargetList;
    }

    default ArrayList<Location> getHorizontalAndVerticalMoves(ChessBoard board, ChessPiece piece) {
        ArrayList<Location> legalMoveTargetList = new ArrayList<>();

        Location pieceLocation=board.getLocationOf(piece);

        //check up
        for(int i=pieceLocation.getRow(), j=pieceLocation.getColumn()-1;j>=0;j--){

            Location testLocation = new Location(i, j);
            if(!addMovesToLegalMoveListBasicParameters(legalMoveTargetList, testLocation, board, piece)){
                break;
            }

        }

        //check down
        for(int i=pieceLocation.getRow(), j=pieceLocation.getColumn()+1;j<8;j++){

            Location testLocation = new Location(i, j);
            if(!addMovesToLegalMoveListBasicParameters(legalMoveTargetList, testLocation, board, piece)){
                break;
            }

        }

        //check left
        for(int i=pieceLocation.getRow()-1, j=pieceLocation.getColumn();i>=0;i--){

            Location testLocation = new Location(i, j);
            if(!addMovesToLegalMoveListBasicParameters(legalMoveTargetList, testLocation, board, piece)){
                break;
            }

        }

        //check right
        for(int i=pieceLocation.getRow()+1, j=pieceLocation.getColumn();i<8;i++){

            Location testLocation = new Location(i, j);
            if(!addMovesToLegalMoveListBasicParameters(legalMoveTargetList, testLocation, board, piece)){
                break;
            }

        }


        return legalMoveTargetList;
    }

    default boolean addMovesToLegalMoveListBasicParameters (ArrayList<Location> legalMoveTargetList, Location location, ChessBoard board, ChessPiece piece){
        if(board.getPieceAtLocation(location)==null) {    //if empty space, add move option to legal moves
            legalMoveTargetList.add(location);
        }else
        if(board.getPieceAtLocation(location).getColour()!=piece.getColour()){
            legalMoveTargetList.add(location);
            return false;                                                 //if space is occupied from enemy piece, give option to take and break
        }else
        if(board.getPieceAtLocation(location).getColour()==piece.getColour()){
            return false;                                                 //if space is occupied from friendly piece, break
        }
        return true;
    }

}
