package at.aau.se2.chessify;

import at.aau.se2.chessify.chess_logic.pieces.PieceColour;
import at.aau.se2.chessify.network.dto.PlayerDTO;

public class Game {

    public Game() {
        // empty constructor for JSON conversion
    }

    private PlayerDTO opponent;
    private String gameId;
    private int status;
    private boolean isWinner = false;
    private PieceColour pieceColour;

    public static final int DEFAULT = -1;
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;

    public Game(PlayerDTO opponent, String gameId, int status, PieceColour pieceColour) {
        this.opponent = opponent;
        this.gameId = gameId;
        this.status = status;
        this.pieceColour = pieceColour;
    }

    public PlayerDTO getOpponent() {
        return opponent;
    }

    public void setOpponent(PlayerDTO opponent) {
        this.opponent = opponent;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public PieceColour getPieceColour() {
        return pieceColour;
    }
}
