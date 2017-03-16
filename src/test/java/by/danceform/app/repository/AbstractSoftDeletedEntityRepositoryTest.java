package by.danceform.app.repository;

import by.danceform.app.domain.SoftDeletedEntity;
import java.io.Serializable;

public abstract class AbstractSoftDeletedEntityRepositoryTest<R extends SoftDeletedEntityRepository<E, ID>, E extends
    SoftDeletedEntity<ID>, ID extends Serializable> extends AbstractAuditingEntityRepositoryTest<R, E, ID> {

}
