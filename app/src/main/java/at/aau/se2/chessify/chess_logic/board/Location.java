package at.aau.se2.chessify.chess_logic.board;

import java.util.ArrayList;

public class Location {

    int row;
    int column;

    public Location() {}

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
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

    public boolean checkIfLocationIsPartOfList(ArrayList<Location> locations){
        for(Location loc : locations){
            if (loc.compareLocation(this)){
                return true;
            }
        }
        return false;
    }

    public boolean compareLocation(Location location){
        if(this.row==location.getRow()){
            if(this.column==location.getColumn()){
                return true;
            }
        }
        return false;
    }

}
