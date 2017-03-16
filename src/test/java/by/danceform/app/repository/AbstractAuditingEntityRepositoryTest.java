package by.danceform.app.repository;

import by.danceform.app.domain.AbstractAuditingEntity;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class AbstractAuditingEntityRepositoryTest<R extends AbstractAuditingEntityRepository<E, ID>, E extends
    AbstractAuditingEntity<ID>, ID extends Serializable> extends AbstractRepositoryTest<R, E, ID> {

    protected static final String EXISTING_CREATED_BY = "test_created_by";
    protected static final String EXISTING_LAST_MODIFIED_BY = "test_last_modified_by";

    protected static final ZonedDateTime EXISTING_CREATED_DATE = ZonedDateTime.of(2017,
        2,
        15,
        11,
        12,
        13,
        0,
        ZoneId.systemDefault());
    protected static final ZonedDateTime EXISTING_LAST_MODIFIED_DATE = ZonedDateTime.of(2017,
        2,
        16,
        12,
        13,
        14,
        0,
        ZoneId.systemDefault());

    protected void initExistingAuditingEntity(AbstractAuditingEntity<ID> existing) {
        existing.setCreatedBy(EXISTING_CREATED_BY);
        existing.setLastModifiedBy(EXISTING_LAST_MODIFIED_BY);
        existing.setCreatedDate(EXISTING_CREATED_DATE);
        existing.setLastModifiedDate(EXISTING_LAST_MODIFIED_DATE);
    }

    protected void generateAuditingFieldsForNewEntity(AbstractAuditingEntity<ID> newEntity) {
        newEntity.setCreatedBy("new_created_by");
        newEntity.setCreatedDate(ZonedDateTime.now(ZoneId.systemDefault()));
        newEntity.setLastModifiedBy("new_last_modified_by");
        newEntity.setLastModifiedDate(ZonedDateTime.now(ZoneId.systemDefault()));
    }
}
