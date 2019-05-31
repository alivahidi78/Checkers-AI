package checkers.player;

import checkers.util.*;

public class AIPlayer extends Player {
    private Database db;
    private int difficulty;

    public AIPlayer(Game game, String name, Color color, int difficulty) {
        super(game, name, color);
        db = Database.getInstance();
        this.difficulty = difficulty;
    }

    @Override
    public Move getNextMove() {
        MinimaxTree tree = new MinimaxTree(db.getBoard(), this.color,
                db.getLastMove(), db.hasTurnChanged(), difficulty);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return tree.dls_getMove();
    }
}
