package com.example.scoket.service;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class RemoteSession {
    Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();


}
