package checkers.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Cloneable {
    private PieceType[][] table;

    Board() {
        table = new PieceType[8][8];
    }

    public PieceType get(int row, int col) {
        return table[row][col];
    }

    void initiate() {
        for (PieceType[] pieceRow : table)
            Arrays.fill(pieceRow, PieceType.BLANK);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    table[i][j] = PieceType.BLACK_MAN;
        for (int i = 5; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    table[i][j] = PieceType.WHITE_MAN;
    }

    void updateChoices(List<Move> potentialMoves,
                       List<Move> potentialJumps, Color currTurn,
                       boolean turnChanged, Move lastMove) {
        potentialMoves.clear();
        potentialJumps.clear();
        if (!turnChanged) {
            updatePotentialJumps(potentialJumps, lastMove.toRow, lastMove.toCol);
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    PieceType p = table[i][j];
                    if (p.getColor() == currTurn) {
                        updatePotentialMoves(potentialMoves, i, j);
                        updatePotentialJumps(potentialJumps, i, j);
                    }
                }
            }
        }
    }

    private void updatePotentialMoves(List<Move> potentialMoves, int i, int j) {
        int[] iDest = new int[]{i + 1, i - 1};
        int[] jDest = new int[]{j + 1, j - 1};
        for (int k : iDest)
            for (int w : jDest)
                if (canMove(i, j, k, w))
                    potentialMoves.add(new Move(i, j, k, w));
    }

    private void updatePotentialJumps(List<Move> potentialJumps, int i, int j) {
        int[] iDest = new int[]{i + 2, i - 2};
        int[] jDest = new int[]{j + 2, j - 2};
        for (int k : iDest)
            for (int w : jDest)
                if (canMove(i, j, k, w)) {
                    potentialJumps.add(new Move(i, j, k, w));
                }
    }

    private boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
        Color color = table[fromRow][fromCol].getColor();
        if (fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7)
            return false;
        if (fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7)
            return false;
        if (table[toRow][toCol] != PieceType.BLANK)
            return false;
        if (table[fromRow][fromCol] == PieceType.BLACK_MAN &&
                toRow < fromRow)
            return false;
        if (table[fromRow][fromCol] == PieceType.WHITE_MAN &&
                toRow > fromRow)
            return false;
        if (Math.abs(fromRow - toRow) == 2) {
            int midRow = (toRow - fromRow) / 2 + fromRow;
            int midCol = (toCol - fromCol) / 2 + fromCol;
            PieceType middle = table[midRow][midCol];
            if (middle == PieceType.BLANK || middle.getColor() == color)
                return false;
        }
        return true;
    }

    @Override
    protected Board clone() {
        Board clone;
        try {
            clone = (Board) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
        PieceType[][] table = new PieceType[this.table.length][];
        for (int i = 0; i < this.table.length; i++)
            table[i] = Arrays.copyOf(this.table[i], this.table[i].length);
        clone.table = table;
        return clone;
    }

    /**
     * @return turn changes or not
     */
    boolean move(Move move) {
        PieceType p = table[move.fromRow][move.fromCol];
        table[move.toRow][move.toCol] = p;
        table[move.fromRow][move.fromCol] = PieceType.BLANK;
        if (move.toRow - move.fromRow == 2) {
            if (move.toCol - move.fromCol == 2)
                table[move.fromRow + 1][move.fromCol + 1] = PieceType.BLANK;
            else
                table[move.fromRow + 1][move.fromCol - 1] = PieceType.BLANK;
        } else if (move.toRow - move.fromRow == -2) {
            if (move.toCol - move.fromCol == 2)
                table[move.fromRow - 1][move.fromCol + 1] = PieceType.BLANK;
            else
                table[move.fromRow - 1][move.fromCol - 1] = PieceType.BLANK;
        }

        PieceType moved = table[move.toRow][move.toCol];
        if (move.toRow == 0 && moved == PieceType.WHITE_MAN)
            table[move.toRow][move.toCol] = PieceType.WHITE_KING;
        else if (move.toRow == 7 && moved == PieceType.BLACK_MAN)
            table[move.toRow][move.toCol] = PieceType.BLACK_KING;

        else if (move.isJump()) {
            List<Move> pj = new ArrayList<>();
            updatePotentialJumps(pj, move.toRow, move.toCol);
            if (!pj.isEmpty())
                return false;
        }
        return true;
    }
}
