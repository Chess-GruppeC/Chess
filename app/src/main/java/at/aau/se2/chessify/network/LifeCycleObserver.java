package at.aau.se2.chessify.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import at.aau.se2.chessify.util.Helper;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class LifeCycleObserver implements DefaultLifecycleObserver {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final BehaviorSubject<WebSocketClient> onReconnectSubject = BehaviorSubject.create();
    private final String playerName;

    private boolean reconnect = false;

    public static final int RECONNECT_AFTER_IN_BACKGROUND_FOR = 20_000;

    public LifeCycleObserver(Context context) {
        this.playerName = Helper.getUniquePlayerName(context);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);
        if (reconnect) {
            reconnect();
        }
        reconnect = false;
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        handler.postDelayed(() -> reconnect = true, RECONNECT_AFTER_IN_BACKGROUND_FOR);
    }


    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onDestroy(owner);
        owner.getLifecycle().removeObserver(this);
        handler.removeCallbacksAndMessages(null);
    }

    private void reconnect() {
        WebSocketClient.reconnectWithPlayerName(playerName);
        WebSocketClient client = WebSocketClient.getInstance(playerName);
        onReconnectSubject.onNext(client);
        onReconnectSubject.onComplete();
    }

    public Observable<WebSocketClient> onClientReconnect() {
        return onReconnectSubject;
    }
}
