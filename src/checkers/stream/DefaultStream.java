package checkers.stream;

import checkers.util.Database;
import checkers.util.Move;
import checkers.util.PieceType;

import java.util.Scanner;

public class DefaultStream implements Stream {
    private static DefaultStream stream = new DefaultStream();
    private Scanner scanner = new Scanner(System.in);
    private Database db;

    private DefaultStream() {
        db = Database.getInstance();
    }

    public static DefaultStream getInstance() {
        return stream;
    }


    @Override
    public void printData() {
        System.out.print("\t ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + (i + 1) + " ");
        System.out.println("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print("" + (i + 1) + "\t ");
            for (int j = 0; j < 8; j++) {
                PieceType p = db.getBoard()[i][j];
                if ((i + j) % 2 == 0)
                    System.out.print("\u001B[47m");
                else if ((i == db.getLastMove().fromRow && j == db.getLastMove().fromCol) ||
                        i == db.getLastMove().toRow && j == db.getLastMove().toCol)
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
        System.out.println(db.getTurn().name + "'s turn:");
    }

    @Override
    public Move scanData() {
        return new Move(scanner.nextInt() - 1,
                scanner.nextInt() - 1,
                scanner.nextInt() - 1,
                scanner.nextInt() - 1);
    }
}
