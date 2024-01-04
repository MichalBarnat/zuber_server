package com.bbc.zuber.controller;

import com.bbc.zuber.model.message.Message;
import com.bbc.zuber.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final MessageRepository messageRepository;

    @PostMapping("/saveMessage")
    public ResponseEntity<String> saveMessage(@RequestBody Message message) {
        message.setDateTime(LocalDateTime.now());
        messageRepository.save(message);
        return ResponseEntity.ok("Message saved correctly!");
    }

    @GetMapping("/getMessages")
    public ResponseEntity<List<Message>> getMessages(@RequestParam String senderId, @RequestParam String receiverId) {
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }

    //todo CHAT
    //przy getMessages zwracac tylko wiadomosci bez reszty

}
