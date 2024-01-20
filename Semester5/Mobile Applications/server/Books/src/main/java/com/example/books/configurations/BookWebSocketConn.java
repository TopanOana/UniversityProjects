package com.example.books.configurations;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookWebSocketConn extends TextWebSocketHandler {
    static Map<String, WebSocketSession> connections = new HashMap<>();

    public BookWebSocketConn() {
        super();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        connections.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        connections.remove(session.getId());
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        connections.remove(session.getId());
    }

    public void broadcast(String message){
        connections.values().forEach(it -> {
            if (it.isOpen()){
                try{
                    it.sendMessage(new TextMessage(message));
                }catch(IOException ex){
                    System.out.println("error sending broadcast! to " + it.getId());
                }
            }
        });
    }
}
