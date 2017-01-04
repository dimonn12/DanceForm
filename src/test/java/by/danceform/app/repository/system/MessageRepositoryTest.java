package by.danceform.app.repository.system;

import by.danceform.app.domain.system.Message;
import by.danceform.app.repository.AbstractRepositoryTest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Dmitry_Shanko on 1/3/2017.
 */
public class MessageRepositoryTest extends AbstractRepositoryTest<MessageRepository, Message, Long> {

    private static final String EXISTING_FROM_EMAIL = "dimonn12@hotmail.com";
    private static final String EXISTING_SUBJECT = "Test EMAIL";
    private static final String EXISTING_CONTENT = "Some content";
    private static final ZonedDateTime EXISTING_DATE_CREATED = ZonedDateTime.of(LocalDate.parse("2016-12-31"),
        LocalTime.MIN,
        ZoneId.systemDefault());
    private static final ZonedDateTime EXISTING_DATE_SENT = ZonedDateTime.of(LocalDate.parse("2017-01-01"),
        LocalTime.MIN,
        ZoneId.systemDefault());
    private static final boolean EXISTING_SENT = true;

    private static final Integer NOT_SENT_AMOUNT = 1;

    private final Message existing = new Message();

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setFromEmail(EXISTING_FROM_EMAIL);
        existing.setSubject(EXISTING_SUBJECT);
        existing.setContent(EXISTING_CONTENT);
        existing.setDateCreated(EXISTING_DATE_CREATED);
        existing.setDateSent(EXISTING_DATE_SENT);
        existing.setSent(EXISTING_SENT);
    }

    @Test
    public void testFindNotSent() {
        final List<Message> notSent = getRepository().findNotSent();
        assertThat(notSent.size(), is(NOT_SENT_AMOUNT));
        for(Message message : notSent) {
            assertThat(message.isSent(), is(false));
        }
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected Message getExistingEntity() {
        return existing;
    }

    @Override
    protected Message getNewEntity() {
        final Message message = new Message();
        message.setFromEmail("test@ya.ru");
        message.setSubject("test subject");
        return message;
    }
}
