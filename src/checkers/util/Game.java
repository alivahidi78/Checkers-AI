package checkers.util;

import checkers.player.AIPlayer;
import checkers.player.HumanPlayer;
import checkers.player.Player;
import checkers.stream.Stream;

import java.util.List;

public class Game {
    private static Game instance = new Game();
    private Stream stream;
    private Database db;
    private Player player1;
    private Player player2;
    private Color winner;

    private Game() {
        db = Database.getInstance();
    }

    public static Game getInstance() {
        return instance;
    }

    static boolean isGameFinished(List<Move> potentialMoves, List<Move> potentialJumps) {
        return potentialJumps.isEmpty() &&
                potentialMoves.isEmpty();
    }

    public Stream getStream() {
        return stream;
    }

    public void start(Stream stream, Color playerColor, boolean isPvC) {
        this.stream = stream;
        player1 = new HumanPlayer(this, "" + playerColor + " Player", playerColor);
        if (isPvC)
            player2 = new AIPlayer(this, "Computer", playerColor.not());
        else
            player2 = new HumanPlayer(this,
                    "" + playerColor.not() + " Player", playerColor.not());
        db.initialize(player1, player2);
        while (!isGameFinished()) {
            stream.printData();
            Move move = db.getTurn().getNextMove();
            while (!isMoveValid(move)) {
                System.out.println("Move not possible!");
                move = db.getTurn().getNextMove();
            }
            db.move(move, player1, player2);
        }
        stream.printData();
        System.out.println(winner + " WINS!");
    }

    private boolean isGameFinished() {
        if (isGameFinished(db.getPotentialMoves(), db.getPotentialJumps())) {
            if (db.getTurn().color == Color.BLACK)
                winner = Color.WHITE;
            else
                winner = Color.BLACK;
            return true;
        }
        return false;
    }

    private boolean isMoveValid(Move move) {
        if (!db.getPotentialJumps().isEmpty())
            return db.getPotentialJumps().contains(move);
        else
            return db.getPotentialMoves().contains(move);
    }
}
