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
    private PieceType[][] board;
    private Player turn;
    private LinkedList<Move> whitePotentialMoves;
    private LinkedList<Move> blackPotentialMoves;
    private Player player1;
    private Player player2;

    Game(Color playerColor, boolean isPvN) {
        board = new PieceType[8][8];
        whitePotentialMoves = new LinkedList<>();
        blackPotentialMoves = new LinkedList<>();
        player1 = new HumanPlayer(this, "Player1", playerColor);
        if (isPvN)
            player2 = new AIPlayer(this, "Computer", playerColor.not());
        else
            player2 = new HumanPlayer(this, "Player2", playerColor.not());
    }

    private void initiate() {
        for (PieceType[] pieceRow : board)
            Arrays.fill(pieceRow, PieceType.BLANK);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    board[i][j] = PieceType.BLACK_MAN;
        for (int i = 5; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    board[i][j] = PieceType.WHITE_MAN;
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
        PieceType p = board[move.fromRow][move.fromCol];
        board[move.toRow][move.toCol] = p;
        board[move.fromRow][move.fromCol] = PieceType.BLANK;
        if (move.toRow - move.fromRow == 2) {
            if (move.toCol - move.fromCol == 2)
                board[move.fromRow + 1][move.fromCol + 1] = PieceType.BLANK;
            else
                board[move.fromRow + 1][move.fromCol - 1] = PieceType.BLANK;
        } else if (move.toRow - move.fromRow == -2) {
            if (move.toCol - move.fromCol == 2)
                board[move.fromRow - 1][move.fromCol + 1] = PieceType.BLANK;
            else
                board[move.fromRow - 1][move.fromCol - 1] = PieceType.BLANK;
        }
    }

    private boolean isGameFinished() {
        return false;//TODO
    }

    private boolean canMove(Move move) {
        if (move.fromRow < 0 || move.fromRow > 7 || move.toRow < 0 || move.toRow > 7)
            return false;
        if (move.fromCol < 0 || move.fromCol > 7 || move.toCol < 0 || move.toCol > 7)
            return false;
        if (turn.color != board[move.fromRow][move.fromCol].getColor())
            return false;
        if (Math.abs(move.fromCol - move.toCol) != Math.abs(move.fromRow - move.toRow))
            return false;
        if (Math.abs(move.fromRow - move.toRow) > 2)
            return false;
        if (board[move.fromRow][move.fromCol] == PieceType.BLACK_MAN &&
                move.toRow < move.fromRow)
            return false;
        if (board[move.fromRow][move.fromCol] == PieceType.WHITE_MAN &&
                move.toRow > move.fromRow)
            return false;
        if (board[move.toRow][move.toCol] != PieceType.BLANK)
            return false;
        if (Math.abs(move.fromRow - move.toRow) == 2) {
            int midRow = (move.toRow - move.fromRow) / 2 + move.fromRow;
            int midCol = (move.toCol - move.fromCol) / 2 + move.fromCol;
            if (board[midRow][midCol].getColor() != turn.color.not())
                return false;
        }
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
        System.out.print("\t ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + i + " ");
        System.out.println("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print("" + i + "\t ");
            for (int j = 0; j < 8; j++) {
                PieceType p = board[i][j];
                if ((i + j) % 2 == 0)
                    System.out.print("\u001B[47m");
                if (p == PieceType.BLANK)
                    System.out.print("   ");
                else if (p == PieceType.BLACK_MAN)
                    System.out.print("\u001B[34m" + " M" + "\u001B[0m" + " ");
                else if (p == PieceType.WHITE_MAN)
                    System.out.print("\u001B[32m" + " M" + "\u001B[0m" + " ");
                else if (p == PieceType.BLACK_KING)
                    System.out.print("\u001B[34m" + " K" + "\u001B[0m" + " ");
                else if (p == PieceType.WHITE_KING)
                    System.out.print("\u001B[32m" + " K" + "\u001B[0m" + " ");
                System.out.print("\u001B[0m");
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
//                row.add(board[i][j].toJSON());
//            }
//            board.add(row);
//        }
//        jsonObject.put("board", board);
//        jsonObject.put("label", "user");
//        jsonObject.put("turn", "user");
//        return jsonObject;
//    }

}
