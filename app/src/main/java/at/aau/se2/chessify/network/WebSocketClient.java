package at.aau.se2.chessify.network;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

import java.util.ArrayList;
import java.util.List;

public class WebSocketClient {

    private StompClient mStompClient;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private BehaviorSubject<StompMessage> createGameSubject;
    private BehaviorSubject<StompMessage> joinGameSubject;

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
        if(INSTANCE == null) {
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
                }, Throwable::printStackTrace));

        compositeDisposable.add(mStompClient.topic("/user/queue/join")
                .subscribe(response -> {
                    joinGameSubject.onNext(response);
                    joinGameSubject.onComplete();
                }, Throwable::printStackTrace));
    }

    /**
     * Usage:
     * client.requestNewGame().subscribe(serverResponse -> {
     *     serverResponse.getPayload(); // Here you receive the game ID asynchronously
     * }, error -> {
     *     // exception handling
     * });
     * @return The observable to subscribe to
     */
    public Observable<StompMessage> requestNewGame() {
        createGameSubject = BehaviorSubject.create();
        mStompClient.send("/topic/create").subscribe();
        return createGameSubject;
    }

    /**
     * Usage:
     * client.joinGame(gameId).subscribe(serverResponse -> {
     *     serverResponse.getPayload(); // Here you receive a status code from the server
     * }, error -> {
     *      // exception handling
     * });
     * The server response can be one of the following: -1 (game not found), 0 (game full), 1 (successfully joined)
     * @return The observable to subscribe to
     */
    public Observable<StompMessage> joinGame(String gameId) {
        joinGameSubject = BehaviorSubject.create();
        mStompClient.send("/topic/join", gameId).subscribe();
        return joinGameSubject;
    }

    /**
     * Usage:
     * client.receiveGameUpdates(gameId).subscribe(data -> {
     *     data.getPayload(); // Here you receive all the game data
     * }, error -> {
     *     // exception handling
     * });
     * @return The flowable to subscribe to
     */
    public Flowable<StompMessage> receiveGameUpdates(String gameId) {
        return mStompClient.topic("/topic/update/" + gameId);
    }

    /**
     * Usage:
     * client.sendGameUpdate(gameId, data).subscribe();
     */
    public void sendGameUpdate(String gameId, String data) {
        mStompClient.send("/topic/game/" + gameId, data).subscribe();
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
