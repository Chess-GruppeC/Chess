package at.aau.se2.chessify.network;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import at.aau.se2.chessify.network.dto.DiceResultDTO;
import at.aau.se2.chessify.network.dto.PlayerDTO;
import io.reactivex.disposables.Disposable;

/**
 * Running some single tests might not work due to the specified test order and because some tests depend on the result of other tests
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebSocketClientTest {

    private static WebSocketClient clientPlayerOne;

    private static final CountDownLatch waiter = new CountDownLatch(1);

    private static String gameID;

    private static boolean connected = false;

    @Before
    public void init() {
        if (connected) return;
        clientPlayerOne = WebSocketClient.getInstance("PlayerOne");
        waitOneSecond();  // wait 1 second to guarantee the connection has been opened
        assertNotNull(clientPlayerOne);
        assertEquals("PlayerOne", clientPlayerOne.getPlayerName());
        assertTrue(clientPlayerOne.isConnected());
        connected = true;
    }

    @AfterClass
    public static void cleanUp() {
        clientPlayerOne.dispose();
    }

    @Test
    public void _1_singleTonTest() {
        WebSocketClient client = WebSocketClient.getInstance("PlayerOne");
        assertEquals(clientPlayerOne, client);
    }

    @Test
    public void _2_getUriTest() {
        assertEquals("ws://se2-demo.aau.at:53207/chess/websocket", clientPlayerOne.getURI());
    }

    @Test
    public void _3_createNewGameTest() {
        AtomicReference<String> idAtomicReference = new AtomicReference<>();
        Disposable createGameDisable = clientPlayerOne.requestNewGame()
                .subscribe(response -> idAtomicReference.set(response.getPayload()));
        waitOneSecond();   // wait 1 second to guarantee the response has been received
        String id = idAtomicReference.get();
        assertNotNull(id);
        assertEquals(5, id.length());   // Received ID should be valid
        gameID = id;
        // Should be disposed due to calling onComplete on the BehaviorSubject
        assertTrue(createGameDisable.isDisposed());
    }

    @Test
    public void _4_joinGameTest() {
        joinGame(clientPlayerOne, gameID);
    }

    @Test
    @Ignore("Test needs to updated as player moves are synchronized now")
    public void _5_sendAndReceiveGameDataTest() {
        assertNotNull(gameID);
        AtomicReference<String> receivedDataAtomicReference = new AtomicReference<>();
        clientPlayerOne.receiveGameUpdates(gameID)
                .subscribe(gameData -> receivedDataAtomicReference.set(gameData.getPayload()));

        clientPlayerOne.sendGameUpdate(gameID, "DATA");
        waitOneSecond();  // wait 1 second to guarantee the response has been received
        String receivedData = receivedDataAtomicReference.get();
        assertNotNull(receivedData);
        assertEquals("DATA", receivedData);
    }

    @Test
    public void _6_getOpponentTest() throws JsonProcessingException {
        assertNotNull(gameID);
        AtomicReference<String> opponentAtomicReference = new AtomicReference<>();

        clientPlayerOne.getOpponent(gameID)
                .subscribe(opponent -> opponentAtomicReference.set(opponent.getPayload()));

        waitOneSecond();   // wait 1 second to guarantee the response has been received

        String opponentJsonString = opponentAtomicReference.get();
        assertNotNull(opponentJsonString);
        PlayerDTO opponent = new ObjectMapper().readValue(opponentJsonString, PlayerDTO.class);

        // Should receive an object of type PlayerDTO
        // As no opponent is present the attribute "name" of the received object should be null
        assertNotNull(opponent);
        assertNull(opponent.getName());
    }

    @Test
    public void _7_sendDiceValuesAndReceiveTheStartingPlayerTest() throws JsonProcessingException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        assertNotNull(gameID);

        AtomicReference<String> diceResultFirstPlayerAtomicReference = new AtomicReference<>();
        clientPlayerOne.receiveStartingPlayer(gameID)
                .subscribe(message -> diceResultFirstPlayerAtomicReference.set(message.getPayload()));

        // First player sends his value
        clientPlayerOne.sendDiceValue(gameID, "1");
        waitOneSecond();

        // No response should be received from the server yet
        // because the result is only sent as soon as enough players joined the game and each player has sent his dice value
        assertNull(diceResultFirstPlayerAtomicReference.get());

        // Instantiate second client to simulate a second player
        WebSocketClient secondWebSocketClient = getNewWebSocketClient("PlayerTwo");
        // Second player joins the game
        joinGame(secondWebSocketClient, gameID);

        AtomicReference<String> diceResultSecondPlayerAtomicReference = new AtomicReference<>();
        secondWebSocketClient.receiveStartingPlayer(gameID)
                .subscribe(message -> diceResultSecondPlayerAtomicReference.set(message.getPayload()));

        // Second player sends his dice value
        secondWebSocketClient.sendDiceValue(gameID, "5");
        waitOneSecond();

        // Both players should have received the result by now
        DiceResultDTO firstPlayerDiceResultDTO = parseDiceResult(diceResultFirstPlayerAtomicReference);
        DiceResultDTO secondPlayerDiceResultDTO = parseDiceResult(diceResultSecondPlayerAtomicReference);
        assertNotNull(firstPlayerDiceResultDTO);
        assertNotNull(secondPlayerDiceResultDTO);

        // The expected winner is "PlayerTwo" as he sent a greater dice value
        assertNotNull(firstPlayerDiceResultDTO.getWinner());
        assertEquals("PlayerTwo", firstPlayerDiceResultDTO.getWinner().getName());
    }

    @Test
    public void _8_reconnectWithNewPlayerNameTest() {
        WebSocketClient.reconnectWithPlayerName("NewName");
        waitOneSecond(); // wait one second to assure the connection has been established
        assertTrue(clientPlayerOne.isConnected());
        assertEquals("NewName", clientPlayerOne.getPlayerName());
    }

    @Test
    public void _9_createGameErrorForwardingTest() {

        AtomicReference<Throwable> idAtomicReference = new AtomicReference<>();
        clientPlayerOne.requestNewGame()
                .subscribe(message -> fail(),
                        throwable -> {
                            idAtomicReference.set(throwable);
                        });

        Throwable throwable = new Exception("Simulated exception");

        // Test if the exception is forwarded to the subscription correctly to simulate a network error
        clientPlayerOne.forwardErrorTo(throwable, clientPlayerOne.getCreateGameSubject());
        assertThrowableForwarding(idAtomicReference);
    }

    @Test
    public void _10_joinGameErrorForwardingTest() {

        AtomicReference<Throwable> idAtomicReference = new AtomicReference<>();
        clientPlayerOne.joinGame("")
                .subscribe(message -> fail(),
                        throwable -> {
                            idAtomicReference.set(throwable);
                        });

        Throwable throwable = new Exception("Simulated exception");

        // Test if the exception is forwarded to the subscription correctly to simulate a network error
        clientPlayerOne.forwardErrorTo(throwable, clientPlayerOne.getJoinGameSubject());
        assertThrowableForwarding(idAtomicReference);
    }

    @Test
    public void _11_getOpponentErrorForwardingTest() {

        AtomicReference<Throwable> idAtomicReference = new AtomicReference<>();
        clientPlayerOne.getOpponent("")
                .subscribe(message -> fail(),
                        throwable -> {
                            idAtomicReference.set(throwable);
                        });

        Throwable throwable = new Exception("Simulated exception");

        // Test if the exception is forwarded to the subscription correctly to simulate a network error
        clientPlayerOne.forwardErrorTo(throwable, clientPlayerOne.getGetOpponentSubject());
        assertThrowableForwarding(idAtomicReference);
    }

    private void assertThrowableForwarding(AtomicReference<Throwable> atomicReference) {
        waitOneSecond();
        assertNotNull(atomicReference.get());
        assertEquals("Simulated exception", atomicReference.get().getMessage());
    }

    private void waitOneSecond() {
        try {
            waiter.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void joinGame(WebSocketClient client, String gameID) {
        assertNotNull(gameID);
        AtomicReference<String> responseAtomicReference = new AtomicReference<>();
        Disposable joinGameDisposable = client.joinGame(gameID)
                .subscribe(response -> responseAtomicReference.set(response.getPayload()));

        waitOneSecond();   // wait 1 second to guarantee the response has been received

        String responseCode = responseAtomicReference.get();
        assertNotNull(responseCode);
        // Should be able to join the game and hence receive the response code "1"
        assertEquals("1", responseCode);
        // Should be disposed due to calling onComplete on the BehaviorSubject
        assertTrue(joinGameDisposable.isDisposed());
    }

    private WebSocketClient getNewWebSocketClient(String playerName) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor<WebSocketClient> constructor = WebSocketClient.class.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        WebSocketClient secondWebSocketClient = constructor.newInstance(playerName);
        waitOneSecond();  // wait one second to assure the connection has been established
        assertEquals(playerName, secondWebSocketClient.getPlayerName());
        assertTrue(secondWebSocketClient.isConnected());
        return secondWebSocketClient;
    }

    private DiceResultDTO parseDiceResult(AtomicReference<String> atomicReference) throws JsonProcessingException {
        String diceResultJsonString = atomicReference.get();
        assertNotNull(diceResultJsonString);
        DiceResultDTO diceResultDTO = new ObjectMapper().readValue(diceResultJsonString, DiceResultDTO.class);
        assertNotNull(diceResultDTO);
        return diceResultDTO;
    }

}