package checkers.Stream;

import checkers.util.Database;
import checkers.util.Move;
import checkers.util.PieceType;
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
        for (PieceType[] pieceTypes : db.getBoard()) {
            JSONArray row = new JSONArray();
            for (PieceType p : pieceTypes) {
                row.add(p.toJSON());
            }
            boardArray.add(row);
        }
        object.put("board", boardArray);
        object.put("type", "board");
        System.out.println(object.toJSONString());
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
        System.err.println(message);
        move = new Move(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        messageReceived = false;
        return move;
    }
}
