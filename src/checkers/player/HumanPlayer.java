package checkers.player;

import checkers.util.Game;
import checkers.util.Color;
import checkers.util.Move;

public class HumanPlayer extends Player {

    public HumanPlayer(Game game, String name, Color color) {
        super(game, name, color);
    }

    @Override
    public Move getNextMove() {
        return game.getStream().scanData();
    }
}
