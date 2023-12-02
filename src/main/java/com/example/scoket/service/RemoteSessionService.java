package com.example.scoket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoteSessionService {
    Map<String, RemoteSession> activeSessionMap = new HashMap<>();


    public void insertSession(WebSocketSession session) {
        if(activeSessionMap.containsKey(session.getId())){

        }
    }
}
