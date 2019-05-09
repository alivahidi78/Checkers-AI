package checkers.util;

public enum Color {
    BLACK, WHITE;

    public Color not() {
        if (this == BLACK)
            return WHITE;
        else
            return BLACK;
    }
}
