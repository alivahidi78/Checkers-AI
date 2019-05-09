package checkers;

import checkers.player.AIPlayer;
import checkers.player.HumanPlayer;
import checkers.player.Player;
import checkers.util.Color;
import checkers.util.Move;
import checkers.util.PieceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private PieceType[][] board;
    private Player turn;
    private List<Move> whitePotentialMoves;
    private List<Move> blackPotentialMoves;
    private List<Move> whitePotentialJumps;
    private List<Move> blackPotentialJumps;
    private Player player1;
    private Player player2;
    private Color winner;

    Game(Color playerColor, boolean isPvN) {
        board = new PieceType[8][8];
        whitePotentialMoves = new ArrayList<>();
        blackPotentialMoves = new ArrayList<>();
        whitePotentialJumps = new ArrayList<>();
        blackPotentialJumps = new ArrayList<>();
        player1 = new HumanPlayer(this, "" + playerColor + " Player", playerColor);
        if (isPvN)
            player2 = new AIPlayer(this, "Computer", playerColor.not());
        else
            player2 = new HumanPlayer(this,
                    "" + playerColor.not() + " Player", playerColor.not());
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
        updatePotentialMoves();
    }

    void start() {
        initiate();
        while (!isGameFinished()) {
            printBoard();
            System.out.println(turn.name + "'s turn:");
            Move move = turn.getNextMove();
            while (!isMoveValid(move)) {
                System.out.println("Move not possible!");
                move = turn.getNextMove();
            }
            move(move);
            updatePotentialMoves();
            updateTurn();
        }
        System.out.println(winner + " WINS!");
    }

    private boolean isGameFinished() {
        if (turn.color == Color.BLACK && blackPotentialJumps.isEmpty() &&
                blackPotentialMoves.isEmpty()) {
            winner = Color.WHITE;
            return true;
        }
        if (turn.color == Color.WHITE && whitePotentialJumps.isEmpty() &&
                whitePotentialMoves.isEmpty()) {
            winner = Color.BLACK;
            return true;
        }
        return false;
    }

    private boolean isMoveValid(Move move) {
        if (turn.color == Color.BLACK) {
            if (!blackPotentialJumps.isEmpty())
                return blackPotentialJumps.contains(move);
            else
                return blackPotentialMoves.contains(move);
        } else {
            if (!whitePotentialJumps.isEmpty())
                return whitePotentialJumps.contains(move);
            else
                return whitePotentialMoves.contains(move);
        }
    }

    private boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7)
            return false;
        if (fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7)
            return false;
        if (turn.color != board[fromRow][fromCol].getColor())
            return false;
        if (Math.abs(fromCol - toCol) != Math.abs(fromRow - toRow))
            return false;
        if (Math.abs(fromRow - toRow) > 2)
            return false;
        if (board[fromRow][fromCol] == PieceType.BLACK_MAN &&
                toRow < fromRow)
            return false;
        if (board[fromRow][fromCol] == PieceType.WHITE_MAN &&
                toRow > fromRow)
            return false;
        if (board[toRow][toCol] != PieceType.BLANK)
            return false;
        if (Math.abs(fromRow - toRow) == 2) {
            int midRow = (toRow - fromRow) / 2 + fromRow;
            int midCol = (toCol - fromCol) / 2 + fromCol;
            if (board[midRow][midCol].getColor() != turn.color.not())
                return false;
        }
        return true;
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
        if (turn.color == Color.WHITE && move.toRow == 0)
            board[move.toRow][move.toCol] = PieceType.WHITE_KING;
        if (turn.color == Color.BLACK && move.toRow == 7)
            board[move.toRow][move.toCol] = PieceType.BLACK_KING;
    }

    private void updateTurn() {
        if (turn.color == Color.BLACK && !blackPotentialJumps.isEmpty())
            return;
        if (turn.color == Color.WHITE && !whitePotentialJumps.isEmpty())
            return;
        if (turn == player1)
            turn = player2;
        else
            turn = player1;
    }

    private void updatePotentialMoves() {
        blackPotentialMoves.clear();
        whitePotentialMoves.clear();
        blackPotentialJumps.clear();
        whitePotentialJumps.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceType p = board[i][j];
                if (p.getColor() == Color.BLACK) {
                    updatePotentialMoves(Color.BLACK, i, j);
                } else if (p.getColor() == Color.WHITE) {
                    updatePotentialMoves(Color.WHITE, i, j);
                }
            }
        }
    }

    private void updatePotentialMoves(Color color, int i, int j) {
        List<Move> potentialMoves = color == Color.BLACK ?
                blackPotentialMoves : whitePotentialMoves;
        List<Move> potentialJumps = color == Color.BLACK ?
                blackPotentialJumps : whitePotentialJumps;
        int[] iDest = new int[]{i + 1, i - 1};
        int[] jDest = new int[]{j + 1, j - 1};
        for (int k : iDest)
            for (int w : jDest)
                if (canMove(i, j, k, w))
                    potentialMoves.add(new Move(i, j, k, w));
        iDest = new int[]{i + 2, i - 2};
        jDest = new int[]{j + 2, j - 2};
        for (int k : iDest)
            for (int w : jDest)
                if (canMove(i, j, k, w)) {
                    potentialJumps.add(new Move(i, j, k, w));
                }
    }


    private void printBoard() {
        System.out.print("\t ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + (i + 1) + " ");
        System.out.println("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print("" + (i + 1) + "\t ");
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
            System.out.print("\t" + (i + 1));
            System.out.println();
        }
        System.out.println();
        System.out.print("\t ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + (i + 1) + " ");
        System.out.println();
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
