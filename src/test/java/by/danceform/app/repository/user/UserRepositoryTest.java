package by.danceform.app.repository.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import by.danceform.app.domain.user.User;
import by.danceform.app.repository.AbstractAuditingEntityRepositoryTest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by Dmitry_Shanko on 1/3/2017.
 */
public class UserRepositoryTest extends AbstractAuditingEntityRepositoryTest<UserRepository, User, Long> {

    private static final Integer TOTAL_COUNT = 9;

    private static final String EXISTING_LOGIN = "test_login";
    private static final String EXISTING_PASSWORD_HASH = "test_password_hash";
    private static final String EXISTING_FIRST_NAME = "test_first_name";
    private static final String EXISTING_LAST_NAME = "test_last_name";
    private static final String EXISTING_EMAIL = "test_email";
    private static final String EXISTING_LANG_KEY = "en";
    private static final String EXISTING_ACTIVATION_KEY = "test_activation_key";
    private static final String EXISTING_RESET_KEY = "test_reset_key";

    private static final Boolean EXISTING_ACTIVATED = true;

    private static final String FIND_ONE_BY_ACTIVATION_KEY = "FIND_ONE_BY_ACT_KEY";
    private static final Long FIND_ONE_BY_ACTIVATION_KEY_ID = -998L;

    private static final String FIND_ONE_BY_RESET_KEY = "FIND_ONE_BY_RST_KEY";
    private static final Long FIND_ONE_BY_RESET_KEY_ID = -997L;

    private static final String FIND_ONE_BY_LOGIN = "FIND_ONE_BY_LOGIN";
    private static final Long FIND_ONE_BY_LOGIN_ID = -996L;

    private static final String FIND_ONE_BY_EMAIL = "FIND_ONE_BY_EMAIL";
    private static final Long FIND_ONE_BY_EMAIL_ID = -995L;

    private static final ZonedDateTime CREATED_BEFORE = ZonedDateTime.of(2017,
        2,
        02,
        0,
        0,
        0,
        0,
        ZoneId.systemDefault());

    private static final Integer NOT_ACTIVATED_USERS_COUNT = 2;

    private final User existing = new User();

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setLogin(EXISTING_LOGIN);
        existing.setPassword(EXISTING_PASSWORD_HASH);
        existing.setFirstName(EXISTING_FIRST_NAME);
        existing.setLastName(EXISTING_LAST_NAME);
        existing.setEmail(EXISTING_EMAIL);
        existing.setLangKey(EXISTING_LANG_KEY);
        existing.setActivationKey(EXISTING_ACTIVATION_KEY);
        existing.setResetKey(EXISTING_RESET_KEY);
        existing.setActivated(EXISTING_ACTIVATED);
        initExistingAuditingEntity(existing);
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected User getExistingEntity() {
        return existing;
    }

    @Test
    public void testFindByActivationKey() {
        Optional<User> activationKeyUserOpt = getRepository().findOneByActivationKey(FIND_ONE_BY_ACTIVATION_KEY);
        assertThat(activationKeyUserOpt.isPresent(), is(true));
        assertThat(activationKeyUserOpt.get().getId(), is(FIND_ONE_BY_ACTIVATION_KEY_ID));
    }

    @Test
    public void testFindByResetKey() {
        Optional<User> resetKeyUserOpt = getRepository().findOneByResetKey(FIND_ONE_BY_RESET_KEY);
        assertThat(resetKeyUserOpt.isPresent(), is(true));
        assertThat(resetKeyUserOpt.get().getId(), is(FIND_ONE_BY_RESET_KEY_ID));
    }

    @Test
    public void testFindByLogin() {
        Optional<User> loginUserOpt = getRepository().findOneByLogin(FIND_ONE_BY_LOGIN);
        assertThat(loginUserOpt.isPresent(), is(true));
        assertThat(loginUserOpt.get().getId(), is(FIND_ONE_BY_LOGIN_ID));
    }

    @Test
    public void testFindByEmail() {
        Optional<User> emailUserOpt = getRepository().findOneByEmail(FIND_ONE_BY_EMAIL);
        assertThat(emailUserOpt.isPresent(), is(true));
        assertThat(emailUserOpt.get().getId(), is(FIND_ONE_BY_EMAIL_ID));
    }

    @Test
    public void testFindByIdWithAuthorities() {
        final Page<User> entitiesWithAuthorities = getRepository()
            .findAllWithAuthorities(new PageRequest(0, TOTAL_COUNT));
        assertThat(entitiesWithAuthorities.getContent().size(), is(TOTAL_COUNT));
        for (User user : entitiesWithAuthorities.getContent()) {
            assertThat(user.getAuthorities(), notNullValue());
        }
    }

    @Test
    public void testFindAllByActivatedIsFalseAndCreatedDateBefore() {
        List<User> notActivatedUsers = getRepository().findAllByActivatedIsFalseAndCreatedDateBefore(CREATED_BEFORE);
        assertThat(notActivatedUsers.size(), is(NOT_ACTIVATED_USERS_COUNT));
    }

    @Test
    public void testDelete() {
        final User createdUser = getRepository().save(getNewEntity());
        assertThat(createdUser, not(nullValue()));
        final Long userId = createdUser.getId();
        assertThat(userId, not(nullValue()));
        getRepository().delete(createdUser);
        getRepository().flush();
        final User deletedUser = getRepository().findOne(userId);
        assertThat(deletedUser, nullValue());
    }

    @Override
    protected User getNewEntity() {
        final User user = new User();
        user.setLogin(randomString(20, "qwertyuiopasdfghjklzxcvbnm"));
        user.setPassword(randomString(60));
        user.setFirstName("new_first_name");
        user.setLastName("new_last_name");
        user.setLangKey("ru");
        user.setEmail(randomString(10, "qwertyuiopasdfghjklzxcvbnm").concat("@mail.com"));
        user.setActivationKey("new_activation_key");
        user.setResetKey("new_reset_key");
        user.setResetDate(ZonedDateTime.now(ZoneId.systemDefault()));
        user.setActivated(true);
        generateAuditingFieldsForNewEntity(user);
        return user;
    }
}
