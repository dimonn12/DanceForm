package by.danceform.app.repository;

import by.danceform.app.domain.SoftDeletedEntity;
import java.io.Serializable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SoftDeletedEntityRepository<T extends SoftDeletedEntity<ID>, ID extends Serializable>
    extends AbstractAuditingEntityRepository<T, ID> {

    @Query("select e from #{#entityName} e where e.deleted=true AND e.id = ?1")
    T findDeletedEntity(ID id);

    @Modifying
    @Query("update #{#entityName} e set e.deleted=true where e.id=?1")
    void softDelete(ID id);

}
