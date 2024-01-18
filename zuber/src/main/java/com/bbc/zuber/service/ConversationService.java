package com.bbc.zuber.service;

import com.bbc.zuber.exceptions.ConversationNotFoundException;
import com.bbc.zuber.model.conversation.Conversation;
import com.bbc.zuber.model.message.Message;
import com.bbc.zuber.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    @Transactional(readOnly = true)
    public Conversation findByRideInfoId(long rideInfoId) {
        return conversationRepository.findByRideInfoId(rideInfoId)
                .orElseThrow(() -> new ConversationNotFoundException("Conversation with ride info id: " + rideInfoId + " not found!"));
    }

    @Transactional(readOnly = true)
    public Conversation findByUuid(UUID conversationUuid) {
        return conversationRepository.findByConversationUuid(conversationUuid)
                .orElseThrow(() -> new ConversationNotFoundException("Conversation with uuid: " + conversationUuid + " not found!"));
    }

    @Transactional(readOnly = true)
    public LinkedList<Message> findMessagesFromConversation(long rideInfoId) {
        Conversation conversation = conversationRepository.findByRideInfoId(rideInfoId)
                .orElseThrow(() -> new ConversationNotFoundException("Conversation with ride info id: " + rideInfoId + " not found!"));

        return conversation.getMessages();
    }
}
