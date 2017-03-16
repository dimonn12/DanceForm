package by.danceform.app.repository;

import by.danceform.app.domain.SoftDeletedEntity;
import java.io.Serializable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SoftDeletedEntityRepository<T extends SoftDeletedEntity<ID>, ID extends Serializable>
    extends AbstractAuditingEntityRepository<T, ID> {

    @Override
    @Query("select e from #{#entityName} e where e.deleted=false AND e.id = ?1")
    T findOne(ID id);

}
