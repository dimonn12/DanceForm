package by.danceform.app.repository.config;

import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.repository.AbstractRepositoryTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;

/**
 * Created by Dmitry_Shanko on 12/27/2016.
 */
public class AgeCategoryRepositoryTest extends AbstractRepositoryTest<AgeCategoryRepository, AgeCategory, Long> {

    private static final String EXISTING_NAME = "Test_name";
    private static final Integer EXISTING_MIN_AGE = -100;
    private static final Integer EXISTING_MAX_AGE = 100;

    private final AgeCategory existing = new AgeCategory();

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setName(EXISTING_NAME);
        existing.setMinAge(EXISTING_MIN_AGE);
        existing.setMaxAge(EXISTING_MAX_AGE);
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected AgeCategory getExistingEntity() {
        return existing;
    }

    @Override
    protected AgeCategory getNewEntity() {
        final AgeCategory ageCategory = new AgeCategory();
        ageCategory.setName(RandomStringUtils.random(5));
        ageCategory.setMinAge(RandomUtils.nextInt(10, 100));
        ageCategory.setMaxAge(RandomUtils.nextInt(10, 100));
        return ageCategory;
    }
}
