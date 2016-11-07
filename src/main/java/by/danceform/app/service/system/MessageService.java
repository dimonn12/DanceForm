package by.danceform.app.service.system;

import by.danceform.app.converter.system.MessageConverter;
import by.danceform.app.domain.system.Message;
import by.danceform.app.dto.system.MessageDTO;
import by.danceform.app.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Inject
    private MessageRepository messageRepository;

    @Inject
    private MessageConverter messageConverter;

    /**
     * Save a message.
     *
     * @param messageDTO the entity to save
     * @return the persisted entity
     */
    public MessageDTO save(MessageDTO messageDTO) {
        log.debug("Request to save Message : {}", messageDTO);
        Message message = messageConverter.convertToEntity(messageDTO);
        boolean isNew = !(null != messageDTO.getId() && null != messageRepository.findOne(messageDTO.getId()));
        if(isNew) {
            message.setDateCreated(ZonedDateTime.now());
            message.setDateSent(null);
            message.setSent(false);
        }
        message = messageRepository.save(message);
        MessageDTO result = messageConverter.convertToDto(message);
        return result;
    }

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MessageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        Page<Message> result = messageRepository.findAll(pageable);
        return result.map(message -> messageConverter.convertToDto(message));
    }

    /**
     * Get one message by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MessageDTO findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        Message message = messageRepository.findOne(id);
        MessageDTO messageDTO = messageConverter.convertToDto(message);
        return messageDTO;
    }

    /**
     * Delete the  message by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.delete(id);
    }
}
