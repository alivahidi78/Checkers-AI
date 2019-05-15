package checkers;

import checkers.Stream.Stream;
import checkers.player.AIPlayer;
import checkers.player.HumanPlayer;
import checkers.player.Player;
import checkers.util.Color;
import checkers.util.Database;
import checkers.util.Move;
import checkers.util.PieceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private Stream stream;
    private Database db;
    private PieceType[][] board;
    private List<Move> potentialMoves;
    private List<Move> potentialJumps;
    private Player player1;
    private Player player2;
    private Color winner;

    Game(Stream stream, Color playerColor, boolean isPvC) {
        board = new PieceType[8][8];
        db = Database.getInstance();
        db.setBoard(board);
        potentialMoves = new ArrayList<>();
        potentialJumps = new ArrayList<>();
        this.stream = stream;
        db.setLastMove(new Move(-1, -1, -1, -1));
        player1 = new HumanPlayer(this, "" + playerColor + " Player", playerColor);
        if (isPvC)
            player2 = new AIPlayer(this, "Computer", playerColor.not());
        else
            player2 = new HumanPlayer(this,
                    "" + playerColor.not() + " Player", playerColor.not());
    }

    public Stream getStream() {
        return stream;
    }

    void start() {
        //Sets Board.
        for (PieceType[] pieceRow : board)
            Arrays.fill(pieceRow, PieceType.BLANK);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    board[i][j] = PieceType.BLACK_MAN;
        for (int i = 5; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    board[i][j] = PieceType.WHITE_MAN;
        db.setTurn(player1.color == Color.BLACK ? player1 : player2);
        updateChoices(Color.BLACK);

        //Starts the game
        while (!isGameFinished()) {
            stream.printData();
            Move move = db.getTurn().getNextMove();
            while (!isMoveValid(move)) {
                System.out.println("Move not possible!");
                move = db.getTurn().getNextMove();
            }
            move(move);
            updateTurn();
        }
        stream.printData();
        System.out.println(winner + " WINS!");
    }

    private boolean isGameFinished() {
        if (potentialJumps.isEmpty() &&
                potentialMoves.isEmpty()) {
            if (db.getTurn().color == Color.BLACK)
                winner = Color.WHITE;
            else
                winner = Color.BLACK;
            return true;
        }
        return false;
    }

    private boolean isMoveValid(Move move) {
        if (!potentialJumps.isEmpty())
            return potentialJumps.contains(move);
        else
            return potentialMoves.contains(move);
    }

    private boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
        Color color = board[fromRow][fromCol].getColor();
        if (fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7)
            return false;
        if (fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7)
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

    private void move(Move move) {
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
        if (p.getColor() == Color.WHITE && move.toRow == 0)
            board[move.toRow][move.toCol] = PieceType.WHITE_KING;
        if (p.getColor() == Color.BLACK && move.toRow == 7)
            board[move.toRow][move.toCol] = PieceType.BLACK_KING;
        db.setLastMove(move);
    }

    private void updateTurn() {
        Move lm = db.getLastMove();
        if (Math.abs(lm.toCol - lm.fromCol) == 2) {
            potentialJumps.clear();
            potentialMoves.clear();
            updatePotentialJumps(lm.toRow, lm.toCol);
            if (!potentialJumps.isEmpty())
                return;
        }
        if (db.getTurn() == player1)
            db.setTurn(player2);
        else
            db.setTurn(player1);
        updateChoices(db.getTurn().color);
    }

    private void updateChoices(Color c) {
        potentialMoves.clear();
        potentialJumps.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceType p = board[i][j];
                if (p.getColor() == c) {
                    updatePotentialMoves(i, j);
                    updatePotentialJumps(i, j);
                }
            }
        }
    }

    private void updatePotentialMoves(int i, int j) {
        int[] iDest = new int[]{i + 1, i - 1};
        int[] jDest = new int[]{j + 1, j - 1};
        for (int k : iDest)
            for (int w : jDest)
                if (canMove(i, j, k, w))
                    potentialMoves.add(new Move(i, j, k, w));
    }

    private void updatePotentialJumps(int i, int j) {
        int[] iDest = new int[]{i + 2, i - 2};
        int[] jDest = new int[]{j + 2, j - 2};
        for (int k : iDest)
            for (int w : jDest)
                if (canMove(i, j, k, w)) {
                    potentialJumps.add(new Move(i, j, k, w));
                }
    }
}
