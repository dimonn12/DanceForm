package by.danceform.app.repository;

import by.danceform.app.domain.AbstractAuditingEntity;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractAuditingEntityRepository<T extends AbstractAuditingEntity<ID>, ID extends Serializable>
    extends AbstractEntityRepository<T, ID> {

}
