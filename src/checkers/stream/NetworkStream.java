package checkers.stream;

import checkers.util.Board;
import checkers.util.Database;
import checkers.util.Move;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class NetworkStream extends WebSocketServer implements Stream {
    private static final int TCP_PORT = 4444;
    private static NetworkStream networkStream = new NetworkStream();
    private final Object messageLock = new Object();
    private final Object connectionLock = new Object();
    private boolean isConnected = false;
    private boolean messageReceived = false;
    private WebSocket socket;
    private Database db;
    private String message;

    private NetworkStream() {
        super(new InetSocketAddress(TCP_PORT));
        db = Database.getInstance();
    }

    public static NetworkStream getInstance() {
        return networkStream;
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        socket = webSocket;
        synchronized (connectionLock) {
            connectionLock.notify();
        }
        isConnected = true;
        printData();
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        isConnected = false;
        socket = null;
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        message = s;
        messageReceived = true;
        synchronized (messageLock) {
            messageLock.notify();
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void printData() {
        Board board = db.getBoard();
        JSONObject object = new JSONObject();
        JSONArray boardArray = new JSONArray();
        if (!isConnected) {
            try {
                synchronized (connectionLock) {
                    connectionLock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 8; i++) {
            JSONArray row = new JSONArray();
            for (int j = 0; j < 8; j++) {
                row.add(board.get(i, j).toJSON());
            }
            boardArray.add(row);
        }
        object.put("board", boardArray);
        object.put("type", "board");
        System.err.println(db.getTurn().color);
        socket.send(object.toJSONString());
    }

    @Override
    public Move scanData() {
        Move move;
        if (!messageReceived)
            try {
                synchronized (messageLock) {
                    messageLock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        Scanner scanner = new Scanner(message);
        move = new Move(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        messageReceived = false;
        return move;
    }
}
