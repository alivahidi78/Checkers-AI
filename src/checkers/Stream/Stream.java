package checkers.Stream;

import checkers.util.Move;
import checkers.util.PieceType;

public interface Stream {
    public void printData(PieceType[][] board, Move lastMove);

    public int[] scanData();
}
