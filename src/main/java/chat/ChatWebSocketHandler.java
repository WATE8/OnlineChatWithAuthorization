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
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            sessions.put(session, username);

            // Личное приветствие
            session.sendMessage(new TextMessage("Добро пожаловать в чат, " + username + "!"));

            // Уведомление всем
            broadcast("🔵 " + username + " вошёл в чат");

        } catch (Exception e) {
            session.close();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = sessions.get(session);
        if (username != null) {
            String text = username + ": " + message.getPayload();
            broadcast(text);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String username = sessions.get(session);
        if (username != null) {
            sessions.remove(session);
            broadcast("🔴 " + username + " покинул чат");
        }
    }

    private void broadcast(String message) {
        for (WebSocketSession s : sessions.keySet()) {
            if (s.isOpen()) {
                try {
                    s.sendMessage(new TextMessage(message));
                } catch (Exception ignored) {}
            }
        }
    }
}
