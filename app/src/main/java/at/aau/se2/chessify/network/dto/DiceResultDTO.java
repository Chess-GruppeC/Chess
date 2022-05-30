package at.aau.se2.chessify.network.dto;

import java.util.List;

public class DiceResultDTO {

    private List<PlayerDTO> players;
    private PlayerDTO winner;

    public DiceResultDTO() {}

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public PlayerDTO getWinner() {
        return winner;
    }
}
