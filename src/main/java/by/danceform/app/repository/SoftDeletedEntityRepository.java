package by.danceform.app.repository;

import by.danceform.app.domain.SoftDeletedEntity;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SoftDeletedEntityRepository<T extends SoftDeletedEntity<ID>, ID extends Serializable>
    extends AbstractAuditingEntityRepository<T, ID> {

}
