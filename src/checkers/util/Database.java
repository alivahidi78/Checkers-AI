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
    private boolean turnChanged = true;

    private Database() {
    }

    public static Database getInstance() {
        return db;
    }

    public boolean isTurnChanged() {
        return turnChanged;
    }

    void initialize(Player player1, Player player2) {
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
        lastMove = new Move(-1,-1,-1,-1);
        Util.updateChoices(board, potentialMoves, potentialJumps, Color.BLACK,
                turnChanged,lastMove);
    }

    void move(Move move, Player player1, Player player2) {
        turnChanged = Util.move(board, move);
        if (turnChanged)
            if (turn == player1)
                turn = player2;
            else
                turn = player1;
        lastMove = move;
        Util.updateChoices(board, potentialMoves, potentialJumps, turn.color, turnChanged, lastMove);
    }

    public List<Move> getPotentialMoves() {
        return Collections.unmodifiableList(potentialMoves);
    }

    public List<Move> getPotentialJumps() {
        return Collections.unmodifiableList(potentialJumps);
    }

    public Player getTurn() {
        return turn;
    }

    public PieceType[][] getBoard() {
        return Util.cloneBoard(board);
    }

    public Move getLastMove() {
        return lastMove;
    }
}
