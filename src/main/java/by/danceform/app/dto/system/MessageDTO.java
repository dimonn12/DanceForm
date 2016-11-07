package by.danceform.app.dto.system;

import by.danceform.app.dto.AbstractDomainDTO;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;


/**
 * A DTO for the Message entity.
 */
public class MessageDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Email
    @Size(min = 1, max = 128)
    private String fromEmail;

    @NotNull
    @Size(min = 1, max = 128)
    private String subject;

    @Size(max = 3000)
    private String content;

    private ZonedDateTime dateCreated;

    private Boolean sent;

    private ZonedDateTime dateSent;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public ZonedDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(ZonedDateTime dateSent) {
        this.dateSent = dateSent;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
               "id=" + id +
               ", fromEmail='" + fromEmail + "'" +
               ", subject='" + subject + "'" +
               ", dateCreated='" + dateCreated + "'" +
               ", sent='" + sent + "'" +
               ", dateSent='" + dateSent + "'" +
               '}';
    }
}
