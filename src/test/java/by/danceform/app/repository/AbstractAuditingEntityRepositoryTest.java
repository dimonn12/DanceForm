package by.danceform.app.repository;

import by.danceform.app.domain.AbstractAuditingEntity;
import by.danceform.app.security.AuthoritiesConstants;
import org.junit.Test;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractAuditingEntityRepositoryTest<R extends AbstractAuditingEntityRepository<E, ID>, E extends AbstractAuditingEntity<ID>, ID extends Serializable>
    extends AbstractRepositoryTest<R, E, ID> {

    protected static final String ADMIN_USERNAME = "admin";
    protected static final String SYSTEM_USERNAME = "system";

    protected static final String EXISTING_CREATED_BY = "test_created_by";
    protected static final String EXISTING_LAST_MODIFIED_BY = "test_last_modified_by";

    protected static final ZonedDateTime EXISTING_CREATED_DATE = ZonedDateTime.of(2017,
        2,
        15,
        11,
        12,
        13,
        0,
        ZoneId.systemDefault());
    protected static final ZonedDateTime EXISTING_LAST_MODIFIED_DATE = ZonedDateTime.of(2017,
        2,
        16,
        12,
        13,
        14,
        0,
        ZoneId.systemDefault());

    protected void initExistingAuditingEntity(final AbstractAuditingEntity<ID> existing) {
        existing.setCreatedBy(EXISTING_CREATED_BY);
        existing.setLastModifiedBy(EXISTING_LAST_MODIFIED_BY);
        existing.setCreatedDate(EXISTING_CREATED_DATE);
        existing.setLastModifiedDate(EXISTING_LAST_MODIFIED_DATE);
    }

    protected void generateAuditingFieldsForNewEntity(final AbstractAuditingEntity<ID> newEntity) {
        newEntity.setCreatedBy(EXISTING_CREATED_BY);
        newEntity.setCreatedDate(ZonedDateTime.now(ZoneId.systemDefault()));
        newEntity.setLastModifiedBy(EXISTING_LAST_MODIFIED_BY);
        newEntity.setLastModifiedDate(ZonedDateTime.now(ZoneId.systemDefault()));
    }

    @Test
    @Transactional
    @Override
    public void testSaveNewEntity() {
        loginAsAdmin();
        super.testSaveNewEntity();
    }

    @Test
    @Transactional
    public void testUpdateAuditedEntity() {
        loginAsAdmin();
        final E createdEntity = getRepository().save(getNewEntity());
        assertThat(createdEntity.getCreatedBy(), equalTo(ADMIN_USERNAME));
        assertThat(createdEntity.getLastModifiedBy(), equalTo(ADMIN_USERNAME));
        createdEntity.setLastModifiedBy(EXISTING_LAST_MODIFIED_BY);
        loginAsSystem();
        final E updatedEntity = getRepository().save(createdEntity);
        getRepository().flush();
        assertThat(updatedEntity.getCreatedDate(), not(updatedEntity.getLastModifiedDate()));
        assertThat(updatedEntity.getCreatedBy(), equalTo(ADMIN_USERNAME));
        assertThat(updatedEntity.getLastModifiedBy(), equalTo(SYSTEM_USERNAME));
    }

    public void loginAsAdmin() {
        login(AuthoritiesConstants.ADMIN, ADMIN_USERNAME);
    }

    public void loginAsSystem() {
        login(AuthoritiesConstants.ADMIN, SYSTEM_USERNAME);
    }

    @Override
    protected boolean checkAdditionalFieldAnnotations(final Object databaseFieldValue, final Field field) {
        if(field.getAnnotation(CreatedBy.class) != null || field.getAnnotation(LastModifiedBy.class) != null) {
            assertThat(databaseFieldValue, equalTo(ADMIN_USERNAME));
            return true;
        }
        if(field.getAnnotation(CreatedDate.class) != null || field.getAnnotation(LastModifiedDate.class) != null) {
            assertThat(((ZonedDateTime)databaseFieldValue).toLocalDate(), equalTo(LocalDate.now()));
            return true;
        }
        return super.checkAdditionalFieldAnnotations(databaseFieldValue, field);
    }

    @Override
    protected List<String> getExcludedComparingFieldsOnFind() {
        final List<String> result = Arrays.asList("createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate");
        result.addAll(super.getExcludedComparingFieldsOnFind());
        return result;
    }

    protected void login(final String role, final String username) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(securityContext);
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        final Authentication auth = new UsernamePasswordAuthenticationToken(new User(username, username, authorities),
            username);
        securityContext.setAuthentication(auth);
    }
}
