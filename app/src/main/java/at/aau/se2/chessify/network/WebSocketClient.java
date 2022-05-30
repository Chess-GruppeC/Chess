package at.aau.se2.chessify.network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class WebSocketClient {

    private static StompClient mStompClient;
    private static final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private BehaviorSubject<StompMessage> createGameSubject = BehaviorSubject.create();
    private BehaviorSubject<StompMessage> joinGameSubject = BehaviorSubject.create();
    private BehaviorSubject<StompMessage> getOpponentSubject = BehaviorSubject.create();

    public static final String WEBSOCKET_ENDPOINT = "/chess/websocket";
    public static final String SERVER = "se2-demo.aau.at";
    public static final Integer PORT = 53207;

    private static WebSocketClient INSTANCE;
    private static String playerName;

    private WebSocketClient(String playerName) {
        WebSocketClient.playerName = playerName;
        establishConnection();
        initSubscriptions();
    }

    public static WebSocketClient getInstance(String playerName) {
        if (INSTANCE == null) {
            INSTANCE = new WebSocketClient(playerName);
        }
        return INSTANCE;
    }

    private void establishConnection() {
        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("username", playerName));
        mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, getURI());
        mStompClient.connect(headers);
    }

    public String getURI() {
        return "ws://" + SERVER + ":" + PORT + WEBSOCKET_ENDPOINT;
    }

    private void initSubscriptions() {
        compositeDisposable.add(mStompClient.topic("/user/queue/create")
                .subscribe(response -> forwardResponseTo(response, createGameSubject),
                        throwable -> forwardErrorTo(throwable, createGameSubject)));

        compositeDisposable.add(mStompClient.topic("/user/queue/join")
                .subscribe(response -> forwardResponseTo(response, joinGameSubject),
                        throwable -> forwardErrorTo(throwable, joinGameSubject)));

        // --> get Opponents name
        compositeDisposable.add(mStompClient.topic("/user/queue/game/opponent")
                .subscribe(response -> forwardResponseTo(response, getOpponentSubject),
                        throwable -> forwardErrorTo(throwable, getOpponentSubject)));

    }

    public <T> void forwardResponseTo(T message, Subject<T> destinationSubject) {
        destinationSubject.onNext(message);
        destinationSubject.onComplete();
    }

    public <T> void forwardErrorTo(Throwable throwable, Subject<T> destinationSubject) {
        destinationSubject.onError(throwable);
        destinationSubject.onComplete();
    }

    public Observable<StompMessage> requestNewGame() {
        createGameSubject = BehaviorSubject.create();
        mStompClient.send("/topic/create")
                .doOnError(throwable -> forwardErrorTo(throwable, createGameSubject))
                .onErrorComplete()
                .subscribe();
        return createGameSubject;
    }

    public Observable<StompMessage> joinGame(String gameId) {
        joinGameSubject = BehaviorSubject.create();
        mStompClient.send("/topic/join", gameId)
                .doOnError(throwable -> forwardErrorTo(throwable, joinGameSubject))
                .onErrorComplete()
                .subscribe();
        return joinGameSubject;
    }

    public Flowable<StompMessage> receiveGameUpdates(String gameId) {
        return mStompClient.topic("/topic/update/" + gameId);
    }

    public Flowable<StompMessage> receiveStartingPlayer(String gameId) {
        return mStompClient.topic("/topic/getStartingPlayer/" + gameId);
    }

    public void sendGameUpdate(String gameId, String data) {
        mStompClient.send("/topic/game/" + gameId, data).subscribe();
    }

    public void sendDiceValue(String gameId, String diceValue) {
        mStompClient.send("/topic/game/rollDice/" + gameId, diceValue).subscribe();
    }

    // Gegenerabfrage über Game ID
    public Observable<StompMessage> getOpponent(String gameId) {
        getOpponentSubject = BehaviorSubject.create();
        mStompClient.send("/topic/game/opponent", gameId)
                .doOnError(throwable -> forwardErrorTo(throwable, getOpponentSubject))
                .onErrorComplete()
                .subscribe();
        return getOpponentSubject;
    }

    public boolean isConnected() {
        return mStompClient.isConnected();
    }

    public void dispose() {
        compositeDisposable.dispose();
        mStompClient.disconnect();
    }

    public String getPlayerName() {
        return playerName;
    }

    public static void reconnectWithNewPlayerName(String newPlayerName) {
        if (mStompClient != null) {
            compositeDisposable.clear();
            mStompClient.disconnect();
            mStompClient = null;
            INSTANCE = null;
            getInstance(newPlayerName);
        }
    }

    public BehaviorSubject<StompMessage> getCreateGameSubject() {
        return createGameSubject;
    }

    public BehaviorSubject<StompMessage> getJoinGameSubject() {
        return joinGameSubject;
    }

    public BehaviorSubject<StompMessage> getGetOpponentSubject() {
        return getOpponentSubject;
    }
}
