package by.danceform.app.repository.config;

import by.danceform.app.domain.config.Location;
import by.danceform.app.repository.AbstractRepositoryTest;
import org.junit.Before;

/**
 * Created by Dmitry_Shanko on 12/27/2016.
 */
public class LocationRepositoryTest extends AbstractRepositoryTest<LocationRepository, Location, Long> {

    private static final String EXISTING_NAME = "Test_name";

    private final Location existing = new Location();

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setName(EXISTING_NAME);
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected Location getExistingEntity() {
        return existing;
    }

    @Override
    protected Location getNewEntity() {
        final Location location = new Location();
        location.setName(randomString(5));
        return location;
    }
}
