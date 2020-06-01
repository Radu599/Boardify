package boardify.group.controller;

import boardify.group.config.WebsocketConstants;
import boardify.group.dto.ChatDto.ClientToServer.ChatClientToServerMessage;
import boardify.group.dto.ChatDto.ClientToServer.ChatClientToServerMessageType;
import boardify.group.dto.ChatDto.ClientToServer.StatsDto;
import boardify.group.dto.JoinGroupDto.ClientToServer.JoinGroupMessageFromClient;
import boardify.group.dto.JoinGroupDto.ServerToClient.ClientNotification;
import boardify.group.dto.JoinGroupDto.ServerToClient.ClientNotificationType;
import boardify.group.dto.Notification;
import boardify.group.model.Stats;
import boardify.group.service.GameGroupSearcher;
import boardify.group.service.StatsService;
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

// TODO: refact this class
@Component
public class WebsocketServer extends WebSocketServer {

    private GameGroupSearcher gameGroupSearcher;
    private StatsService statsService;
    private HashMap<WebSocket, String> users;
    private HashMap<Integer, HashMap<WebSocket, String>> groups; // <groupId, users>
    private static final int PORT = WebsocketConstants.PORT;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    private WebsocketServer(GameGroupSearcher gameGroupSearcher, StatsService statsService) {
        super(new InetSocketAddress((PORT)));
        this.gameGroupSearcher = gameGroupSearcher;
        this.statsService = statsService;
        users = new HashMap<>();
        groups = new HashMap<>();
        logger.info("WebSocket before start");
        this.start();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        logger.info("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    }

    //TODO: refact onMessage!!!
    @Override
    public void onMessage(WebSocket conn, String message) {

        logger.info("Message:" + message);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JoinGroupMessageFromClient msg = mapper.readValue(message, JoinGroupMessageFromClient.class);

            switch (msg.getType()) {
                case SEARCH_GAME:
                    int groupId = gameGroupSearcher.joinGame(msg.getEmail(), msg.getGameId(), msg.getCity()).get(0).getId(); // TODO: research on how to use principal
                    // add user to it's group
                    HashMap value = groups.get(groupId);

                    if (value == null)
                        value = new HashMap();

                    value.put(conn, msg.getEmail());

                    groups.put(groupId, value);
                    notifyPlayerJoinedGroup(groupId);
                    break;
            }
            logger.info("Message from user: " + msg.getEmail() + ", text: " + msg.getGameId() + ", type:" + msg.getType());
        } catch (IOException e) {
            logger.info("This is not a valid join message");
        }

        try {
            ChatClientToServerMessage chatClientToServerMessage = mapper.readValue(message, ChatClientToServerMessage.class);
            chatClientToServerMessage.setTimestamp(System.currentTimeMillis());
            switch (chatClientToServerMessage.getType()) {
                case CHAT_MESSAGE:
                    int targetGroup = chatClientToServerMessage.getTargetGroup();
                    broadcastMessageToGroup(chatClientToServerMessage, targetGroup);
                    statsService.saveStats(new Stats(targetGroup, chatClientToServerMessage.getSenderEmail(), chatClientToServerMessage.getTimestamp()));
                    Stats stats = statsService.findStatsByGroupIdAndEmail(targetGroup, chatClientToServerMessage.getSenderEmail());
                    Notification statsDto = new StatsDto(stats.getEmail(), stats.getGroupId(), stats.getLastMessage(), stats.getMessageCount(), ChatClientToServerMessageType.STATS);
                    broadcastMessageToGroup(statsDto, targetGroup);
                    break;
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            logger.info("This is not a valid chat message");
        }
    }

    private void broadcastStatsToGroup(int targetGroup) {
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.info("Error" + ex.getMessage());
        assert conn != null;
    }

    private void notifyPlayerJoinedGroup(int groupId) throws JsonProcessingException {

        logger.info("+++++++++SUCCESSFUL LOGGING notifyGroupMembers+++++++++");
        HashMap<WebSocket, String> usersInCurrentGroup = groups.get(groupId);

        if (usersInCurrentGroup.size() >= gameGroupSearcher.getMinimumNumberOfPlayers(gameGroupSearcher.findGameForGroup(groupId)))
            broadcastGameStarts(groupId);
        else
            broadcastUserJoinedTheGroup(groupId);

        logger.info("+++++++++SUCCESSFUL LOGGING notifyGroupMembers+++++++++" + groupId);
    }

    private void broadcastGameStarts(int groupId) {

        HashMap<WebSocket, String> usersInCurrentGroup = groups.get(groupId);
        broadcastMessageToGroup(new ClientNotification(usersInCurrentGroup.size(), groupId, ClientNotificationType.START_GAME), groupId);
    }

    private void broadcastUserJoinedTheGroup(int groupId) throws JsonProcessingException {

        HashMap<WebSocket, String> usersInCurrentGroup = groups.get(groupId);
        broadcastMessageToGroup(new ClientNotification(usersInCurrentGroup.size(), groupId, ClientNotificationType.JOINED), groupId);
    }

    private void broadcastMessageToGroup(Notification clientNotification, int groupId) {

        HashMap<WebSocket, String> usersInCurrentGroup = groups.get(groupId);

        ObjectMapper mapper = new ObjectMapper();
        String messageJson = null;
        try {
            messageJson = mapper.writeValueAsString(clientNotification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (Map.Entry<WebSocket, String> entry : usersInCurrentGroup.entrySet()) {
            WebSocket sock = entry.getKey();
            sock.send(messageJson);
        }
    }
}
