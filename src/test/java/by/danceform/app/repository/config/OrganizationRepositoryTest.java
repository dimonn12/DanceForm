package by.danceform.app.repository.config;

import by.danceform.app.domain.config.Organization;
import by.danceform.app.domain.config.Trainer;
import by.danceform.app.repository.AbstractRepositoryTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Dmitry_Shanko on 12/27/2016.
 */
public class OrganizationRepositoryTest extends AbstractRepositoryTest<OrganizationRepository, Organization, Long> {

    private static final String EXISTING_NAME = "Test_name";
    private static final boolean EXISTING_VISIBLE = true;

    private static final Integer VISIBLE_AMOUNT = 2;

    private final Organization existing = new Organization();

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setName(EXISTING_NAME);
        existing.setVisible(EXISTING_VISIBLE);
    }

    @Test
    public void testFindVisible() {
        final List<Organization> visibleOrganizations = getRepository().findVisible();
        assertThat(visibleOrganizations.size(), is(VISIBLE_AMOUNT));
        for(Organization organization : visibleOrganizations) {
            assertThat(organization.isVisible(), is(true));
        }
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected Organization getExistingEntity() {
        return existing;
    }

    @Override
    protected Organization getNewEntity() {
        final Organization organization = new Organization();
        organization.setName(RandomStringUtils.random(5));
        return organization;
    }
}
