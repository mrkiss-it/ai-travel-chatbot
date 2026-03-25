package com.kiss.ai_travel_chatbot.controller;

import com.kiss.ai_travel_chatbot.service.ChatService;
import com.kiss.ai_travel_chatbot.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    @Autowired
    private OllamaService ollamaService;

    @PostMapping("/chat")
    public String chat (@RequestBody Map<String, String> request) {
        return ollamaService.generate(request.get("message"));
    }
}
