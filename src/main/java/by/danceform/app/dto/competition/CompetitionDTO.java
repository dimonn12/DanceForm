package by.danceform.app.dto.competition;

import by.danceform.app.domain.competition.Competition;
import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


/**
 * A DTO for the Competition entity.
 */
public class CompetitionDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    @NotNull
    private LocalDate date;

    private Boolean visible;

    private String organizer;

    @NotNull
    @Size(min = 1, max = 512)
    private String location;

    public CompetitionDTO() {
    }

    public CompetitionDTO(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CompetitionDTO{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", date='" + date + "'" +
               ", visible='" + visible + "'" +
               ", organizer='" + organizer + "'" +
               ", location='" + location + "'" +
               '}';
    }
}
