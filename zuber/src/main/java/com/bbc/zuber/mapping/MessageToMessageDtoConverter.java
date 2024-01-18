package com.bbc.zuber.mapping;

import com.bbc.zuber.model.message.Message;
import com.bbc.zuber.model.message.dto.MessageDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class MessageToMessageDtoConverter implements Converter<Message, MessageDto> {

    @Override
    public MessageDto convert(MappingContext<Message, MessageDto> mappingContext) {
        Message message = mappingContext.getSource();

        return MessageDto.builder()
                .messageUuid(message.getMessageUuid())
                .senderUuid(message.getSenderUuid())
                .content(message.getContent())
                .dateTime(message.getDateTime())
                .build();
    }
}
