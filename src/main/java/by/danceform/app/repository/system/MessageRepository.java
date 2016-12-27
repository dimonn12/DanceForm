package by.danceform.app.repository.system;

import by.danceform.app.domain.system.Message;
import by.danceform.app.repository.AbstractEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Message entity.
 */
public interface MessageRepository extends AbstractEntityRepository<Message, Long> {

    @Query("SELECT mess FROM Message mess WHERE mess.sent = false")
    List<Message> findNotSent();

}
