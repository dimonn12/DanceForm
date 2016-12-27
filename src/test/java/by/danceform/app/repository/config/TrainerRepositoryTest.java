package by.danceform.app.repository.config;

import by.danceform.app.domain.config.Trainer;
import by.danceform.app.repository.AbstractRepositoryTest;
import by.danceform.app.service.util.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;

/**
 * Created by Dmitry_Shanko on 12/27/2016.
 */
public class TrainerRepositoryTest extends AbstractRepositoryTest<TrainerRepository, Trainer, Long> {

    private static final Long EXISTING_ID = 1L;

    private static final String EXISTING_NAME = "Павел";
    private static final String EXISTING_SURNAME = "Ярцев";
    private static final boolean EXISTING_VISIBLE = true;

    private final Trainer existing = new Trainer();

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setName(EXISTING_NAME);
        existing.setSurname(EXISTING_SURNAME);
        existing.setVisible(EXISTING_VISIBLE);
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected Trainer getExistingEntity() {
        return existing;
    }

    @Override
    protected Trainer getNewEntity() {
        Trainer trainer = new Trainer();
        trainer.setName(RandomStringUtils.random(5));
        trainer.setSurname(RandomStringUtils.random(10));
        return trainer;
    }
}
