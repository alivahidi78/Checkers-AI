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

    public void start(Stream stream, Color playerColor, int mode,
                      int difficulty1, int difficulty2) {
        this.stream = stream;
        Logger logger = new Logger("log.txt");
        switch (mode) {
            case 1:
                player1 = new HumanPlayer(this, "" + playerColor + " Player", playerColor);
                player2 = new HumanPlayer(this, "" + playerColor.not() + " Player", playerColor.not());
                break;
            case 2:
                player1 = new HumanPlayer(this, "" + playerColor + " Player", playerColor);
                player2 = new AIPlayer(this, "Computer", playerColor.not(), difficulty1);
                break;
            default:
                player1 = new AIPlayer(this, "Computer " + playerColor, playerColor, difficulty1);
                player2 = new AIPlayer(this, "Computer " + playerColor.not(), playerColor.not(), difficulty2);
        }
        db.initialize(player1, player2);
        logger.log("Game started");
        while (!isGameFinished()) {
            stream.printData();
            Player turn = db.getTurn();
            Move move = turn.getNextMove();
            logger.log(turn.name + "'s Move:");
            logger.log(move.toString());
            while (!isMoveValid(move)) {
                System.out.println("Move not possible!");
                logger.log("Invalid move");
                move = db.getTurn().getNextMove();
                logger.log(move.toString());
            }
            db.move(move, player1, player2);
        }
        stream.printData();
        System.out.println(winner + " WINS!");
        logger.log(winner + " WON");
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
