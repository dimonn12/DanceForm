package by.danceform.app.repository;

import by.danceform.app.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by dimonn12 on 28.12.2016.
 */
@NoRepositoryBean
public interface AbstractEntityRepository<T extends AbstractEntity<ID>, ID extends Serializable>
    extends JpaRepository<T, ID> {

}
