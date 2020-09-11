package bokduckbang.mySocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry
			.addHandler(webSocketHandler(), "/room-reserve")
			.addInterceptors(new HttpSessionHandshakeInterceptor())
			.setAllowedOrigins("*").withSockJS();
	}

	@Bean
	public WebSocketHandler webSocketHandler() {
		return new Handler();
	}
}