package checkers.Stream;

import checkers.util.Move;
import checkers.util.PieceType;

import java.util.Scanner;

public class DefaultStream implements Stream {
    private static DefaultStream stream = new DefaultStream();
    private Scanner scanner = new Scanner(System.in);

    private DefaultStream() {
    }

    public static DefaultStream getStream() {
        return stream;
    }


    @Override
    public void printData(PieceType[][] board, Move lastMove) {
        System.out.print("\t ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + (i + 1) + " ");
        System.out.println("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print("" + (i + 1) + "\t ");
            for (int j = 0; j < 8; j++) {
                PieceType p = board[i][j];
                if ((i + j) % 2 == 0)
                    System.out.print("\u001B[47m");
                else if ((i == lastMove.fromRow && j == lastMove.fromCol) ||
                        i == lastMove.toRow && j == lastMove.toCol)
                    System.out.print("\033[43m");
                if (p == PieceType.BLANK)
                    System.out.print("   ");
                else if (p == PieceType.BLACK_MAN)
                    System.out.print("\033[1;34m" + " M ");
                else if (p == PieceType.WHITE_MAN)
                    System.out.print("\033[1;31m" + " M ");
                else if (p == PieceType.BLACK_KING)
                    System.out.print("\033[1;34m" + " K ");
                else if (p == PieceType.WHITE_KING)
                    System.out.print("\033[1;31m" + " K ");
                System.out.print("\u001B[0m");
            }
            System.out.print("\t" + (i + 1));
            System.out.println();
        }
        System.out.println();
        System.out.print("\t ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + (i + 1) + " ");
        System.out.println();
    }

    @Override
    public int[] scanData() {
        int[] data = new int[4];
        data[0] = scanner.nextInt() - 1;
        data[1] = scanner.nextInt() - 1;
        data[2] = scanner.nextInt() - 1;
        data[3] = scanner.nextInt() - 1;
        return data;
    }
}
