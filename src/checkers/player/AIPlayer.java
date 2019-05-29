package checkers.player;

import checkers.util.*;

public class AIPlayer extends Player {
    private Database db;

    public AIPlayer(Game game, String name, Color color) {
        super(game, name, color);
        db = Database.getInstance();
    }

    @Override
    public Move getNextMove() {
        MinimaxTree tree = new MinimaxTree(db.getBoard(), this.color,
                db.getLastMove(), db.isTurnChanged());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tree.dls_getMove();
    }
}
