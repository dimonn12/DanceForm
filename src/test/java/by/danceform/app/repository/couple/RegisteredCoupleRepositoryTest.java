package by.danceform.app.repository.couple;

import by.danceform.app.domain.couple.RegisteredCouple;
import by.danceform.app.repository.AbstractSoftDeletedEntityRepositoryTest;
import by.danceform.app.repository.competition.CompetitionCategoryRepository;
import by.danceform.app.repository.config.DanceClassRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
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

    private static final String EXISTING_CREATED_BY = "created_by_test";
    private static final String EXISTING_LAST_MODIFIED_BY = "last_modified_by_test";

    private static final ZonedDateTime EXISTING_CREATED_DATE = ZonedDateTime.of(2017,
        2,
        15,
        11,
        12,
        13,
        0,
        ZoneId.systemDefault());
    private static final ZonedDateTime EXISTING_LAST_MODIFIED_DATE = ZonedDateTime.of(2017,
        2,
        16,
        12,
        13,
        14,
        0,
        ZoneId.systemDefault());

    private static final Long EXISTING_COMPETITION_CATEGORY_ID = -999L;
    private static final Long EXISTING_DANCE_CLASS_ID = -999L;

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

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected RegisteredCouple getExistingEntity() {
        return existing;
    }

    @Override
    protected RegisteredCouple getNewEntity() {
        RegisteredCouple newCouple = new RegisteredCouple();
        newCouple.setPartner1Name(RandomStringUtils.random(5));
        newCouple.setPartner1Surname(RandomStringUtils.random(6));
        newCouple.setPartner2Name(RandomStringUtils.random(7));
        newCouple.setPartner2Surname(RandomStringUtils.random(8));

        newCouple.setPartner1DateOfBirth(LocalDate.now());
        newCouple.setPartner2DateOfBirth(LocalDate.now());

        newCouple.setCompetitionCategory(competitionCategoryRepository.findOne(EXISTING_COMPETITION_CATEGORY_ID));
        newCouple.setPartner1DanceClassLA(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        newCouple.setPartner1DanceClassST(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        newCouple.setPartner2DanceClassLA(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));
        newCouple.setPartner2DanceClassST(danceClassRepository.findOne(EXISTING_DANCE_CLASS_ID));

        newCouple.setLocation(RandomStringUtils.random(5));
        newCouple.setTrainer1(RandomStringUtils.random(5));
        newCouple.setTrainer2(RandomStringUtils.random(5));
        newCouple.setOrganization(RandomStringUtils.random(5));

        generateAuditingFieldsForNewEntity(newCouple);
        return newCouple;
    }
}
