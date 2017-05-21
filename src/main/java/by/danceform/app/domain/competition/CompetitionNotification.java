package by.danceform.app.domain.competition;


import by.danceform.app.domain.IEntity;
import by.danceform.app.domain.SoftDeletedEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A CompetitionNotification.
 */
@Entity
@Table(name = "competition_notification")
@Where(clause = "deleted <> 1")
@SQLDelete(sql = "UPDATE competitionNotification e set e.deleted=true where e.id=?")
public class CompetitionNotification extends SoftDeletedEntity<Long> implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "competition_id", nullable = false)
    private Long competitionId;

    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "message", length = 2048, nullable = false)
    private String message;

    @Column(name = "is_active")
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
        return "CompetitionNotification{" +
               "id=" + id +
               ", competitionId='" + competitionId + "'" +
               ", message='" + message + "'" +
               ", isActive='" + isActive + "'" +
               '}';
    }
}
