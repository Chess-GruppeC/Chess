package at.aau.se2.chessify.AndroidGameUI.altLogic.pieces;

import java.util.ArrayList;

import at.aau.se2.chessify.AndroidGameUI.altLogic.board.ChessBoard;
import at.aau.se2.chessify.AndroidGameUI.altLogic.board.Location;

public abstract class ChessPiece {
    int pieceValue;
    boolean moved=false;
    PieceColour colour;

    abstract public ArrayList<Location> getLegalMoves(ChessBoard board);

    public int getPieceValue() {
        return pieceValue;
    }

    public void setPieceValue(int pieceValue) {
        this.pieceValue = pieceValue;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public PieceColour getColour() {
        return colour;
    }

    public void setColour(PieceColour colour) {
        this.colour = colour;
    }


}
