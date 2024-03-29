package checkers.player;

import checkers.util.Game;
import checkers.util.Color;
import checkers.util.Move;

public abstract class Player {
    public final Color color;
    public final String name;
    final Game game;

    Player(Game game, String name, Color color) {
        this.game = game;
        this.color = color;
        this.name = name;
    }

    public abstract Move getNextMove();
}
