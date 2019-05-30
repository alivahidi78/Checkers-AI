package checkers.util;

import checkers.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database {
    private static Database db = new Database();
    private Board board = new Board();
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

    public boolean hasTurnChanged() {
        return turnChanged;
    }

    void initialize(Player player1, Player player2) {
        turn = player1.color == Color.BLACK ? player1 : player2;
        board.initiate();
        lastMove = new Move(-1, -1, -1, -1);
        board.updateChoices(potentialMoves, potentialJumps, Color.BLACK,
                turnChanged, lastMove);
    }

    void move(Move move, Player player1, Player player2) {
        turnChanged = board.move(move);
        if (turnChanged)
            if (turn == player1)
                turn = player2;
            else
                turn = player1;
        lastMove = move;
        board.updateChoices(potentialMoves, potentialJumps, turn.color, turnChanged, lastMove);
    }

    List<Move> getPotentialMoves() {
        return Collections.unmodifiableList(potentialMoves);
    }

    List<Move> getPotentialJumps() {
        return Collections.unmodifiableList(potentialJumps);
    }

    public Player getTurn() {
        return turn;
    }

    public Board getBoard() {
        return board.clone();
    }

    public Move getLastMove() {
        return lastMove;
    }
}
