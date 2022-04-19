package at.aau.se2.chessify.AndroidGameUI;

public enum Color {
    WHITE,
    BLACK;
    public Color opp() {
        return this == WHITE ? BLACK : WHITE;
    }
}
