package at.aau.se2.chessify.network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class WebSocketClient {

    private StompClient mStompClient;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private BehaviorSubject<StompMessage> createGameSubject = BehaviorSubject.create();
    private BehaviorSubject<StompMessage> joinGameSubject = BehaviorSubject.create();
    private BehaviorSubject<StompMessage> getOpponentSubject = BehaviorSubject.create();

    public static final String WEBSOCKET_ENDPOINT = "/chess/websocket";
    public static final String SERVER = "se2-demo.aau.at";
    public static final Integer PORT = 53207;

    private static WebSocketClient INSTANCE;
    private static String username;

    private WebSocketClient() {
        establishConnection();
        initSubscriptions();
    }

    public static WebSocketClient getInstance(String username) {
        if (INSTANCE == null) {
            WebSocketClient.username = username;
            INSTANCE = new WebSocketClient();
        }
        return INSTANCE;
    }

    private void establishConnection() {
        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("username", username));
        mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, getURI());
        mStompClient.connect(headers);
    }

    public String getURI() {
        return "ws://" + SERVER + ":" + PORT + WEBSOCKET_ENDPOINT;
    }

    private void initSubscriptions() {
        compositeDisposable.add(mStompClient.topic("/user/queue/create")
                .subscribe(response -> {
                    createGameSubject.onNext(response);
                    createGameSubject.onComplete();
                }, throwable -> {
                    createGameSubject.onError(throwable);
                    createGameSubject.onComplete();
                }));

        compositeDisposable.add(mStompClient.topic("/user/queue/join")
                .subscribe(response -> {
                    joinGameSubject.onNext(response);
                    joinGameSubject.onComplete();
                }, throwable -> {
                    joinGameSubject.onError(throwable);
                    joinGameSubject.onComplete();
                }));

        // --> get Opponents name
        compositeDisposable.add(mStompClient.topic("/user/queue/game/opponent")
                .subscribe(response -> {
                    getOpponentSubject.onNext(response);
                    getOpponentSubject.onComplete();
                }, throwable -> {
                    getOpponentSubject.onError(throwable);
                    getOpponentSubject.onComplete();
                }));

    }

    public Observable<StompMessage> requestNewGame() {
        createGameSubject = BehaviorSubject.create();
        mStompClient.send("/topic/create")
                .doOnError(throwable -> {
                    createGameSubject.onError(throwable);
                    createGameSubject.onComplete();
                }).onErrorComplete()
                .subscribe();
        return createGameSubject;
    }

    public Observable<StompMessage> joinGame(String gameId) {
        joinGameSubject = BehaviorSubject.create();
        mStompClient.send("/topic/join", gameId)
                .doOnError(throwable -> {
                    joinGameSubject.onError(throwable);
                    joinGameSubject.onComplete();
                }).onErrorComplete()
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
                .doOnError(throwable -> {
                    getOpponentSubject.onError(throwable);
                    getOpponentSubject.onComplete();
                }).onErrorComplete()
                .subscribe();
        return getOpponentSubject;
    }

    public boolean isConnected() {
        return mStompClient.isConnected();
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    public String getUsername() {
        return username;
    }

    public void setClient(StompClient c) {
        this.mStompClient = c;
    }
}
