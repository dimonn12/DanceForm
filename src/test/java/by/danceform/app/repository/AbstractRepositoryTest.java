package by.danceform.app.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import by.danceform.app.DanceFormApp;
import by.danceform.app.domain.AbstractEntity;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import javax.persistence.Id;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;


/**
 * Created by Dmitry_Shanko on 12/27/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public abstract class AbstractRepositoryTest<R extends AbstractEntityRepository<E, ID>, E extends AbstractEntity<ID>, ID extends Serializable> {

    private final static Long ENTITY_ALLOCATION_SIZE = 1L;

    protected static final Long EXISTING_ID = -999L;

    @Autowired
    private R repository;

    protected R getRepository() {
        return repository;
    }

    protected abstract ID getExistingId();

    protected abstract E getExistingEntity();

    protected abstract E getNewEntity();

    @Test
    public void testFindAll() {
        final List<E> all = repository.findAll();
        assertThat(all, notNullValue());
        assertThat(all.size(), not(0));
    }

    @Test
    @Transactional
    public void testFindById() {
        final E entityInDatabase = repository.findOne(getExistingId());
        final E existingEntity = getExistingEntity();
        assertThat(entityInDatabase, notNullValue());
        compareFieldValues(existingEntity, entityInDatabase, getExcludedComparingFieldsOnFind());
    }

    @Test
    @Transactional
    public void testSaveNewEntity() {
        final E entity = getNewEntity();
        final E copiedEntity = entity.deepCopy();
        final E savedEntity = repository.save(entity);
        final E entityInDatabase = repository.findOne(savedEntity.getId());
        assertThat(entityInDatabase, not(nullValue()));
        assertThat(entityInDatabase.getId(), not(nullValue()));
        compareFieldValues(copiedEntity, entityInDatabase, getExcludedComparingFieldsOnCreate());
    }

    @Test
    @Transactional
    public void shouldIncreaseEntityIdOnEntityAllocationSize() {
        final E firstEntity = getNewEntity();
        repository.save(firstEntity);
        final E secondEntity = getNewEntity();
        repository.save(secondEntity);
        if(firstEntity.getId() instanceof Long) {
            assertThat(((Long)secondEntity.getId()) - ((Long)firstEntity.getId()), equalTo(ENTITY_ALLOCATION_SIZE));
        }
    }

    protected void compareFieldValues(final E entity, final E entityInDatabase, final List<String> excludedFieldNames) {
        try {
            final BeanInfo modelInfo = Introspector.getBeanInfo(entity.getClass());
            for(final PropertyDescriptor fieldInfo : modelInfo.getPropertyDescriptors()) {
                final Method getter = fieldInfo.getReadMethod();
                if (excludedFieldNames.contains(fieldInfo.getName())) {
                    continue;
                }
                final Field field = ReflectionUtils.findField(entity.getClass(), fieldInfo.getName());
                if(null != getter && null != field) {
                    compareFieldValue(entity, entityInDatabase, getter, field);
                }
            }
        } catch(ReflectiveOperationException | IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }

    private void compareFieldValue(final E entity, final E entityInDatabase, final Method getter, final Field field)
        throws ReflectiveOperationException, IntrospectionException {
        final Object entityFieldValue = getter.invoke(entity);
        final Object databaseFieldValue = getter.invoke(entityInDatabase);
        if(field.getAnnotation(Id.class) != null) {
            assertThat(databaseFieldValue, not(nullValue()));
        } else {
            if (null != entityFieldValue && !checkAdditionalFieldAnnotations(databaseFieldValue, field)) {
                assertThat(databaseFieldValue, equalTo(entityFieldValue));
            }
        }
    }

    protected boolean checkAdditionalFieldAnnotations(final Object databaseFieldValue, final Field field) {
        return false;
    }

    protected List<String> getExcludedComparingFieldsOnCreate() {
        return Collections.emptyList();
    }

    protected List<String> getExcludedComparingFieldsOnFind() {
        return Collections.emptyList();
    }

}
