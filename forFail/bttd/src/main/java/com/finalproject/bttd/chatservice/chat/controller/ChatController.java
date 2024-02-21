package com.finalproject.bttd.chatservice.chat.controller;

import com.finalproject.bttd.chatservice.chat.dto.ChatRoom;
import com.finalproject.bttd.chatservice.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> getAll() {
        return chatService.findAll();
    }
}
