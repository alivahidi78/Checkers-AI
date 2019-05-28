package checkers;

import checkers.player.AIPlayer;
import checkers.player.HumanPlayer;
import checkers.player.Player;
import checkers.stream.Stream;
import checkers.util.Color;
import checkers.util.Database;
import checkers.util.Move;

public class Game {
    private Stream stream;
    private Database db;
    private Player player1;
    private Player player2;
    private Color winner;

    Game(Stream stream, Color playerColor, boolean isPvC) {
        db = Database.getInstance();
        this.stream = stream;
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
        db.initialize(player1, player2);
        while (!isGameFinished()) {
            stream.printData();
            Move move = db.getTurn().getNextMove();
            while (!db.isMoveValid(move)) {
                System.out.println("Move not possible!");
                move = db.getTurn().getNextMove();
            }
            db.move(move);
            db.updateTurn(player1, player2);
        }
        stream.printData();
        System.out.println(winner + " WINS!");
    }

    private boolean isGameFinished() {
        if (db.getPotentialJumps().isEmpty() &&
                db.getPotentialMoves().isEmpty()) {
            if (db.getTurn().color == Color.BLACK)
                winner = Color.WHITE;
            else
                winner = Color.BLACK;
            return true;
        }
        return false;
    }
}
