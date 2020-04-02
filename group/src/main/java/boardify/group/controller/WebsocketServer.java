package boardify.group.controller;

import boardify.group.dto.ClientNotification;
import boardify.group.dto.ClientNotificationType;
import boardify.group.dto.MessageFromClient;
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
import java.util.Map;
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
            MessageFromClient msg = mapper.readValue(message, MessageFromClient.class);

            switch (msg.getType()) {
                case SEARCH_GAME:
                    int groupId = gameGroupSearcher.joinGame(msg.getEmail(), msg.getGameId()).get(0).getId();
                    // add user to it's group
                    HashMap value = groups.get(groupId);

                    if(value==null)
                        value = new HashMap();

                    value.put(conn, msg.getEmail());

                    groups.put(groupId, value);
                    notifyGroupMembers(groupId);
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


    private void notifyGroupMembers(int groupId) throws JsonProcessingException {

        HashMap<WebSocket, String> usersInCurrentGroup = groups.get(groupId);

        if(usersInCurrentGroup.size()>=gameGroupSearcher.getMinimumNumberOfPlayers(gameGroupSearcher.findGameForGroup(groupId)))// TODO: start game if enough users
            broadcastGameStarts(groupId);
        else
            broadcastUserJoinedTheGroup(groupId);// TODO: other type; used this just for debug
    }

    private void broadcastGameStarts(int groupId) {

        broadcastMessageToGroup(new ClientNotification(ClientNotificationType.START_GAME), groupId);

    }

    private void removeUser(WebSocket conn) throws JsonProcessingException {
        users.remove(conn);
        //broadcastUserActivityMessage(MessageType.USER_LEFT);
    }


    private void broadcastUserJoinedTheGroup(int groupId) throws JsonProcessingException {

        HashMap<WebSocket, String> usersInCurrentGroup = groups.get(groupId);

        broadcastMessageToGroup(new ClientNotification(String.valueOf(usersInCurrentGroup.size()), ClientNotificationType.JOINED), groupId);

    }

    private void broadcastMessageToGroup(ClientNotification clientNotification, int groupId) {

        HashMap<WebSocket, String> usersInCurrentGroup = groups.get(groupId);

        ObjectMapper mapper = new ObjectMapper();
        String messageJson = null;
        try {
            messageJson = mapper.writeValueAsString(clientNotification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (Map.Entry<WebSocket,String> entry : usersInCurrentGroup.entrySet()) {
            WebSocket sock = entry.getKey();
            sock.send(messageJson);
        }
    }
}
