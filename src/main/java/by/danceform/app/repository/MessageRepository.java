package by.danceform.app.repository;

import by.danceform.app.domain.system.Message;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Message entity.
 */
@SuppressWarnings("unused")
public interface MessageRepository extends JpaRepository<Message,Long> {

}
