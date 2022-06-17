package at.aau.se2.chessify.network.dto;

import java.util.List;

import at.aau.se2.chessify.chessLogic.board.Location;

public class GameDataDTO<T> {

    private PlayerDTO nextPlayer;
    private T data;
    private List<Location> destroyedLocationsByAtomicMove;

    public GameDataDTO() {
        // empty constructor needed for JSON parsing
    }

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
