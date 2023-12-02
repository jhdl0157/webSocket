package com.example.scoket.handler;

import com.example.scoket.service.RemoteSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientHandler extends TextWebSocketHandler {
    private final RemoteSessionService remoteSessionService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Client connected to Server Session Id : {} ",session.getId());
        log.info("Client IP : {}",session.getRemoteAddress());
        remoteSessionService.insertSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //세션을 닫을때 자료구조를 뒤져서 연결된 친구들까지 닫아주기
        //remoteSessionService.closePeerSession(session);
        session.close();
        log.info("close Session.. Session Id : {}",session.getId());
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        log.info("Client send Binary Message... message payLoad : {}",message.getPayload());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("Client send Text Message... message payLoad : {}",message.getPayload());

    }
}
