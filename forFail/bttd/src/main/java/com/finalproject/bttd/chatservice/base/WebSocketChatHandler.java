package com.finalproject.bttd.chatservice.base;

import com.finalproject.bttd.chatservice.chat.dto.ChatMessage;
import com.finalproject.bttd.chatservice.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        ChatMessage chatMessage = Util.Chat.resolvePayload(payload);
        chatService.handleAction(chatMessage.getRoomId(), session, chatMessage);
    }
}
