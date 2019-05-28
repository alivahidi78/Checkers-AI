package checkers.util;

import java.util.List;

public class Util {
    public static void updateChoices(PieceType[][] board, List<Move> potentialMoves, List<Move> potentialJumps, Color c) {
        potentialMoves.clear();
        potentialJumps.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceType p = board[i][j];
                if (p.getColor() == c) {
                    updatePotentialMoves(board, potentialMoves, i, j);
                    updatePotentialJumps(board, potentialJumps, i, j);
                }
            }
        }
//        if(!potentialJumps.isEmpty())
//            potentialMoves.clear();//FIXME
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
            if (board[midRow][midCol].getColor() != color.not())
                return false;
        }
        return true;
    }
}
