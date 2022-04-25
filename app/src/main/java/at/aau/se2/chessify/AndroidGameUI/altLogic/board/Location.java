package at.aau.se2.chessify.AndroidGameUI.altLogic.board;

import at.aau.se2.chessify.AndroidGameUI.altLogic.pieces.ChessPiece;

public class Location {
    ChessPiece piece;


    int row;
    int column;

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public int getPiece() {
        return row;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setPiece(Object o) {
    }
}
