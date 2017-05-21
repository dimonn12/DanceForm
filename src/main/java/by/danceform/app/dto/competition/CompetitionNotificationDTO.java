package by.danceform.app.dto.competition;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A DTO for the CompetitionNotification entity.
 */
public class CompetitionNotificationDTO extends AbstractDomainDTO<Long> {

    private Long id;

    @NotNull
    private Long competitionId;

    @NotNull
    @Size(min = 1, max = 2048)
    private String message;

    private Boolean isActive;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "CompetitionNotificationDTO{" +
               "id=" + id +
               ", competitionId='" + competitionId + "'" +
               ", message='" + message + "'" +
               ", isActive='" + isActive + "'" +
               '}';
    }
}
