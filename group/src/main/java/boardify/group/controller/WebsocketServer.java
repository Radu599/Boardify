package boardify.group.controller;

import boardify.group.dto.ClientNotification;
import boardify.group.dto.ClientNotificationType;
import boardify.group.dto.MessageFromClient;
import boardify.group.service.GameGroupSearcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


@Component
public class WebsocketServer extends WebSocketServer {

    private final Logger logger = LogManager.getLogger();

    private GameGroupSearcher gameGroupSearcher;
    private HashMap<WebSocket, String> users;
    private HashMap<Integer, HashMap<WebSocket, String>> groups; // <groupId, users>
    private static final int PORT = 8081;

    @Autowired
    private WebsocketServer(GameGroupSearcher gameGroupSearcher) {
        super(new InetSocketAddress((PORT)));
        this.gameGroupSearcher = gameGroupSearcher;
        users = new HashMap<>();
        groups = new HashMap<>();
        this.start();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        logger.info("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        logger.info("Message:" + message);

        ObjectMapper mapper = new ObjectMapper();
        try {
            MessageFromClient msg = mapper.readValue(message, MessageFromClient.class);

            switch (msg.getType()) {
                case SEARCH_GAME:
                    int groupId = gameGroupSearcher.joinGame(msg.getEmail(), msg.getGameId()).get(0).getId(); // TODO: research on how to use principal
                    // add user to it's group
                    HashMap value = groups.get(groupId);

                    if(value==null)
                        value = new HashMap();

                    value.put(conn, msg.getEmail());

                    groups.put(groupId, value);
                    notifyGroupMembers(groupId);
                    break;
            }
            logger.info("Message from user: " + msg.getEmail() + ", text: " + msg.getGameId() + ", type:" + msg.getType());
        } catch (IOException e) {
            logger.info("Wrong message format.");
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.info("Error" + ex.getMessage());
        assert conn != null;
    }

    private void notifyGroupMembers(int groupId) throws JsonProcessingException {

        logger.info("+++++++++SUCCESSFUL LOGGING notifyGroupMembers+++++++++");
        HashMap<WebSocket, String> usersInCurrentGroup = groups.get(groupId);

        if(usersInCurrentGroup.size()>=gameGroupSearcher.getMinimumNumberOfPlayers(gameGroupSearcher.findGameForGroup(groupId)))// TODO: start game if enough users
            broadcastGameStarts(groupId);
        else
            broadcastUserJoinedTheGroup(groupId);// TODO: other type; used this just for debug

        logger.info("+++++++++SUCCESSFUL LOGGING notifyGroupMembers+++++++++" + groupId);
    }

    private void broadcastGameStarts(int groupId) {

        broadcastMessageToGroup(new ClientNotification(ClientNotificationType.START_GAME), groupId);

    }

    private void removeUser(WebSocket conn) throws JsonProcessingException {
        users.remove(conn);
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
