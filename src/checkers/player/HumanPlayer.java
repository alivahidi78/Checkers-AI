package checkers.player;

import checkers.Game;
import checkers.util.Color;
import checkers.util.Move;

public class HumanPlayer extends Player {

    public HumanPlayer(Game game, String name, Color color) {
        super(game, name, color);
    }

    @Override
    public Move getNextMove() {
        int[] m = game.stream.scanData();
        return new Move(m);
    }
}
