import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

class Game {
    private PieceType[][] pieces = new PieceType[8][8];
    private boolean isWhite;

    Game(boolean playWhite) {
        initiate();
        isWhite = playWhite;
    }

    private void initiate() {
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
        try {
            Socket socket = new Socket("localhost", 12345);
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            InputStream is = socket.getInputStream();
            Scanner scanner = new Scanner(is);
            while (!isGameFinished()) {
                String s = scanner.next();
                String[] moveStr = s.split(",");
                int[] move = new int[4];
                for (int i = 0; i < 4; i++) {
                    String c = moveStr[i];
                    move[i] = Integer.parseInt(c);
                }
                processMove(move);
                JSONObject board = collectBoard();
                oos.writeObject(board);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void processMove(int[] move) {
        move(move[0], move[1], move[2], move[3]);
    }


    private boolean isGameFinished() {
        return false;//TODO
    }

    private void move(int row1, int col1, int row2, int col2) {
        PieceType p = pieces[row1][col1];
        pieces[row2][col2] = p;
        pieces[row1][col1] = null;
        if (row2 - row1 == 2) {
            if (col2 - col1 == 2)
                pieces[row1 + 1][col1 + 1] = null;
            else
                pieces[row1 + 1][col1 - 1] = null;
        } else if (row2 - row1 == -2) {
            if (col2 - col1 == 2)
                pieces[row1 - 1][col1 + 1] = null;
            else
                pieces[row1 - 1][col1 - 1] = null;
        }
    }

    private void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceType p = pieces[i][j];
                if (p == null)
                    System.out.print("0 ");
                else if (p == PieceType.BLACK_MAN)
                    System.out.print("1 ");
                else if (p == PieceType.WHITE_MAN)
                    System.out.print("2 ");
            }
            System.out.println();
        }
    }
}
