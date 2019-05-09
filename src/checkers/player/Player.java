package checkers.player;

import checkers.Game;
import checkers.util.Color;
import checkers.util.Move;

public abstract class Player {
    public final Color color;
    public final String name;
    private final Game game;

    Player(Game game, String name, Color color) {
        this.game = game;
        this.color = color;
        this.name = name;
    }

    public abstract Move getNextMove();
}
