package checkers.util;

import org.json.simple.JSONObject;

public enum PieceType {
    BLANK, WHITE_MAN, BLACK_MAN, WHITE_KING, BLACK_KING;

    public Color getColor() {
        if (this == WHITE_MAN || this == WHITE_KING)
            return Color.WHITE;
        if (this == BLACK_MAN || this == BLACK_KING)
            return Color.BLACK;
        return null;
    }

    public JSONObject toJSON() {
        JSONObject piece;
        if (this == BLANK)
            piece = null;
        else {
            String type = "man";
            String color = "#00677f";
            piece = new JSONObject();
            if (this == BLACK_KING || this == WHITE_KING)
                type = "king";
            if (this.getColor() == Color.WHITE)
                color = "white";
            piece.put("color", color);
            piece.put("type", type);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("piece", piece);
        return jsonObject;
    }
}

