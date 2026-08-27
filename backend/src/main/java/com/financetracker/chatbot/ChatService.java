package com.financetracker.chatbot;

import com.financetracker.chatbot.model.ChatMessage;
import com.financetracker.chatbot.repository.ChatMessageRepository;
import com.financetracker.model.User;
import com.financetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ChatService {

    @Autowired private ChatMessageRepository chatRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private FinancialContextService contextService;
    @Autowired private AIService aiService;

    @Transactional
    public Map<String, String> processUserMessage(String userEmail, String message) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        // Save User Message
        ChatMessage userMsg = new ChatMessage(user, "USER", message);
        chatRepo.save(userMsg);

        // Aggregate live context & generate response
        Map<String, Object> context = contextService.collectUserFinancialContext(userEmail);
        String reply = aiService.generateResponse(message, context);

        // Save Bot Response
        ChatMessage botMsg = new ChatMessage(user, "BOT", reply);
        chatRepo.save(botMsg);

        Map<String, String> response = new HashMap<>();
        response.put("userMessage", message);
        response.put("botReply", reply);
        return response;
    }

    public List<ChatMessage> getChatHistory(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));
        return chatRepo.findTop20ByUserOrderByCreatedAtAsc(user);
    }
}
