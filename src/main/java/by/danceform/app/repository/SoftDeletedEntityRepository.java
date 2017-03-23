package by.danceform.app.repository;

import by.danceform.app.domain.SoftDeletedEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface SoftDeletedEntityRepository<T extends SoftDeletedEntity<ID>, ID extends Serializable>
    extends AbstractAuditingEntityRepository<T, ID> {

}
