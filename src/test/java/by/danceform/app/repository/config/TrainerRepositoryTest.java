package by.danceform.app.repository.config;

import by.danceform.app.domain.config.Trainer;
import by.danceform.app.repository.BaseRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Dmitry_Shanko on 12/27/2016.
 */
public class TrainerRepositoryTest extends BaseRepositoryTest<TrainerRepository, Trainer, Long> {

    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    public void shouldFindById() {
        List<Trainer> trainer = trainerRepository.findAll();
        assertThat(trainer, notNullValue());
    }

}
