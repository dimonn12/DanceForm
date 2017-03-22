package by.danceform.app.repository.couple;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import by.danceform.app.domain.couple.RegisteredCouple;
import by.danceform.app.repository.AbstractSoftDeletedEntityRepositoryTest;
import by.danceform.app.repository.competition.CompetitionCategoryRepository;
import by.danceform.app.repository.config.DanceClassRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisteredCoupleRepositoryTest extends
    AbstractSoftDeletedEntityRepositoryTest<RegisteredCoupleRepository, RegisteredCouple, Long> {

    private static final String EXISTING_PARTNER_1_NAME = "test_partner_1_name";
    private static final String EXISTING_PARTNER_2_NAME = "test_partner_2_name";
    private static final String EXISTING_PARTNER_1_SURNAME = "test_partner_1_surname";
    private static final String EXISTING_PARTNER_2_SURNAME = "test_partner_2_surname";

    private static final LocalDate EXISTING_PARTNER_1_DATE_OF_BIRTH = LocalDate.of(2016, 10, 10);
    private static final LocalDate EXISTING_PARTNER_2_DATE_OF_BIRTH = LocalDate.of(2016, 11, 11);

    private static final String EXISTING_LOCATION = "test_location";
    private static final String EXISTING_ORGANIZATION = "test_organization";

    private static final String EXISTING_TRAINER_1 = "test_trainer_1";
    private static final String EXISTING_TRAINER_2 = "test_trainer_2";

    private static final Long EXISTING_COMPETITION_CATEGORY_ID = -999L;
    private static final Long EXISTING_DANCE_CLASS_ID = -999L;

    private static final Long GROUP_UNIQUE_BY_COMPETITION_ID = -998L;
    private static final Integer GROUP_UNIQUE_BY_COMPETITION_ID_RESULT = 3;

    private static final Long FIND_ALL_BY_CATEGORY_ID = -997L;
    private static final Integer FIND_ALL_BY_CATEGORY_ID_SIZE = 2;

    private final RegisteredCouple existing = new RegisteredCouple();

    @Autowired
    private CompetitionCategoryRepository competitionCategoryRepository;

    @Autowired
    private DanceClassRepository danceClassRepository;

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setPartner1Name(EXISTING_PARTNER_1_NAME);
        existing.setPartner2Name(EXISTING_PARTNER_2_NAME);
        existing.setPartner1Surname(EXISTING_PARTNER_1_SURNAME);
        existing.setPartner2Surname(EXISTING_PARTNER_2_SURNAME);
        existing.setPartner1DateOfBirth(EXISTING_PARTNER_1_DATE_OF_BIRTH);
        existing.setPartner2DateOfBirth(EXISTING_PARTNER_2_DATE_OF_BIRTH);
        existing.setLocation(EXISTING_LOCATION);
        existing.setOrganization(EXISTING_ORGANIZATION);
        existing.setTrainer1(EXISTING_TRAINER_1);
        existing.setTrainer2(EXISTING_TRAINER_2);
        existing.setCompetitionCategory(competitionCategoryRepository.findOne(EXISTING_COMPETITION_CATEGORY_ID));
        existing.setPartner1DanceClassLA(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        existing.setPartner1DanceClassST(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        existing.setPartner2DanceClassLA(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        existing.setPartner2DanceClassST(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));

        initExistingAuditingEntity(existing);
    }

    @Test
    public void testGroupUniqueByCompetitionId() {
        List<Integer> groups = getRepository().groupUniqueByCompetitionId(GROUP_UNIQUE_BY_COMPETITION_ID);
        assertThat(groups.size(), equalTo(GROUP_UNIQUE_BY_COMPETITION_ID_RESULT));
    }

    @Test
    public void testFindAllByCategoryId() {
        List<RegisteredCouple> registeredCouples = getRepository().findAllByCategoryId(FIND_ALL_BY_CATEGORY_ID);
        assertThat(registeredCouples.size(), equalTo(FIND_ALL_BY_CATEGORY_ID_SIZE));
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected Long getIdForDeletedEntity() {
        return EXISTING_DELETED_ID;
    }

    @Override
    protected RegisteredCouple getExistingEntity() {
        return existing;
    }

    @Override
    protected RegisteredCouple getNewEntity() {
        RegisteredCouple newCouple = new RegisteredCouple();
        newCouple.setPartner1Name(randomString(5));
        newCouple.setPartner1Surname(randomString(6));
        newCouple.setPartner2Name(randomString(7));
        newCouple.setPartner2Surname(randomString(8));

        newCouple.setPartner1DateOfBirth(LocalDate.now());
        newCouple.setPartner2DateOfBirth(LocalDate.now());

        newCouple.setCompetitionCategory(competitionCategoryRepository.findOne(EXISTING_COMPETITION_CATEGORY_ID));
        newCouple.setPartner1DanceClassLA(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        newCouple.setPartner1DanceClassST(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        newCouple.setPartner2DanceClassLA(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        newCouple.setPartner2DanceClassST(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));

        newCouple.setLocation(randomString(5));
        newCouple.setTrainer1(randomString(5));
        newCouple.setTrainer2(randomString(5));
        newCouple.setOrganization(randomString(5));

        generateAuditingFieldsForNewEntity(newCouple);
        return newCouple;
    }

}
