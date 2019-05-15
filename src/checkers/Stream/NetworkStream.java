package checkers.Stream;

import checkers.util.Move;
import checkers.util.PieceType;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class NetworkStream extends WebSocketServer implements Stream {
    private static final int TCP_PORT = 4444;
    private static NetworkStream networkStream = new NetworkStream();
    private WebSocket socket;

    private NetworkStream() {
        super(new InetSocketAddress(TCP_PORT));
    }

    public static NetworkStream getStream() {
        return networkStream;
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        socket = webSocket;
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        socket = null;
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println(s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void printData(PieceType[][] board, Move lastMove) {

    }

    @Override
    public int[] scanData() {
        return new int[0];
    }
}
