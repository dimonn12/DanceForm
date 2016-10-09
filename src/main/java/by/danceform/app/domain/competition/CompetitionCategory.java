package by.danceform.app.domain.competition;

import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.domain.config.DanceClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * A CompetitionCategory.
 */
@Entity
@Table(name = "competition_category")
public class CompetitionCategory extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Size(max = 256)
    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "check_min_age")
    private Boolean checkMinAge;

    @Column(name = "check_max_age")
    private Boolean checkMaxAge;

    @Column(name = "competition_id", nullable = false)
    private Long competitionId;

    @ManyToMany
    @JoinTable(name = "competition_category_dance_class",
               joinColumns = @JoinColumn(name = "competition_categories_id", referencedColumnName = "ID"),
               inverseJoinColumns = @JoinColumn(name = "dance_classes_id", referencedColumnName = "ID"))
    private Set<DanceClass> danceClasses = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "competition_category_age_category",
               joinColumns = @JoinColumn(name = "competition_categories_id", referencedColumnName = "ID"),
               inverseJoinColumns = @JoinColumn(name = "age_categories_id", referencedColumnName = "ID"))
    private Set<AgeCategory> ageCategories = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isCheckMinAge() {
        return checkMinAge;
    }

    public void setCheckMinAge(Boolean checkMinAge) {
        this.checkMinAge = checkMinAge;
    }

    public Boolean isCheckMaxAge() {
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

    public Set<DanceClass> getDanceClasses() {
        return danceClasses;
    }

    public void setDanceClasses(Set<DanceClass> danceClas) {
        this.danceClasses = danceClas;
    }

    public Set<AgeCategory> getAgeCategories() {
        return ageCategories;
    }

    public void setAgeCategories(Set<AgeCategory> ageCategories) {
        this.ageCategories = ageCategories;
    }

    @Override
    public String toString() {
        return "CompetitionCategory{" +
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
