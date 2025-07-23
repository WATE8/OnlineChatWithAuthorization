package chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Configuration
    @EnableWebSocket
    public class WebSocketConfig implements WebSocketConfigurer {
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(chatWebSocketHandler(), "/chat").setAllowedOrigins("*");
        }

        @Bean
        public ChatWebSocketHandler chatWebSocketHandler() {
            return new ChatWebSocketHandler();
        }
    }
}