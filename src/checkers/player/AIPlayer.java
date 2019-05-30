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
                db.getLastMove(), db.hasTurnChanged());
        return tree.dls_getMove();
    }
}
