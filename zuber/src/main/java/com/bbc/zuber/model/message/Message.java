package com.bbc.zuber.model.message;

import com.bbc.zuber.model.conversation.Conversation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "messages")
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_seq")
    @SequenceGenerator(name = "messages_seq", sequenceName = "messages_seq", allocationSize = 1)
    private Long id;
    @Column(name = "message_uuid")
    private UUID messageUuid;
    @Column(name = "sender_uuid")
    private UUID senderUuid;
    private String content;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "conversations_id")
    private Conversation conversation;
}