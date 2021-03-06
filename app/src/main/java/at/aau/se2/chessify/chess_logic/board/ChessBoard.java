package at.aau.se2.chessify.chess_logic.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.aau.se2.chessify.chess_logic.pieces.Bishop;
import at.aau.se2.chessify.chess_logic.pieces.ChessPiece;
import at.aau.se2.chessify.chess_logic.pieces.King;
import at.aau.se2.chessify.chess_logic.pieces.Knight;
import at.aau.se2.chessify.chess_logic.pieces.Pawn;
import at.aau.se2.chessify.chess_logic.pieces.PieceColour;
import at.aau.se2.chessify.chess_logic.pieces.Queen;
import at.aau.se2.chessify.chess_logic.pieces.Rook;

public class ChessBoard {
    ChessPiece[][] gameBoard;

    public ChessBoard copy() {
        ChessBoard copy = new ChessBoard();
        ChessPiece[][] copyOfPieces = Arrays.stream(gameBoard).map(ChessPiece[]::clone).toArray(ChessPiece[][]::new);
        copy.setGameBoard(copyOfPieces);
        return copy;
    }

    public ChessBoard() {
        this.gameBoard = new ChessPiece[8][8];

        addPawns();
        addKnights();
        addBishops();
        addRooks();
        addQueens();
        addKings();
    }

