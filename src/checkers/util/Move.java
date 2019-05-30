package checkers.util;

import java.util.Objects;

public class Move {
    public final int fromRow;
    public final int fromCol;
    public final int toRow;
    public final int toCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    public Move(int[] move) {
        this(move[0], move[1], move[2], move[3]);
    }

    public boolean isJump() {
        return Math.abs(toCol - fromCol) == 2;
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
    public String toString() {
        return "[ " + fromRow + " ," + fromCol + " ] ->" +
                " [ " + toRow + " ," + toCol + " ]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromRow, fromCol, toRow, toCol);
    }
}
