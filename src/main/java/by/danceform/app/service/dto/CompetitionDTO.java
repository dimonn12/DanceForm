package by.danceform.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Competition entity.
 */
public class CompetitionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    @NotNull
    private LocalDate date;

    private Boolean isVisible;

    private String organizer;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }
    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetitionDTO competitionDTO = (CompetitionDTO) o;

        if ( ! Objects.equals(id, competitionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompetitionDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", date='" + date + "'" +
            ", isVisible='" + isVisible + "'" +
            ", organizer='" + organizer + "'" +
            '}';
    }
}
