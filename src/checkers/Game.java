package checkers;

import checkers.player.AIPlayer;
import checkers.player.HumanPlayer;
import checkers.player.Player;
import checkers.util.Color;
import checkers.util.Move;
import checkers.util.PieceType;

import java.util.Arrays;
import java.util.LinkedList;

public class Game {
    private PieceType[][] pieces;
    private Player turn;
    private LinkedList<Move> whitePotentialMoves;
    private LinkedList<Move> blackPotentialMoves;
    private Player player1;
    private Player player2;

    Game(Color playerColor, boolean isPvN) {
        pieces = new PieceType[8][8];
        whitePotentialMoves = new LinkedList<>();
        blackPotentialMoves = new LinkedList<>();
        player1 = new HumanPlayer(this, "Player1", playerColor);
        if (isPvN)
            player2 = new AIPlayer(this, "Computer", playerColor.not());
        else
            player2 = new HumanPlayer(this, "Player2", playerColor.not());
    }

    private void initiate() {
        for (PieceType[] pieceRow : pieces)
            Arrays.fill(pieceRow, PieceType.BLANK);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    pieces[i][j] = PieceType.BLACK_MAN;
        for (int i = 5; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    pieces[i][j] = PieceType.WHITE_MAN;
        turn = player1.color == Color.BLACK ? player1 : player2;
    }

    void start() {
        initiate();
        while (!isGameFinished()) {
            printBoard();
            System.out.println(turn.name + "'s turn:");
            Move move = turn.getNextMove();
            while (!canMove(move)) {
                System.out.println("Move not possible!");
                move = turn.getNextMove();
            }
            move(move);
            updateTurn();
            updatePotentialMoves();
        }
    }

    private void move(Move move) {
        PieceType p = pieces[move.fromRow][move.fromCol];
        pieces[move.toRow][move.toCol] = p;
        pieces[move.fromRow][move.fromCol] = PieceType.BLANK;
        if (move.toRow - move.fromRow == 2) {
            if (move.toCol - move.fromCol == 2)
                pieces[move.fromRow + 1][move.fromCol + 1] = PieceType.BLANK;
            else
                pieces[move.fromRow + 1][move.fromCol - 1] = PieceType.BLANK;
        } else if (move.toRow - move.fromRow == -2) {
            if (move.toCol - move.fromCol == 2)
                pieces[move.fromRow - 1][move.fromCol + 1] = PieceType.BLANK;
            else
                pieces[move.fromRow - 1][move.fromCol - 1] = PieceType.BLANK;
        }
    }

    private boolean isGameFinished() {
        return false;//TODO
    }

    private boolean canMove(Move move) {
        //TODO
        return true;
    }

    private void updateTurn() {
        //TODO
        if (turn == player1)
            turn = player2;
        else
            turn = player1;
    }

    private void updatePotentialMoves() {
        //TODO
    }

    private void printBoard() {
        System.out.print("\t|");
        for (int i = 0; i < 8; i++)
            System.out.print("" + i + "|");
        System.out.println("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print("" + i + "\t|");
            for (int j = 0; j < 8; j++) {
                PieceType p = pieces[i][j];
                if (p == PieceType.BLANK)
                    System.out.print(" |");
                else if (p == PieceType.BLACK_MAN)
                    System.out.print("1|");
                else if (p == PieceType.WHITE_MAN)
                    System.out.print("2|");
            }
            System.out.println();
        }
    }

    //    private JSONObject collectBoard() {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray board = new JSONArray();
//        for (int i = 0; i < 8; i++) {
//            JSONArray row = new JSONArray();
//            for (int j = 0; j < 8; j++) {
//                row.add(pieces[i][j].toJSON());
//            }
//            board.add(row);
//        }
//        jsonObject.put("board", board);
//        jsonObject.put("label", "user");
//        jsonObject.put("turn", "user");
//        return jsonObject;
//    }

}
