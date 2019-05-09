package checkers.util;

import org.json.simple.JSONObject;

public enum PieceType {
    BLANK, WHITE_MAN, BLACK_MAN, WHITE_KING, BLACK_KING;

    public JSONObject toJSON() {
        String type = "null";
        String color = "null";
        try {
            switch (this) {
                case WHITE_MAN:
                case WHITE_KING:
                    color = "white";
                    break;
                case BLACK_MAN:
                case BLACK_KING:
                    color = "black";
            }
            switch (this) {
                case BLACK_KING:
                case WHITE_KING:
                    type = "king";
                    break;
                case BLACK_MAN:
                case WHITE_MAN:
                    type = "man";
            }
        } catch (Exception e) {
            type = "null";
            color = "null";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("color", color);
        return jsonObject;
    }
}