    public ChessPiece[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(ChessPiece[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public PieceColour checkWinner(){
        boolean isWhiteKingAlive=false;
        boolean isBlackKingAlive=false;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                ChessPiece checkPiece = getPieceAtLocation(new Location(i, j));
                if(checkPiece!=null) {
                    if (checkPiece.getClass() == King.class) {
                        if (checkPiece.getColour() == PieceColour.BLACK) {
                            isBlackKingAlive = true;
                        }
                        if (checkPiece.getColour() == PieceColour.WHITE) {
                            isWhiteKingAlive = true;
                        }
                    }
                }
            }
        }
        if(isBlackKingAlive&&isWhiteKingAlive){
            return null;
        }
        if(isBlackKingAlive){
            return PieceColour.BLACK;
        }
        if(isWhiteKingAlive){
            return PieceColour.WHITE;
        }
        return PieceColour.GREY;
    }

    public int performMoveOnBoard(Move move) throws IllegalArgumentException{
        int takenPieceValue=0;
        if(!(isWithinBounds(move.getFrom()) && isWithinBounds(move.getTo()))){
            throw new IllegalArgumentException("Please select a field within bounds");
        }
        ChessPiece movingPiece = getPieceAtLocation(move.getFrom());
        ArrayList<Location> allLegalMoves = movingPiece.getLegalMoves(this);
        for(Location location : allLegalMoves){
            if(move.getTo().compareLocation(location)){
                if(getPieceAtLocation(move.getTo())!=null){
                    takenPieceValue=getPieceAtLocation(move.getTo()).getPieceValue();
                }
                setLocationTo(movingPiece, move.getTo());
                movingPiece.setMoved(true);
                checkForPawnTransformation(movingPiece);
                return takenPieceValue;
            }
        }
        throw new IllegalArgumentException("Please perform a legal move");
    }

    public void checkForPawnTransformation(ChessPiece piece){
        if(piece.getClass()==Pawn.class){
            checkPawnForTransformationLocation(piece);
        }
    }

    public void checkPawnForTransformationLocation (ChessPiece piece){
        if(piece.getColour()==PieceColour.WHITE){
            if(getLocationOf(piece).getRow()==0){
                performPawnQueenSwap(piece);
            }
        }else
        if(getLocationOf(piece).getRow()==7){
            performPawnQueenSwap(piece);
        }
    }

    public void performPawnQueenSwap(ChessPiece piece){
        Queen transformedPiece = new Queen(piece.getColour());
        setLocationTo(transformedPiece, getLocationOf(piece));
    }

    public List<Location> performAtomicMove(Move move) {
        List<Location> destroyedLocations = new ArrayList<>();
        int takenPieceValue = performMoveOnBoard(move);
        if (takenPieceValue == 0) {
            return null;
        }
        Location location = move.getTo();
        gameBoard[location.getRow()][location.getColumn()] = null;
        destroyedLocations.add(location);
        for (int i = location.getRow() - 1; i <= location.getRow() + 1; i++) {
            for (int j = location.getColumn() - 1; j <= location.getColumn() + 1; j++) {
                Location tempLocation = new Location(i, j);
                if (isWithinBounds(tempLocation)) {
                    ChessPiece piece = getPieceAtLocation(tempLocation);
                    if (piece == null || piece instanceof Pawn)
                        continue;
                    destroyedLocations.add(tempLocation);
                    gameBoard[tempLocation.getRow()][tempLocation.getColumn()] = null;
                }
            }
        }
        return destroyedLocations;
    }

    public Location getLocationOf(ChessPiece piece){
        for(int i=0; i<gameBoard.length; i++){
            for(int j=0; j< gameBoard[i].length; j++){
                if(gameBoard[i][j]==piece){
                    return new Location(i, j);
                }
            }
        }
        return null;
    }

    public void setLocationTo(ChessPiece piece, Location targetLocation){
        Location originLocation = this.getLocationOf(piece);
        gameBoard[targetLocation.getRow()][targetLocation.getColumn()]=piece;
        if(originLocation!=null) {
            gameBoard[originLocation.getRow()][originLocation.getColumn()] = null;
        }
    }

    public ChessPiece getPieceAtLocation(Location location){
        return gameBoard[location.getRow()][location.getColumn()];
    }

    public boolean isWithinBounds(int row, int column){
        if((row >= 0) && (row < 8)
                && (column >= 0) && (column < 8)){
            return true;
        }
        return false;
    }

    public boolean isWithinBounds(Location location){
        if(location != null) {
            if ((location.getRow() >= 0) && (location.getRow() < 8)
                    && (location.getColumn() >= 0) && (location.getColumn() < 8)) {
                return true;
            }
        }
        return false;
    }

    public void addPawns(){
        addBlackPawns();
        addWhitePawns();
    }

    public void addKnights(){
        addBlackKnights();
        addWhiteKnights();
    }

    public void addBishops(){
        addBlackBishops();
        addWhiteBishops();
    }

    public void addRooks(){
        addBlackRooks();
        addWhiteRooks();
    }

    public void addQueens(){
        addBlackQueen();
        addWhiteQueen();
    }

    public void addKings(){
        addBlackKing();
        addWhiteKing();
    }

    public void addBlackPawns(){
        for(int i=0; i<8; i++){
            Pawn blackPawn=new Pawn(PieceColour.BLACK);
            gameBoard[1][i]=blackPawn;
        }
    }

    public void addWhitePawns(){
        for(int i=0; i<8; i++){
            Pawn whitePawn=new Pawn(PieceColour.WHITE);
            gameBoard[6][i]=whitePawn;
        }
    }

    public void addBlackKnights(){
        Knight blackKnight1 = new Knight(PieceColour.BLACK);
        gameBoard[0][1]=blackKnight1;
        Knight blackKnight2 = new Knight(PieceColour.BLACK);
        gameBoard[0][6]=blackKnight2;
    }

    public void addWhiteKnights(){
        Knight whiteKnight1 = new Knight(PieceColour.WHITE);
        gameBoard[7][1]=whiteKnight1;
        Knight whiteKnight2 = new Knight(PieceColour.WHITE);
        gameBoard[7][6]=whiteKnight2;
    }

    public void addBlackBishops(){
        Bishop blackBishop1 = new Bishop(PieceColour.BLACK);
        gameBoard[0][2]=blackBishop1;
        Bishop blackBishop2 = new Bishop(PieceColour.BLACK);
        gameBoard[0][5]=blackBishop2;
    }

    public void addWhiteBishops(){
        Bishop whiteBishop1 = new Bishop(PieceColour.WHITE);
        gameBoard[7][2]=whiteBishop1;
        Bishop whiteBishop2 = new Bishop(PieceColour.WHITE);
        gameBoard[7][5]=whiteBishop2;
    }

    public void addBlackRooks(){
        Rook blackRook1 = new Rook(PieceColour.BLACK);
        gameBoard[0][0]=blackRook1;
        Rook blackRook2 = new Rook(PieceColour.BLACK);
        gameBoard[0][7]=blackRook2;
    }

    public void addWhiteRooks(){
        Rook whiteRook1 = new Rook(PieceColour.WHITE);
        gameBoard[7][0]=whiteRook1;
        Rook whiteRook2 = new Rook(PieceColour.WHITE);
        gameBoard[7][7]=whiteRook2;
    }

    public void addBlackQueen(){
        Queen blackQueen = new Queen (PieceColour.BLACK);
        gameBoard[0][3]=blackQueen;
    }

    public void addWhiteQueen(){
        Queen whiteQueen = new Queen (PieceColour.WHITE);
        gameBoard[7][3]=whiteQueen;
    }

    public void addBlackKing(){
        King blackKing = new King (PieceColour.BLACK);
        gameBoard[0][4]=blackKing;
    }

    public void addWhiteKing(){
        King whiteKing = new King (PieceColour.WHITE);
        gameBoard[7][4]=whiteKing;
    }

}
