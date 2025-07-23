package chat;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<WebSocketSession, String> sessions = new HashMap<>();
    private final Key SECRET_KEY;

    public ChatWebSocketHandler() {
        String secretString = "f832h293hd29d8h29djwq9djqw9djqw9d";
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        if (query == null || !query.startsWith("token=")) {
            session.close();
            return;
        }
        String token = query.replace("token=", "");
        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            sessions.put(session, username);
            session.sendMessage(new TextMessage("Welcome, " + username + "!"));
        } catch (Exception e) {
            session.close();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String user = sessions.get(session);
        String text = user + ": " + message.getPayload();
        for (WebSocketSession s : sessions.keySet()) {
            if (s.isOpen()) s.sendMessage(new TextMessage(text));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }
}
