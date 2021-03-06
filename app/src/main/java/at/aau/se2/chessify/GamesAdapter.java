package at.aau.se2.chessify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GamesAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<Game> games;

    public GamesAdapter(Context applicationContext, List<Game> games) {
        inflater = (LayoutInflater.from(applicationContext));
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int i) {
        return games.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {

        if (v == null) {
            v = inflater.inflate(R.layout.listview_games_item, null);
        }

        View view = v;
        TextView playerName = view.findViewById(R.id.player_name);
        Game game = games.get(i);
        try {
            playerName.setText(game.getOpponent().getName());
        } catch (NullPointerException e) {
            // ignored
        }
        TextView gameId = view.findViewById(R.id.game_id);
        String gameIdStr = "#" + game.getGameId();
        gameId.setText(gameIdStr);
        TextView gameState = view.findViewById(R.id.game_status);
        String statusStr;
        switch (game.getStatus()) {
            case 0:
                statusStr = "Running";
                break;
            case 1:
                statusStr = "Finished. ";
                statusStr += game.isWinner() ? "You won!" : "You lost";
                break;
            default:
                statusStr = "Unknown state";
                break;
        }
        String gameStatusStr = "Status: ".concat(statusStr);
        gameState.setText(gameStatusStr);
        return view;
    }

    public void updateAdapter(List<Game> updated) {
        this.games = updated;
        notifyDataSetChanged();
    }

}