package by.danceform.app.domain.system;


import by.danceform.app.domain.AbstractEntity;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Email
    @Size(min = 1, max = 128)
    @Column(name = "from_email", length = 128, nullable = false)
    private String fromEmail;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "subject", length = 128, nullable = false)
    private String subject;

    @Size(max = 3000)
    @Column(name = "content", length = 3000)
    private String content;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "date_sent")
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

    public Boolean isSent() {
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
        return "Message{" +
               "id=" + id +
               ", fromEmail='" + fromEmail + "'" +
               ", subject='" + subject + "'" +
               ", dateCreated='" + dateCreated + "'" +
               ", sent='" + sent + "'" +
               ", dateSent='" + dateSent + "'" +
               '}';
    }
}
