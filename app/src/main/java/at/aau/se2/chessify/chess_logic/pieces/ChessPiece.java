package at.aau.se2.chessify.chess_logic.pieces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import at.aau.se2.chessify.chess_logic.board.ChessBoard;
import at.aau.se2.chessify.chess_logic.board.Location;

import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = King.class, name = "King"),
        @JsonSubTypes.Type(value = Bishop.class, name = "Bishop"),
        @JsonSubTypes.Type(value = Knight.class, name = "Knight"),
        @JsonSubTypes.Type(value = Pawn.class, name = "Pawn"),
        @JsonSubTypes.Type(value = Queen.class, name = "Queen"),
        @JsonSubTypes.Type(value = Rook.class, name = "Rook"),
})
public abstract class ChessPiece {
    int pieceValue;
    boolean moved=false;
    PieceColour colour;

    public ChessPiece() {}

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
