package checkers.util;

public enum PieceType {
    BLANK, WHITE_MAN, BLACK_MAN, WHITE_KING, BLACK_KING;

    public Color getColor() {
        if (this == WHITE_MAN || this == WHITE_KING)
            return Color.WHITE;
        if (this == BLACK_MAN || this == BLACK_KING)
            return Color.BLACK;
        return null;
    }

//    public JSONObject toJSON() {
//        String type = "null";
//        String color = "null";
//        try {
//            switch (this) {
//                case WHITE_MAN:
//                case WHITE_KING:
//                    color = "white";
//                    break;
//                case BLACK_MAN:
//                case BLACK_KING:
//                    color = "black";
//            }
//            switch (this) {
//                case BLACK_KING:
//                case WHITE_KING:
//                    type = "king";
//                    break;
//                case BLACK_MAN:
//                case WHITE_MAN:
//                    type = "man";
//            }
//        } catch (Exception e) {
//            type = "null";
//            color = "null";
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("type", type);
//        jsonObject.put("color", color);
//        return jsonObject;
//    }
}

