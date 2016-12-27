package by.danceform.app.repository;

import by.danceform.app.DanceFormApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Dmitry_Shanko on 12/27/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
@Transactional
public abstract class BaseRepositoryTest<R extends JpaRepository<T, ID>, T, ID extends Serializable> {

}
