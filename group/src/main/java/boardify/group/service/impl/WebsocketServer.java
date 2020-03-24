package boardify.group.service.impl;

import boardify.group.dto.Message;
import boardify.group.dto.MessageType;
import boardify.group.service.GameGroupSearcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Component
public class WebsocketServer extends WebSocketServer {

    private GameGroupSearcher gameGroupSearcher;

    private HashMap<WebSocket, String> users;
    private HashMap<Integer, HashMap<WebSocket, String>> groups; // <groupId, users>

    private Set<WebSocket> conns;

    private static final int PORT = 8081;

    @Autowired
    private WebsocketServer(GameGroupSearcher gameGroupSearcher) {
        super(new InetSocketAddress((PORT)));
        this.gameGroupSearcher = gameGroupSearcher;
        conns = new HashSet<>();
        users = new HashMap<>();
        groups = new HashMap<>();

        this.start();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        conns.add(webSocket);
        System.out.println("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        // When connection is closed, remove the user.
        try {
            removeUser(conn);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        //TODO: cant use principal...

        System.out.println("Message:" + message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message msg = mapper.readValue(message, Message.class);

            switch (msg.getType()) {
                case SEARCH_GAME:
                    gameGroupSearcher.findGameGroup();
                    HashMap value = new HashMap();
                    value.put(conn, msg.getEmail());
                    //TODO: the key should be the group id because there may be more instances of same game going on
                    groups.put(msg.getGameId(), value);
                    searchGame(new Message(msg.getEmail(),msg.getGameId(), msg.getType()), conn);
                    break;
            }

            System.out.println("Message from user: " + msg.getEmail() + ", text: " + msg.getGameId() + ", type:" + msg.getType());
        } catch (IOException e) {
            System.out.println("Wrong message format.");
            // return error message to user
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

        if (conn != null) {
            conns.remove(conn);
        }
        assert conn != null;
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    private void broadcastMessage(Message msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String messageJson = mapper.writeValueAsString(msg);
            for (WebSocket sock : conns) {
                sock.send(messageJson);
                System.out.println("sending " + msg.toString() + " to " + sock.getLocalSocketAddress());
            }
        } catch (JsonProcessingException e) {

        }
    }

    private void searchGame(Message message, WebSocket conn) throws JsonProcessingException {

        users.put(conn, message.getEmail()); // TODO: useless?
        acknowledgeUserJoined(message.getEmail(), conn);
        broadcastUserActivityMessage(MessageType.SEARCH_GAME);// TODO: other type; used this just for debug
    }

    private void removeUser(WebSocket conn) throws JsonProcessingException {
        users.remove(conn);
        //broadcastUserActivityMessage(MessageType.USER_LEFT);
    }

    private void acknowledgeUserJoined(String email, WebSocket conn) throws JsonProcessingException {

        // TODO: user joined game logic
        System.out.println("acknowledgeUserJoined");
    }

    private void broadcastUserActivityMessage(MessageType messageType) throws JsonProcessingException {

        Message newMessage = new Message();

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(users.values());
        //newMessage.setData(data);
        //newMessage.setType(messageType);
        broadcastMessage(newMessage);
    }
}
