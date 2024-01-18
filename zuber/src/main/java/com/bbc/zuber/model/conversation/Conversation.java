package com.bbc.zuber.model.conversation;

import com.bbc.zuber.model.message.Message;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "conversations")
@Builder
@Getter
@Setter
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversations_seq")
    @SequenceGenerator(name = "conversations_seq", sequenceName = "conversations_seq", allocationSize = 1)
    private Long id;
    @Column(name = "conversation_uuid")
    private UUID conversationUuid;
    @Column(name = "ride_info_id")
    private Long rideInfoId;
    @OneToMany(mappedBy = "conversation", cascade = ALL)
    @OrderBy("dateTime ASC")
    private LinkedList<Message> messages;

    public void addMessage(Message message) {
        Optional.ofNullable(messages)
                .orElseGet(LinkedList::new)
                .add(message);
    }
}
