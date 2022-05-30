package at.aau.se2.chessify.network.dto;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

public class DiceResultDTOTest {

    @Test
    public void parseJsonStringTest() throws JsonProcessingException {
        String jsonString = "{\"players\":[{\"name\":\"player1\",\"diceValue\":5},{\"name\":\"player2\",\"diceValue\":null}]}";
        DiceResultDTO resultDTO = new ObjectMapper().readValue(jsonString, DiceResultDTO.class);
        assertNotNull(resultDTO);
        assertEquals(2, resultDTO.getPlayers().size());
        assertNull(resultDTO.getWinner());
        assertEquals(new Integer(5), resultDTO.getPlayers().get(0).getDiceValue());
        assertNull(resultDTO.getPlayers().get(1).getDiceValue());
    }

    @Test
    public void parseJsonStringWithWinnerTest() throws JsonProcessingException {
        String jsonString =
                "{\"players\":[{\"name\":\"player1\",\"diceValue\":5},{\"name\":\"player2\",\"diceValue\":1}]" +
                        ",\"winner\":{\"name\":\"player1\",\"diceValue\":5}}";

        DiceResultDTO resultDTO = new ObjectMapper().readValue(jsonString, DiceResultDTO.class);
        assertNotNull(resultDTO);
        assertEquals(2, resultDTO.getPlayers().size());
        assertNotNull(resultDTO.getWinner());
        assertEquals("player1" , resultDTO.getWinner().getName());
        assertEquals(new Integer(5), resultDTO.getPlayers().get(0).getDiceValue());
        assertEquals(new Integer(1), resultDTO.getPlayers().get(1).getDiceValue());
    }
}