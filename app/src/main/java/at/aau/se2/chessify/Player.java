package at.aau.se2.chessify;

import at.aau.se2.chessify.chessLogic.pieces.PieceColour;

public class Player {
    private PieceColour team;
    private Player whitePlayer;
    private Player blackPlayer;
    private int diceNumber1;
    private int diceNumber2;
    private Player currentPlayer;

    public int getDiceNumber1() {
        return diceNumber1;
    }

    public void setDiceNumber1(int diceNumber1) {
        this.diceNumber1 = diceNumber1;
    }

    public int getDiceNumber2() {
        return diceNumber2;
    }

    public void setDiceNumber2(int diceNumber2) {
        this.diceNumber2 = diceNumber2;
    }



   /* public Player(PieceColour team, Player whitePlayer, Player blackPlayer, Player currentPlayer) {
        this.team = team;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = currentPlayer;
    }*/

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public Player(PieceColour team) {
        this.team = team;
    }

    public PieceColour getTeam() {
        return team;
    }

    public void setTeam(PieceColour team) {
        this.team = team;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void changeCurrentPlayer() {
        if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
        } else {
            currentPlayer = whitePlayer;
        }
    }
}
