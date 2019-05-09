package checkers.player;

import checkers.Game;
import checkers.util.Color;
import checkers.util.Move;

public class AIPlayer extends Player {

    public AIPlayer(Game game, String name, Color color) {
        super(game, name, color);
    }

    @Override
    public Move getNextMove() {
        return null;
    }
}
