package at.aau.se2.chessify.AndroidGameUI;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.AndroidGameUI.altLogic.board.Location;
import at.aau.se2.chessify.AndroidGameUI.altLogic.pieces.Bishop;
import at.aau.se2.chessify.AndroidGameUI.altLogic.pieces.ChessPiece;
import at.aau.se2.chessify.AndroidGameUI.altLogic.pieces.King;
import at.aau.se2.chessify.AndroidGameUI.altLogic.pieces.Knight;
import at.aau.se2.chessify.AndroidGameUI.altLogic.pieces.Pawn;
import at.aau.se2.chessify.AndroidGameUI.altLogic.pieces.Queen;
import at.aau.se2.chessify.AndroidGameUI.altLogic.pieces.Rook;
import at.aau.se2.chessify.R;


public class BoardView extends AppCompatActivity implements View.OnClickListener{

    public TextView[][] BoardView = new TextView[8][8];
    public TextView[][] BoardViewBackground = new TextView[8][8];

    public Location[][] Board = new Location[8][8];
    public Location[][] Board2 = new Location[8][8];

    King king = new King();
    Queen queen = new Queen();
    Bishop bishop= new Bishop();
    Rook rook= new Rook();
    Knight knight = new Knight();
    Pawn pawn = new Pawn();

    ChessPiece bKing;
    ChessPiece wKing;

    ChessPiece bQueen;
    ChessPiece wQueen;

    ChessPiece bKnight1;
    ChessPiece bKnight2;
    ChessPiece wKnight1;
    ChessPiece wKnight2;

    ChessPiece bRook1;
    ChessPiece bRook2;
    ChessPiece wRook1;
    ChessPiece wRook2;

    ChessPiece bBishop1;
    ChessPiece bBishop2;
    ChessPiece wBishop1;
    ChessPiece wBishop2;

    ChessPiece bPawn1;
    ChessPiece bPawn2;
    ChessPiece bPawn3;
    ChessPiece bPawn4;
    ChessPiece bPawn5;
    ChessPiece bPawn6;
    ChessPiece bPawn7;
    ChessPiece bPawn8;

    ChessPiece wPawn1;
    ChessPiece wPawn2;
    ChessPiece wPawn3;
    ChessPiece wPawn4;
    ChessPiece wPawn5;
    ChessPiece wPawn6;
    ChessPiece wPawn7;
    ChessPiece wPawn8;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        initializeBoard();


    }


    private void initializeBoard() {
        //init all Pieces
        bKing = new King();
        wKing = new King();

        bQueen = new Queen();
        wQueen = new Queen();

        bRook1 = new Rook();
        bRook2 = new Rook();
        wRook1 = new Rook();
        wRook2 = new Rook();

        bKnight1 = new Knight();
        bKnight2 = new Knight();
        wKnight1 = new Knight();
        wKnight2 = new Knight();

        bBishop1 = new Bishop();
        bBishop2 = new Bishop();
        wBishop1 = new Bishop();
        wBishop2 = new Bishop();

        bPawn1 = new Pawn();
        bPawn2 = new Pawn();
        bPawn3 = new Pawn();
        bPawn4 = new Pawn();
        bPawn5 = new Pawn();
        bPawn6 = new Pawn();
        bPawn7 = new Pawn();
        bPawn8 = new Pawn();

        wPawn1 = new Pawn();
        wPawn2 = new Pawn();
        wPawn3 = new Pawn();
        wPawn4 = new Pawn();
        wPawn5 = new Pawn();
        wPawn6 = new Pawn();
        wPawn7 = new Pawn();
        wPawn8 = new Pawn();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Board[i][j] = new Location(i,j);
                Board[i][j] = new Location(i,j);
            }
        }

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


        setAltBoard();
    }
    private void setAltBoard(){

        //setPawn
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j < 2; j++) {
                BoardView[i][j].setBackgroundResource(R.drawable.black_pawn);
            }
            for (int j = 6; j < 7; j++) {
                BoardView[i][j].setBackgroundResource(R.drawable.white_pawn);
            }
        }

        //Rook

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 1; j++) {
                if (i == 0 || i == 7) {
                    BoardView[i][j].setBackgroundResource(R.drawable.black_rook);
                }
            }
            for (int j = 7; j < 8; j++) {
                if (i == 0 || i == 7){
                BoardView[i][j].setBackgroundResource(R.drawable.white_rook);

                }
            }
        }

        //Knight
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 1; j++) {
                if (i == 1 || i == 6) {
                    BoardView[i][j].setBackgroundResource(R.drawable.black_knight);
                }
            }
            for (int j = 7; j < 8; j++) {
                if (i == 1 || i == 6){
                    BoardView[i][j].setBackgroundResource(R.drawable.white_knight);
                }
            }
        }
        //Bishop
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 1; j++) {
                if (i == 2 || i == 5) {
                    BoardView[i][j].setBackgroundResource(R.drawable.black_bishop);
                }
            }
            for (int j = 7; j < 8; j++) {
                if (i == 2 || i == 5){
                    BoardView[i][j].setBackgroundResource(R.drawable.white_bishop);

                }
            }
        }
        //King
        BoardView[3][0].setBackgroundResource(R.drawable.black_king);
        BoardView[4][7].setBackgroundResource(R.drawable.white_king);
        //Queen
        BoardView[4][0].setBackgroundResource(R.drawable.black_queen);
        BoardView[3][7].setBackgroundResource(R.drawable.white_queen);



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



}
