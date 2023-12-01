package com.example.scoket;

import com.example.scoket.handler.ClientHandler;
import com.example.scoket.handler.DeviceHandler;
import com.example.scoket.util.PathVariable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final ClientHandler clientHandler;
    private final DeviceHandler deviceHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(clientHandler,"client/**")
                .setAllowedOrigins("*")
                        .addInterceptors(new ClientSocketInterceptor());

        registry.addHandler(deviceHandler,"/device/**")
                .setAllowedOrigins("*").addInterceptors(new DeviceSocketInterceptor());

    }

    class ClientSocketInterceptor implements HandshakeInterceptor{

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        }
    }


    class DeviceSocketInterceptor implements HandshakeInterceptor{

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            String deviceId =  getPathVariable(request, PathVariable.DEVICE_ID);
            String deviceType =  getPathVariable(request, PathVariable.DEVICE_TYPE);
            String operationId =  getPathVariable(request, PathVariable.OPERATION_ID);
            String token = getQueryParams(request,"token");
            attributes.put("deviceId",deviceId);
            attributes.put("deviceType",deviceType);
            attributes.put("operationId",operationId);
            attributes.put("token",token);
            log.info("deviceId : {}, deviceType : {} , operationId : {}, token : {}",deviceId,deviceType,operationId,token);
            return true;
        }

        private String getPathVariable(ServerHttpRequest request, PathVariable pathVariable) {
            String paths = request.getURI().getPath();
            return paths.split("/")[pathVariable.getIndex()];

        }

        private String getQueryParams(ServerHttpRequest request, String input) {
            String[] queries = request.getURI().getQuery().split("&");
            for( String query : queries){
                if(query.contains(input)){
                    return query.split("=")[1];
                }
            }
            throw new RuntimeException("찾는 값이 없습니다.");
        }


        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        }
    }


}
