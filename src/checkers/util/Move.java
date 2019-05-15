package checkers.util;

import java.util.Objects;

public class Move {
    public int fromRow;
    public int fromCol;
    public int toRow;
    public int toCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    public Move(int[] move) {
        this(move[0], move[1], move[2], move[3]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return fromRow == move.fromRow &&
                fromCol == move.fromCol &&
                toRow == move.toRow &&
                toCol == move.toCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromRow, fromCol, toRow, toCol);
    }
}
