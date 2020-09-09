package bokduckbang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Component
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(EchoHandler(), "/myHandler/info") // 특정 URL에 웹소켓 핸들러를 매핑한다.
        		.setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor()); // 핸드셰이크 요청을 인터셉트할 인터셉터
    }

    @Bean
    public WebSocketHandler EchoHandler(){
        return new EchoHandler();
    }
}
