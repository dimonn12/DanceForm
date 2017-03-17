package by.danceform.app.repository.config;

import by.danceform.app.domain.config.DanceClass;
import by.danceform.app.repository.AbstractRepositoryTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Dmitry_Shanko on 12/27/2016.
 */
public class DanceClassRepositoryTest extends AbstractRepositoryTest<DanceClassRepository, DanceClass, Long> {

    private static final String EXISTING_NAME = "Test_name";
    private static final String EXISTING_DESCRIPTION = "Test_description";
    private static final String EXISTING_SYMBOL = "T1";
    private static final Integer EXISTING_TRANSFER_SCORE = 100;
    private static final Integer EXISTING_WEIGHT = -1000;

    private static final Long EXISTING_ID_2 = -998L;
    private static final Long EXISTING_ID_3 = -997L;

    private final DanceClass existing = new DanceClass();

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setName(EXISTING_NAME);
        existing.setDescription(EXISTING_DESCRIPTION);
        existing.setSymbol(EXISTING_SYMBOL);
        existing.setTransferScore(EXISTING_TRANSFER_SCORE);
        existing.setWeight(EXISTING_WEIGHT);
    }

    @Test
    public void testNextDanceClassReferences() {
        final DanceClass danceClass = getRepository().findOne(EXISTING_ID_2);
        assertThat(danceClass, notNullValue());
        assertThat(danceClass.getNextDanceClass(), notNullValue());
        assertThat(danceClass.getNextDanceClass().getId(), equalTo(EXISTING_ID));
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Transactional
    public void shouldFailInNotUniqueNextDanceClass() {
        final DanceClass newToSave = getNewEntity();
        newToSave.setNextDanceClass(getRepository().findOne(EXISTING_ID));
        getRepository().save(newToSave);
    }

    @Override
    @Test
    @Transactional
    public void testSaveNewEntity() {
        final DanceClass entity = getNewEntity();
        entity.setNextDanceClass(getRepository().findOne(EXISTING_ID_3));
        final DanceClass copiedEntity = entity.deepCopy();
        DanceClass savedEntity = getRepository().save(entity);
        DanceClass entityInDatabase = getRepository().findOne(savedEntity.getId());
        assertThat(entityInDatabase, not(nullValue()));
        assertThat(entityInDatabase.getId(), not(nullValue()));
        compareFieldValues(copiedEntity, entityInDatabase, getExcludedComparingFieldsOnCreate());
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected DanceClass getExistingEntity() {
        return existing;
    }

    @Override
    protected DanceClass getNewEntity() {
        final DanceClass danceClass = new DanceClass();
        danceClass.setName(RandomStringUtils.random(10));
        danceClass.setDescription(RandomStringUtils.random(20));
        danceClass.setSymbol(RandomStringUtils.random(2));
        danceClass.setWeight(RandomUtils.nextInt(1000, 2000));
        danceClass.setTransferScore(RandomUtils.nextInt(1000, 2000));
        return danceClass;
    }
}
