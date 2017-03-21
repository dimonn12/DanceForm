package by.danceform.app.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import by.danceform.app.domain.SoftDeletedEntity;
import java.io.Serializable;
import java.util.List;
import org.junit.Test;

public abstract class AbstractSoftDeletedEntityRepositoryTest<R extends SoftDeletedEntityRepository<E, ID>, E extends
    SoftDeletedEntity<ID>, ID extends Serializable> extends AbstractAuditingEntityRepositoryTest<R, E, ID> {

    protected static final Long EXISTING_DELETED_ID = -998L;

    private static final Boolean DELETED_FALSE = Boolean.FALSE;
    private static final Boolean DELETED_TRUE = Boolean.TRUE;

    @Test
    public void testSoftDelete() {
        final E createdEntity = getRepository().save(getNewEntity());
        final ID entityId = createdEntity.getId();
        assertThat(createdEntity.getDeleted(), equalTo(DELETED_FALSE));
        getRepository().delete(entityId);
        final E deletedEntityShouldNotExist = getRepository().findOne(entityId);
        assertThat(deletedEntityShouldNotExist, nullValue());
    }

    @Test
    public void testFindDeletedEntity() {
        final E deletedEntity = getRepository().findOne(getIdForDeletedEntity());
        assertThat(deletedEntity, nullValue());
        final List<E> allEntities = getRepository().findAll();
        for (E entity: allEntities) {
            assertThat(entity.getId(), not(equalTo(getIdForDeletedEntity())));
        }
    }

    protected abstract ID getIdForDeletedEntity();

}
