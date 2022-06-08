package at.aau.se2.chessify;

import at.aau.se2.chessify.network.dto.PlayerDTO;

public class Game {

    public Game() {
        // empty constructor for JSON conversion
    }

    private PlayerDTO opponent;
    private String gameId;
    private int status;

    public static final int DEFAULT = -1;
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;

    public Game(PlayerDTO opponent, String gameId, int status) {
        this.opponent = opponent;
        this.gameId = gameId;
        this.status = status;
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
}
