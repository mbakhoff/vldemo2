package vldemo2.servlets;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class AuctionSocket {

    private final AuctionSocketController controller;
    private Session session;

    public AuctionSocket(AuctionSocketController controller) {
        this.controller = controller;
    }

    public void send(String message) throws IOException {
        if (session.isOpen()) {
            session.getRemote().sendString(message, null);
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
        controller.getSockets().add(this);
    }

    @OnWebSocketClose
    public void onClose(int status, String message) {
        controller.getSockets().remove(this);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("received on websocket: " + message);
    }
}
