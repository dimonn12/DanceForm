package by.danceform.app.repository.system;

import by.danceform.app.domain.system.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Message entity.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT mess FROM Message mess WHERE mess.sent = false")
    List<Message> findNotSent();

}
