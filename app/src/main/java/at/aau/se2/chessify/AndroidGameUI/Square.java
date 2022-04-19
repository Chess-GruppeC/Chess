package at.aau.se2.chessify.AndroidGameUI;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Square extends BaseAdapter {
    public Square(ChessActivity chessActivity) {
    }


/*    private ChessActivity chessContext;
    private Board board;
    public Square(ChessActivity context) {
        this(context, Board.defaultBoard());
    }
    public Square(ChessActivity context, Board board) {
        this.chessContext = context;
        this.board = board;
    }*/

    @Override
    public int getCount() {
        return 0; //Board.(numRow*numCols)
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        /*ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = chessContext.getLayoutInflater();
            view = inflater.inflate(R.layout.square, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.pieceViewItem = (PieceImageView) view
                    .findViewById(R.id.squareView);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int row = i / 8;
        int col = i % 8;

        view.setBackgroundResource(getSquareColor(row, col));

        PieceImageView pieceView = viewHolder.pieceViewItem;
        pieceView.setRow(row);
        pieceView.setCol(col);

        view.setOnClickListener(new OnPieceClick(chessContext, board,
                row, col));
        view.setOnDragListener(new OnPieceDrag(chessContext, board, row,
                col));
*/
        return view;
    }
/*
    Board getBoard() {
        return board;
    }

    static class ViewHolder {
        PieceImageView pieceViewItem;
    }
    
 */
}
