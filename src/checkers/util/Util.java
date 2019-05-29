package checkers.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static void updateChoices(PieceType[][] board, List<Move> potentialMoves,
                                     List<Move> potentialJumps, Color currTurn,
                                     boolean turnChanged, Move lastMove) {
        potentialMoves.clear();
        potentialJumps.clear();
        if (!turnChanged) {
            Util.updatePotentialJumps(board, potentialJumps, lastMove.toRow, lastMove.toCol);
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    PieceType p = board[i][j];
                    if (p.getColor() == currTurn) {
                        updatePotentialMoves(board, potentialMoves, i, j);
                        updatePotentialJumps(board, potentialJumps, i, j);
                    }
                }
            }
        }
    }

    public static boolean isGameFinished(PieceType[][] board, List<Move> potentialMoves, List<Move> potentialJumps) {
        return potentialJumps.isEmpty() &&
                potentialMoves.isEmpty();
    }

    public static void updatePotentialMoves(PieceType[][] board, List<Move> potentialMoves, int i, int j) {
        int[] iDest = new int[]{i + 1, i - 1};
        int[] jDest = new int[]{j + 1, j - 1};
        for (int k : iDest)
            for (int w : jDest)
                if (canMove(board, i, j, k, w))
                    potentialMoves.add(new Move(i, j, k, w));
    }

    public static void updatePotentialJumps(PieceType[][] board, List<Move> potentialJumps, int i, int j) {
        int[] iDest = new int[]{i + 2, i - 2};
        int[] jDest = new int[]{j + 2, j - 2};
        for (int k : iDest)
            for (int w : jDest)
                if (canMove(board, i, j, k, w)) {
                    potentialJumps.add(new Move(i, j, k, w));
                }
    }

    private static boolean canMove(PieceType[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        Color color = board[fromRow][fromCol].getColor();
        if (fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7)
            return false;
        if (fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7)
            return false;
        if (board[toRow][toCol] != PieceType.BLANK)
            return false;
        if (board[fromRow][fromCol] == PieceType.BLACK_MAN &&
                toRow < fromRow)
            return false;
        if (board[fromRow][fromCol] == PieceType.WHITE_MAN &&
                toRow > fromRow)
            return false;
        if (Math.abs(fromRow - toRow) == 2) {
            int midRow = (toRow - fromRow) / 2 + fromRow;
            int midCol = (toCol - fromCol) / 2 + fromCol;
            PieceType middle = board[midRow][midCol];
            if (middle == PieceType.BLANK || middle.getColor() == color)
                return false;
        }
        return true;
    }

    public static PieceType[][] cloneBoard(PieceType[][] board) {
        PieceType[][] newBoard = new PieceType[board.length][];
        for (int i = 0; i < board.length; i++)
            newBoard[i] = Arrays.copyOf(board[i], board[i].length);
        return newBoard;
    }

    /**
     * @return turn changes or not
     */
    public static boolean move(PieceType[][] board, Move move) {
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

        PieceType moved = board[move.toRow][move.toCol];
        if (move.toRow == 0 && moved == PieceType.WHITE_MAN)
            board[move.toRow][move.toCol] = PieceType.WHITE_KING;
        else if (move.toRow == 7 && moved == PieceType.BLACK_MAN)
            board[move.toRow][move.toCol] = PieceType.BLACK_KING;

        else if (move.isJump()) {
            List<Move> pj = new ArrayList<>();
            updatePotentialJumps(board, pj, move.toRow, move.toCol);
            if (!pj.isEmpty())
                return false;
        }
        return true;
    }
}
