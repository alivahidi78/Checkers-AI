package checkers.util;

import checkers.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Database {
    private static Database db = new Database();
    private PieceType[][] board = new PieceType[8][8];
    private List<Move> potentialMoves = new ArrayList<>();
    private List<Move> potentialJumps = new ArrayList<>();
    private Move lastMove;
    private Player turn;

    private Database() {
    }

    public static Database getInstance() {
        return db;
    }

    public List<Move> getPotentialMoves() {
        return Collections.unmodifiableList(potentialMoves);
    }

    public List<Move> getPotentialJumps() {
        return Collections.unmodifiableList(potentialJumps);
    }

    public void initialize(Player player1, Player player2) {
        turn = player1.color == Color.BLACK ? player1 : player2;
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
        updateChoices(Color.BLACK);
        lastMove = new Move(-1, -1, -1, -1);
    }

    public Player getTurn() {
        return turn;
    }

    public PieceType[][] getBoard() {
        PieceType[][] board = new PieceType[this.board.length][];
        for (int i = 0; i < this.board.length; i++)
            board[i] = Arrays.copyOf(this.board[i], this.board[i].length);
        return board;
    }

    public void move(Move move) {
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
        lastMove = move;
    }

    public void updateTurn(Player player1, Player player2) {
        PieceType moved = board[lastMove.toRow][lastMove.toCol];

        if (lastMove.toRow == 0 && moved == PieceType.WHITE_MAN)
            board[lastMove.toRow][lastMove.toCol] = PieceType.WHITE_KING;
        else if (lastMove.toRow == 7 && moved == PieceType.BLACK_MAN)
            board[lastMove.toRow][lastMove.toCol] = PieceType.BLACK_KING;

        else if (Math.abs(lastMove.toCol - lastMove.fromCol) == 2) {
            potentialMoves.clear();
            potentialJumps.clear();
            Util.updatePotentialJumps(board, potentialJumps, lastMove.toRow, lastMove.toCol);
            if (!potentialJumps.isEmpty())
                return;
        }
        if (turn == player1)
            turn = player2;
        else
            turn = player1;
        updateChoices(turn.color);
    }

    public Move getLastMove() {
        return lastMove;
    }

    private void updateChoices(Color c) {
        Util.updateChoices(board, potentialMoves, potentialJumps, c);
    }

    public boolean isMoveValid(Move move) {
        if (!potentialJumps.isEmpty())
            return potentialJumps.contains(move);
        else
            return potentialMoves.contains(move);
    }
}
