package at.aau.se2.chessify.network.dto;

import java.util.List;

import at.aau.se2.chessify.chess_logic.board.Location;

public class GameDataDTO<T> {

    private PlayerDTO nextPlayer;
    private T data;
    private List<Location> destroyedLocationsByAtomicMove;

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

    public List<Location> getDestroyedLocationsByAtomicMove() {
        return destroyedLocationsByAtomicMove;
    }

    public void setDestroyedLocationsByAtomicMove(List<Location> destroyedLocationsByAtomicMove) {
        this.destroyedLocationsByAtomicMove = destroyedLocationsByAtomicMove;
    }
}
