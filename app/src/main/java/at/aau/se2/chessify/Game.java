package at.aau.se2.chessify;

import at.aau.se2.chessify.network.dto.PlayerDTO;

public class Game {

    private PlayerDTO opponent;
    private String gameId;
    private String status;

    public static final Integer STATUS_FINISHED = 1;
    public static final Integer STATUS_RUNNING = 0;

    public Game(PlayerDTO opponent, String gameId, Integer status) {
        this.opponent = opponent;
        this.gameId = gameId;
        String statusStr;
        switch (status) {
            case 0:
                statusStr = "Running";
                break;
            case 1:
                statusStr = "Finished";
                break;
            default:
                statusStr = "Unknown state";
                break;
        }
        this.status = statusStr;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
