package com.financetracker.chatbot;

import com.financetracker.chatbot.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired private ChatService chatService;

    @PostMapping
    public ResponseEntity<Map<String, String>> sendMessage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> request
    ) {
        String email = userDetails != null ? userDetails.getUsername() : "ritik@example.com";
        String message = request.getOrDefault("message", "");
        return ResponseEntity.ok(chatService.processUserMessage(email, message));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getHistory(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails != null ? userDetails.getUsername() : "ritik@example.com";
        return ResponseEntity.ok(chatService.getChatHistory(email));
    }
}
