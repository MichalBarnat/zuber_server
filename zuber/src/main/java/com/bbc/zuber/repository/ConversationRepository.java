package com.bbc.zuber.repository;

import com.bbc.zuber.model.conversation.Conversation;
import com.bbc.zuber.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByConversationUuid(UUID conversationUuid);

    Optional<Conversation> findByRideInfoId(Long rideInfoId);
}
