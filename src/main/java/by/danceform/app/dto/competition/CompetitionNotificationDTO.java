package by.danceform.app.dto.competition;

import by.danceform.app.dto.AbstractDomainDTO;
import by.danceform.app.dto.NamedReferenceDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A DTO for the CompetitionNotification entity.
 */
public class CompetitionNotificationDTO extends AbstractDomainDTO<Long> {

    @NotNull
    private NamedReferenceDTO competition;

    @NotNull
    @Size(min = 1, max = 2048)
    private String message;

    private Boolean active;

    public NamedReferenceDTO getCompetition() {
        return competition;
    }

    public void setCompetition(NamedReferenceDTO competition) {
        this.competition = competition;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isActive() {
        return active;
    }

    public void setIsActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "CompetitionNotificationDTO{" +
               "id=" + id +
               ", competition='" + competition + "'" +
               ", message='" + message + "'" +
               ", active='" + active + "'" +
               '}';
    }
}
