package at.aau.se2.chessify.network.dto;

public class PlayerDTO {

    private String name;
    private Integer diceValue;

    public PlayerDTO() {
        // empty constructor needed for JSON parsing
    }

    public PlayerDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getDiceValue() {
        return diceValue;
    }

}
