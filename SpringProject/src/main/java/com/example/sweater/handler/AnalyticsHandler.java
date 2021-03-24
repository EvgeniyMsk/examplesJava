package com.example.sweater.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;

public class AnalyticsHandler extends TextWebSocketHandler {

    private  ArrayList<TextMessage> messages = new ArrayList<>();
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // The WebSocket has been closed
        System.out.println("analytics web socket has been closed");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // The WebSocket has been opened
        // I might save this session object so that I can send messages to it outside of this method
        // Let's send the first message
        System.out.println("analytics web socket connection established");
        session.sendMessage(new TextMessage("You are now connected to the server. This is the first message."));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        // A message has been received
        System.out.println("Analytics Message received: " + textMessage.getPayload());
        if (textMessage.getPayload().equals("get the mouse movement")) {
            String concatenatedString = "";
            System.out.println("messages size" + messages.size());
            for (TextMessage msg: messages) {
                System.out.println("concatenate.."+msg.getPayload());
                concatenatedString += "|" + msg.getPayload();
            }
            session.sendMessage(new TextMessage(concatenatedString));
        } else {
            messages.add(textMessage);
        }
    }

}

