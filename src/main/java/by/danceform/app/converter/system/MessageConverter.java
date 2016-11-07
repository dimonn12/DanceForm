package by.danceform.app.converter.system;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.system.Message;
import by.danceform.app.dto.system.MessageDTO;
import org.springframework.stereotype.Component;

/**
 * Created by dimonn12 on 07.11.2016.
 */
@Component
public class MessageConverter extends AbstractConverter<Message, MessageDTO, Long> {

    @Override
    protected MessageDTO convertEntityToDto(Message entity, MessageDTO dto) {
        dto.setFromEmail(trimIfNull(entity.getFromEmail()));
        dto.setSubject(trimIfNull(entity.getSubject()));
        dto.setContent(trimIfNull(entity.getContent()));
        dto.setDateCreated(entity.getDateCreated());
        dto.setDateSent(entity.getDateSent());
        dto.setSent(entity.isSent());
        return dto;
    }

    @Override
    protected Message convertDtoToEntity(MessageDTO dto, Message entity) {
        entity.setFromEmail(trimIfNull(dto.getFromEmail()));
        entity.setSubject(trimIfNull(dto.getSubject()));
        entity.setContent(trimIfNull(dto.getContent()));
        entity.setDateCreated(dto.getDateCreated());
        entity.setDateSent(dto.getDateSent());
        entity.setSent(dto.getSent());
        return entity;
    }

    @Override
    protected MessageDTO getNewDTO() {
        return new MessageDTO();
    }

    @Override
    protected Message getNewEntity() {
        return new Message();
    }
}
