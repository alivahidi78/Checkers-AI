package checkers.player;

import checkers.util.Game;
import checkers.util.Color;
import checkers.util.Database;
import checkers.util.Move;

public class AIPlayer extends Player {
    private Database db;

    public AIPlayer(Game game, String name, Color color) {
        super(game, name, color);
        db = Database.getInstance();
    }

    @Override
    public Move getNextMove() {
        return null;
    }
}
