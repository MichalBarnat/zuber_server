package com.bbc.zuber.service;

import com.bbc.zuber.exceptions.RideInfoNotFoundException;
import com.bbc.zuber.model.conversation.Conversation;
import com.bbc.zuber.model.message.Message;
import com.bbc.zuber.model.message.command.CreateMessageCommand;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.repository.ConversationRepository;
import com.bbc.zuber.repository.MessageRepository;
import com.bbc.zuber.repository.RideInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final RideInfoRepository rideInfoRepository;

    @Transactional
    public Message saveMessageFromUserToConversation(CreateMessageCommand command) {
        RideInfo rideInfo = rideInfoRepository.findByUserUuid(command.getSenderUuid())
                .orElseThrow(() -> new RideInfoNotFoundException("Ride info with user uuid: " + command.getSenderUuid() + " not found!"));

        Conversation conversation = conversationRepository.findByRideInfoId(rideInfo.getId())
                .orElseGet(() -> {
                    Conversation newConversation = Conversation.builder()
                            .conversationUuid(UUID.randomUUID())
                            .rideInfoId(rideInfo.getId())
                            .build();
                    return conversationRepository.save(newConversation);
                });

        Message message = Message.builder()
                .messageUuid(UUID.randomUUID())
                .senderUuid(command.getSenderUuid())
                .content(command.getContent())
                .dateTime(LocalDateTime.now())
                .conversation(conversation)
                .build();

        conversation.addMessage(message);

        return messageRepository.save(message);
    }

    @Transactional
    public Message saveMessageFromDriverToConversation(CreateMessageCommand command) {
        RideInfo rideInfo = rideInfoRepository.findByDriverUuid(command.getSenderUuid())
                .orElseThrow(() -> new RideInfoNotFoundException("Ride info with driver uuid: " + command.getSenderUuid() + " not found!"));

        Conversation conversation = conversationRepository.findByRideInfoId(rideInfo.getId())
                .orElseGet(() -> {
                    Conversation newConversation = Conversation.builder()
                            .conversationUuid(UUID.randomUUID())
                            .rideInfoId(rideInfo.getId())
                            .build();
                    return conversationRepository.save(newConversation);
                });

        Message message = Message.builder()
                .messageUuid(UUID.randomUUID())
                .senderUuid(command.getSenderUuid())
                .content(command.getContent())
                .dateTime(LocalDateTime.now())
                .conversation(conversation)
                .build();

        conversation.addMessage(message);

        return messageRepository.save(message);
    }
}
