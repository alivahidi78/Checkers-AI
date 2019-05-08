import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

class Game {
    private PieceType[][] pieces = new PieceType[8][8];
    private boolean isWhite;

    Game(boolean playWhite) {
        initiate();
        isWhite = playWhite;
    }

    private void initiate() {
        for (PieceType[] pieceRow : pieces)
            Arrays.fill(pieceRow, PieceType.BLANK);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    pieces[i][j] = PieceType.BLACK_MAN;
        for (int i = 5; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if ((i + j) % 2 == 1)
                    pieces[i][j] = PieceType.WHITE_MAN;
    }

    void run() {
        Server server = new Server();
//        while (!isGameFinished()) {
//            String s = scanner.next();
//            String[] moveStr = s.split(",");
//            int[] move = new int[4];
//            for (int i = 0; i < 4; i++)
//                move[i] = Integer.parseInt(moveStr[i]);
//            processMove(move);
//            JSONObject board = collectBoard();
//            oos.writeObject(board);
//        }
    }

    private JSONObject collectBoard() {
        JSONObject jsonObject = new JSONObject();
        JSONArray board = new JSONArray();
        for (int i = 0; i < 8; i++) {
            JSONArray row = new JSONArray();
            for (int j = 0; j < 8; j++) {
                row.add(pieces[i][j].toJSON());
            }
            board.add(row);
        }
        jsonObject.put("board", board);
        jsonObject.put("label", "user");
        jsonObject.put("turn", "user");
        return jsonObject;
    }

    private boolean isGameFinished() {
        return false;//TODO
    }

    private void processMove(int[] move) {
        processMove(move[0], move[1], move[2], move[3]);
    }

    private void processMove(int row1, int col1, int row2, int col2) {
        PieceType p = pieces[row1][col1];
        pieces[row2][col2] = p;
        pieces[row1][col1] = PieceType.BLANK;
        if (row2 - row1 == 2) {
            if (col2 - col1 == 2)
                pieces[row1 + 1][col1 + 1] = PieceType.BLANK;
            else
                pieces[row1 + 1][col1 - 1] = PieceType.BLANK;
        } else if (row2 - row1 == -2) {
            if (col2 - col1 == 2)
                pieces[row1 - 1][col1 + 1] = PieceType.BLANK;
            else
                pieces[row1 - 1][col1 - 1] = PieceType.BLANK;
        }
    }

//    private void printBoard() {
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                PieceType p = pieces[i][j];
//                if (p == null)
//                    System.out.print("0 ");
//                else if (p == PieceType.BLACK_MAN)
//                    System.out.print("1 ");
//                else if (p == PieceType.WHITE_MAN)
//                    System.out.print("2 ");
//            }
//            System.out.println();
//        }
//    }
}
