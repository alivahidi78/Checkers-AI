package checkers.util;

import checkers.player.Player;

public class Database {
    private static Database db = new Database();
    private PieceType[][] board;
    private Move lastMove;
    private Player turn;
    private Database() {

    }

    public static Database getInstance() {
        return db;
    }

    public Player getTurn() {
        return turn;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }

    public PieceType[][] getBoard() {
        return board;
    }

    public void setBoard(PieceType[][] board) {
        this.board = board;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }
}
