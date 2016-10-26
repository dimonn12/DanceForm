package by.danceform.app.dto.competition;

import by.danceform.app.dto.AbstractDomainDTO;
import by.danceform.app.dto.NamedReferenceDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


/**
 * A DTO for the CompetitionCategory entity.
 */
public class CompetitionCategoryDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 64)
    private String name;

    @Size(max = 256)
    private String description;

    private LocalDate date;

    private Boolean active;

    private Boolean checkMinAge;

    private Boolean checkMaxAge;

    private Long competitionId;

    private NamedReferenceDTO danceCategory;

    private NamedReferenceDTO maxDanceClass;

    private Set<NamedReferenceDTO> ageCategories = new HashSet<>();

    public CompetitionCategoryDTO() {

    }

    public CompetitionCategoryDTO(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getCheckMinAge() {
        return checkMinAge;
    }

    public void setCheckMinAge(Boolean checkMinAge) {
        this.checkMinAge = checkMinAge;
    }

    public Boolean getCheckMaxAge() {
        return checkMaxAge;
    }

    public void setCheckMaxAge(Boolean checkMaxAge) {
        this.checkMaxAge = checkMaxAge;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public NamedReferenceDTO getDanceCategory() {
        return danceCategory;
    }

    public void setDanceCategory(NamedReferenceDTO danceCategory) {
        this.danceCategory = danceCategory;
    }

    public NamedReferenceDTO getMaxDanceClass() {
        return maxDanceClass;
    }

    public void setMaxDanceClass(NamedReferenceDTO maxDanceClass) {
        this.maxDanceClass = maxDanceClass;
    }

    public Set<NamedReferenceDTO> getAgeCategories() {
        return ageCategories;
    }

    public void setAgeCategories(Set<NamedReferenceDTO> ageCategories) {
        this.ageCategories = ageCategories;
    }

    @Override
    public String toString() {
        return "CompetitionCategoryDTO{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", description='" + description + "'" +
               ", active='" + active + "'" +
               ", checkMinAge='" + checkMinAge + "'" +
               ", checkMaxAge='" + checkMaxAge + "'" +
               ", competitionId='" + competitionId + "'" +
               '}';
    }
}
