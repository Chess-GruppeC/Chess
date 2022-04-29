package at.aau.se2.chessify.AndroidGameUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.R;
import at.aau.se2.chessify.chessLogic.board.ChessBoard;
import at.aau.se2.chessify.chessLogic.board.Location;
import at.aau.se2.chessify.chessLogic.pieces.Bishop;
import at.aau.se2.chessify.chessLogic.pieces.ChessPiece;
import at.aau.se2.chessify.chessLogic.pieces.King;
import at.aau.se2.chessify.chessLogic.pieces.Knight;
import at.aau.se2.chessify.chessLogic.pieces.Pawn;
import at.aau.se2.chessify.chessLogic.pieces.PieceColour;
import at.aau.se2.chessify.chessLogic.pieces.Queen;
import at.aau.se2.chessify.chessLogic.pieces.Rook;


public class BoardView extends AppCompatActivity implements View.OnClickListener{

    private int currentProgress = 0;
    ProgressBar specialMoveBar;

    public TextView[][] BoardView = new TextView[8][8];
    public TextView[][] BoardViewBackground = new TextView[8][8];


    ChessBoard chessBoard = new ChessBoard();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        initializeBoard();


    }


    private void initializeBoard() {

        BoardView[0][0] = (TextView) findViewById(R.id.R00);
        BoardViewBackground[0][0] = (TextView) findViewById(R.id.R000);
        BoardView[1][0] = (TextView) findViewById(R.id.R10);
        BoardViewBackground[1][0] = (TextView) findViewById(R.id.R010);
        BoardView[2][0] = (TextView) findViewById(R.id.R20);
        BoardViewBackground[2][0] = (TextView) findViewById(R.id.R020);
        BoardView[3][0] = (TextView) findViewById(R.id.R30);
        BoardViewBackground[3][0] = (TextView) findViewById(R.id.R030);
        BoardView[4][0] = (TextView) findViewById(R.id.R40);
        BoardViewBackground[4][0] = (TextView) findViewById(R.id.R040);
        BoardView[5][0] = (TextView) findViewById(R.id.R50);
        BoardViewBackground[5][0] = (TextView) findViewById(R.id.R050);
        BoardView[6][0] = (TextView) findViewById(R.id.R60);
        BoardViewBackground[6][0] = (TextView) findViewById(R.id.R060);
        BoardView[7][0] = (TextView) findViewById(R.id.R70);
        BoardViewBackground[7][0] = (TextView) findViewById(R.id.R070);

        BoardView[0][1] = (TextView) findViewById(R.id.R01);
        BoardViewBackground[0][1] = (TextView) findViewById(R.id.R001);
        BoardView[1][1] = (TextView) findViewById(R.id.R11);
        BoardViewBackground[1][1] = (TextView) findViewById(R.id.R011);
        BoardView[2][1] = (TextView) findViewById(R.id.R21);
        BoardViewBackground[2][1] = (TextView) findViewById(R.id.R021);
        BoardView[3][1] = (TextView) findViewById(R.id.R31);
        BoardViewBackground[3][1] = (TextView) findViewById(R.id.R031);
        BoardView[4][1] = (TextView) findViewById(R.id.R41);
        BoardViewBackground[4][1] = (TextView) findViewById(R.id.R041);
        BoardView[5][1] = (TextView) findViewById(R.id.R51);
        BoardViewBackground[5][1] = (TextView) findViewById(R.id.R051);
        BoardView[6][1] = (TextView) findViewById(R.id.R61);
        BoardViewBackground[6][1] = (TextView) findViewById(R.id.R061);
        BoardView[7][1] = (TextView) findViewById(R.id.R71);
        BoardViewBackground[7][1] = (TextView) findViewById(R.id.R071);

        BoardView[0][2] = (TextView) findViewById(R.id.R02);
        BoardViewBackground[0][2] = (TextView) findViewById(R.id.R002);
        BoardView[1][2] = (TextView) findViewById(R.id.R12);
        BoardViewBackground[1][2] = (TextView) findViewById(R.id.R012);
        BoardView[2][2] = (TextView) findViewById(R.id.R22);
        BoardViewBackground[2][2] = (TextView) findViewById(R.id.R022);
        BoardView[3][2] = (TextView) findViewById(R.id.R32);
        BoardViewBackground[3][2] = (TextView) findViewById(R.id.R032);
        BoardView[4][2] = (TextView) findViewById(R.id.R42);
        BoardViewBackground[4][2] = (TextView) findViewById(R.id.R042);
        BoardView[5][2] = (TextView) findViewById(R.id.R52);
        BoardViewBackground[5][2] = (TextView) findViewById(R.id.R052);
        BoardView[6][2] = (TextView) findViewById(R.id.R62);
        BoardViewBackground[6][2] = (TextView) findViewById(R.id.R062);
        BoardView[7][2] = (TextView) findViewById(R.id.R72);
        BoardViewBackground[7][2] = (TextView) findViewById(R.id.R072);

        BoardView[0][3] = (TextView) findViewById(R.id.R03);
        BoardViewBackground[0][3] = (TextView) findViewById(R.id.R003);
        BoardView[1][3] = (TextView) findViewById(R.id.R13);
        BoardViewBackground[1][3] = (TextView) findViewById(R.id.R013);
        BoardView[2][3] = (TextView) findViewById(R.id.R23);
        BoardViewBackground[2][3] = (TextView) findViewById(R.id.R023);
        BoardView[3][3] = (TextView) findViewById(R.id.R33);
        BoardViewBackground[3][3] = (TextView) findViewById(R.id.R033);
        BoardView[4][3] = (TextView) findViewById(R.id.R43);
        BoardViewBackground[4][3] = (TextView) findViewById(R.id.R043);
        BoardView[5][3] = (TextView) findViewById(R.id.R53);
        BoardViewBackground[5][3] = (TextView) findViewById(R.id.R053);
        BoardView[6][3] = (TextView) findViewById(R.id.R63);
        BoardViewBackground[6][3] = (TextView) findViewById(R.id.R063);
        BoardView[7][3] = (TextView) findViewById(R.id.R73);
        BoardViewBackground[7][3] = (TextView) findViewById(R.id.R073);

        BoardView[0][4] = (TextView) findViewById(R.id.R04);
        BoardViewBackground[0][4] = (TextView) findViewById(R.id.R004);
        BoardView[1][4] = (TextView) findViewById(R.id.R14);
        BoardViewBackground[1][4] = (TextView) findViewById(R.id.R014);
        BoardView[2][4] = (TextView) findViewById(R.id.R24);
        BoardViewBackground[2][4] = (TextView) findViewById(R.id.R024);
        BoardView[3][4] = (TextView) findViewById(R.id.R34);
        BoardViewBackground[3][4] = (TextView) findViewById(R.id.R034);
        BoardView[4][4] = (TextView) findViewById(R.id.R44);
        BoardViewBackground[4][4] = (TextView) findViewById(R.id.R044);
        BoardView[5][4] = (TextView) findViewById(R.id.R54);
        BoardViewBackground[5][4] = (TextView) findViewById(R.id.R054);
        BoardView[6][4] = (TextView) findViewById(R.id.R64);
        BoardViewBackground[6][4] = (TextView) findViewById(R.id.R064);
        BoardView[7][4] = (TextView) findViewById(R.id.R74);
        BoardViewBackground[7][4] = (TextView) findViewById(R.id.R074);

        BoardView[0][5] = (TextView) findViewById(R.id.R05);
        BoardViewBackground[0][5] = (TextView) findViewById(R.id.R005);
        BoardView[1][5] = (TextView) findViewById(R.id.R15);
        BoardViewBackground[1][5] = (TextView) findViewById(R.id.R015);
        BoardView[2][5] = (TextView) findViewById(R.id.R25);
        BoardViewBackground[2][5] = (TextView) findViewById(R.id.R025);
        BoardView[3][5] = (TextView) findViewById(R.id.R35);
        BoardViewBackground[3][5] = (TextView) findViewById(R.id.R035);
        BoardView[4][5] = (TextView) findViewById(R.id.R45);
        BoardViewBackground[4][5] = (TextView) findViewById(R.id.R045);
        BoardView[5][5] = (TextView) findViewById(R.id.R55);
        BoardViewBackground[5][5] = (TextView) findViewById(R.id.R055);
        BoardView[6][5] = (TextView) findViewById(R.id.R65);
        BoardViewBackground[6][5] = (TextView) findViewById(R.id.R065);
        BoardView[7][5] = (TextView) findViewById(R.id.R75);
        BoardViewBackground[7][5] = (TextView) findViewById(R.id.R075);

        BoardView[0][6] = (TextView) findViewById(R.id.R06);
        BoardViewBackground[0][6] = (TextView) findViewById(R.id.R006);
        BoardView[1][6] = (TextView) findViewById(R.id.R16);
        BoardViewBackground[1][6] = (TextView) findViewById(R.id.R016);
        BoardView[2][6] = (TextView) findViewById(R.id.R26);
        BoardViewBackground[2][6] = (TextView) findViewById(R.id.R026);
        BoardView[3][6] = (TextView) findViewById(R.id.R36);
        BoardViewBackground[3][6] = (TextView) findViewById(R.id.R036);
        BoardView[4][6] = (TextView) findViewById(R.id.R46);
        BoardViewBackground[4][6] = (TextView) findViewById(R.id.R046);
        BoardView[5][6] = (TextView) findViewById(R.id.R56);
        BoardViewBackground[5][6] = (TextView) findViewById(R.id.R056);
        BoardView[6][6] = (TextView) findViewById(R.id.R66);
        BoardViewBackground[6][6] = (TextView) findViewById(R.id.R066);
        BoardView[7][6] = (TextView) findViewById(R.id.R76);
        BoardViewBackground[7][6] = (TextView) findViewById(R.id.R076);

        BoardView[0][7] = (TextView) findViewById(R.id.R07);
        BoardViewBackground[0][7] = (TextView) findViewById(R.id.R007);
        BoardView[1][7] = (TextView) findViewById(R.id.R17);
        BoardViewBackground[1][7] = (TextView) findViewById(R.id.R017);
        BoardView[2][7] = (TextView) findViewById(R.id.R27);
        BoardViewBackground[2][7] = (TextView) findViewById(R.id.R027);
        BoardView[3][7] = (TextView) findViewById(R.id.R37);
        BoardViewBackground[3][7] = (TextView) findViewById(R.id.R037);
        BoardView[4][7] = (TextView) findViewById(R.id.R47);
        BoardViewBackground[4][7] = (TextView) findViewById(R.id.R047);
        BoardView[5][7] = (TextView) findViewById(R.id.R57);
        BoardViewBackground[5][7] = (TextView) findViewById(R.id.R057);
        BoardView[6][7] = (TextView) findViewById(R.id.R67);
        BoardViewBackground[6][7] = (TextView) findViewById(R.id.R067);
        BoardView[7][7] = (TextView) findViewById(R.id.R77);
        BoardViewBackground[7][7] = (TextView) findViewById(R.id.R077);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece chessPiece = chessBoard.getPieceAtLocation(new Location(i,j));

                if (chessPiece != null){
                    if (chessPiece.getClass() == Pawn.class && chessPiece.getColour() == PieceColour.BLACK){
                        BoardView[i][j].setBackgroundResource(R.drawable.black_pawn);
                    }
                    if (chessPiece.getClass() == Pawn.class && chessPiece.getColour() == PieceColour.WHITE){
                        BoardView[i][j].setBackgroundResource(R.drawable.white_pawn);
                    }
                    if (chessPiece.getClass() == Rook.class && chessPiece.getColour() == PieceColour.BLACK){
                        BoardView[i][j].setBackgroundResource(R.drawable.black_rook);
                    }
                    if (chessPiece.getClass() == Rook.class && chessPiece.getColour() == PieceColour.WHITE){
                        BoardView[i][j].setBackgroundResource(R.drawable.white_rook);
                    }
                    if (chessPiece.getClass() == Bishop.class && chessPiece.getColour() == PieceColour.BLACK){
                        BoardView[i][j].setBackgroundResource(R.drawable.black_bishop);
                    }
                    if (chessPiece.getClass() == Bishop.class && chessPiece.getColour() == PieceColour.WHITE){
                        BoardView[i][j].setBackgroundResource(R.drawable.white_bishop);
                    }
                    if (chessPiece.getClass() == Knight.class && chessPiece.getColour() == PieceColour.BLACK){
                        BoardView[i][j].setBackgroundResource(R.drawable.black_knight);
                    }
                    if (chessPiece.getClass() == Knight.class && chessPiece.getColour() == PieceColour.WHITE){
                        BoardView[i][j].setBackgroundResource(R.drawable.white_knight);
                    }
                    if (chessPiece.getClass() == King.class && chessPiece.getColour() == PieceColour.BLACK){
                        BoardView[i][j].setBackgroundResource(R.drawable.black_king);
                    }
                    if (chessPiece.getClass() == King.class && chessPiece.getColour() == PieceColour.WHITE){
                        BoardView[i][j].setBackgroundResource(R.drawable.white_king);
                    }
                    if (chessPiece.getClass() == Queen.class && chessPiece.getColour() == PieceColour.BLACK){
                        BoardView[i][j].setBackgroundResource(R.drawable.black_queen);
                    }
                    if (chessPiece.getClass() == Queen.class && chessPiece.getColour() == PieceColour.WHITE){
                        BoardView[i][j].setBackgroundResource(R.drawable.white_queen);
                    }
                }
            }
        }

       // setAltBoard();
    }
    private void setAltBoard(){

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

            }
        }



    }

    @Override
    public void onClick(View view) {
        //add clicked position
        // if selected ....
    }

    //method:
    //TODO: safe board
    //TODO: undo
    //TODO: choice
    //TODO: set color at allowed position
    //TODO: is King in danger

    public void initSpecialmoveBar() {
        specialMoveBar = findViewById(R.id.special_move_bar);
    }

    /* Konzept der Special Move Bar
    public void fillSpecialMoveBar(){

        if (piece.getPieceId().contains("bp"))
            currentProgress = currentProgress + 2;
        else if (piece.getPieceId().contains("bn") || piece.getPieceId().contains("bb"))
            currentProgress = currentProgress + 5;
        else if (piece.getPieceId().contains("br"))
            currentProgress = currentProgress + 7;
        else if (piece.getPieceId().contains("bq"))
            currentProgress = currentProgress + 9;

        specialMoveBar.setProgress(currentProgress);
        specialMoveBar.setMax(30);
    }
*/



}
