package at.aau.se2.chessify.network.dto;

public class GameDataDTO<T> {

    private PlayerDTO nextPlayer;
    private T data;

    public GameDataDTO() {}

    public GameDataDTO(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PlayerDTO getNextPlayer() {
        return nextPlayer;
    }
}
