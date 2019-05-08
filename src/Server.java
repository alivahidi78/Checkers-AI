import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer {
    private static int TCP_PORT = 4444;
    private WebSocket socket;

    Server() {
        super(new InetSocketAddress(TCP_PORT));
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
}
