package by.danceform.app.repository;

import by.danceform.app.domain.AbstractAuditingEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractAuditingEntityRepository<T extends AbstractAuditingEntity<ID>, ID extends Serializable>
    extends AbstractEntityRepository<T, ID> {

}
