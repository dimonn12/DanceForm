package by.danceform.app.repository.competition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import by.danceform.app.domain.competition.Competition;
import by.danceform.app.domain.competition.CompetitionWithDetails;
import by.danceform.app.repository.AbstractSoftDeletedEntityRepositoryTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;

public class CompetitionRepositoryTest extends
    AbstractSoftDeletedEntityRepositoryTest<CompetitionRepository, Competition, Long> {

    protected static final Long EXISTING_DELETED_COMPETITION_ID = -995L;

    private static final String EXISTING_NAME = "Test name";
    private static final String EXISTING_ORGANIZER = "Test Org";
    private static final String EXISTING_LOCATION = "Test Loc";
    private static final Boolean EIXSTING_VISIBLE = Boolean.TRUE;

    private static final Long EXISTING_BANNER_IMAGE_ID = -998L;
    private static final Long EXISTING_DETAILS_DOCUMENT_ID = -997L;

    private static final LocalDate EXISTING_START_DATE = LocalDate.of(2016, 10, 01);
    private static final LocalDate EXISTING_END_DATE = LocalDate.of(2016, 10, 02);

    private static final LocalDateTime EXISTING_REGISTRATION_CLOSES_TIME = LocalDateTime.of(2017, 2, 15, 11, 12, 13);

    private static final Long EXISTING_DOCUMENT_ID = -999L;

    private static final Integer VISIBLE_LIST_SIZE = 4;

    private final Competition existing = new Competition();

    @Before
    public void init() {
        existing.setName(EXISTING_NAME);
        existing.setOrganizer(EXISTING_ORGANIZER);
        existing.setLocation(EXISTING_LOCATION);
        existing.setStartDate(EXISTING_START_DATE);
        existing.setEndDate(EXISTING_END_DATE);
        existing.setVisible(EIXSTING_VISIBLE);
        existing.setRegistrationClosesTime(EXISTING_REGISTRATION_CLOSES_TIME);
        existing.setBannerImageId(EXISTING_BANNER_IMAGE_ID);
        existing.setDetailsDocumentId(EXISTING_DETAILS_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void testUpdateDocumentAttachment() {
        final Competition newEntity = getNewEntity();
        newEntity.setBannerImageId(EXISTING_DOCUMENT_ID);
        newEntity.setDetailsDocumentId(EXISTING_DOCUMENT_ID);
        final Competition savedCompetition = getRepository().save(newEntity);
        assertThat(savedCompetition.getBannerImageId(), equalTo(EXISTING_DOCUMENT_ID));
        assertThat(savedCompetition.getDetailsDocumentId(), equalTo(EXISTING_DOCUMENT_ID));
        getRepository().updateDocumentAttachment(EXISTING_DOCUMENT_ID);
        getRepository().flush();
        final Competition updatedCompetition = getRepository().findOne(savedCompetition.getId());
        assertThat(updatedCompetition.getBannerImageId(), equalTo(EXISTING_DOCUMENT_ID));
        assertThat(updatedCompetition.getDetailsDocumentId(), is(nullValue()));
    }

    @Test
    @Transactional
    public void testUpdateImageAttachment() {
        final Competition newEntity = getNewEntity();
        newEntity.setBannerImageId(EXISTING_DOCUMENT_ID);
        newEntity.setDetailsDocumentId(EXISTING_DOCUMENT_ID);
        final Competition savedCompetition = getRepository().save(newEntity);
        assertThat(savedCompetition.getBannerImageId(), equalTo(EXISTING_DOCUMENT_ID));
        assertThat(savedCompetition.getDetailsDocumentId(), equalTo(EXISTING_DOCUMENT_ID));
        getRepository().updateImageAttachment(EXISTING_DOCUMENT_ID);
        getRepository().flush();
        final Competition updatedCompetition = getRepository().findOne(savedCompetition.getId());
        assertThat(updatedCompetition.getBannerImageId(), is(nullValue()));
        assertThat(updatedCompetition.getDetailsDocumentId(), equalTo(EXISTING_DOCUMENT_ID));
    }

    @Test
    public void testFindVisible() {
        List<Competition> competitions = getRepository().findVisible();
        assertThat(competitions.size(), equalTo(VISIBLE_LIST_SIZE));
    }

    @Test
    public void testFindOneWithDetails() {
        CompetitionWithDetails competitionWithDetails = getRepository().findOneWithDetails
            (getExistingId());
        assertThat(competitionWithDetails, notNullValue());
        compareFieldValues(getExistingEntity(), competitionWithDetails.getCompetition(),
            getExcludedComparingFieldsOnFind());
        assertThat(competitionWithDetails.getUploadedDocument(), notNullValue());
        assertThat(competitionWithDetails.getUploadedDocument().getId(), equalTo(EXISTING_DETAILS_DOCUMENT_ID));
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected Competition getExistingEntity() {
        return existing;
    }

    @Override
    protected Competition getNewEntity() {
        Competition newEntity = new Competition();
        newEntity.setName(randomString(10));
        newEntity.setLocation(randomString(5));
        newEntity.setOrganizer(randomString(5));
        newEntity.setVisible(true);
        newEntity.setStartDate(LocalDate.now());
        newEntity.setEndDate(LocalDate.now().plusDays(10L));
        return newEntity;
    }

    @Override
    protected Long getIdForDeletedEntity() {
        return EXISTING_DELETED_COMPETITION_ID;
    }
}
