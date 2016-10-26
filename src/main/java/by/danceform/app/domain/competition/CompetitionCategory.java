package by.danceform.app.domain.competition;

import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.INamedEntity;
import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.domain.config.DanceCategory;
import by.danceform.app.domain.config.DanceClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A CompetitionCategory.
 */
@Entity
@Table(name = "competition_category")
public class CompetitionCategory extends AbstractEntity<Long> implements INamedEntity {

    private static final long serialVersionUID = 3728310148232456453L;

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

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "check_min_age")
    private Boolean checkMinAge;

    @Column(name = "check_max_age")
    private Boolean checkMaxAge;

    @Column(name = "competition_id", nullable = false)
    private Long competitionId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "dance_category_id")
    private DanceCategory danceCategory;

    @ManyToOne
    @JoinColumn(name = "max_dance_class_id")
    private DanceClass maxDanceClass;

    @ManyToMany
    @JoinTable(name = "competition_category_age_category",
               joinColumns = @JoinColumn(name = "competition_categories_id", referencedColumnName = "ID"),
               inverseJoinColumns = @JoinColumn(name = "age_categories_id", referencedColumnName = "ID"))
    private Set<AgeCategory> ageCategories = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public DanceClass getMaxDanceClass() {
        return maxDanceClass;
    }

    public void setMaxDanceClass(DanceClass maxDanceClass) {
        this.maxDanceClass = maxDanceClass;
    }

    public Set<AgeCategory> getAgeCategories() {
        return ageCategories;
    }

    public void setAgeCategories(Set<AgeCategory> ageCategories) {
        this.ageCategories = ageCategories;
    }

    public DanceCategory getDanceCategory() {
        return danceCategory;
    }

    public void setDanceCategory(DanceCategory danceCategory) {
        this.danceCategory = danceCategory;
    }

    @Override
    public String toString() {
        return "CompetitionCategory{" +
               "id=" +
               id +
               ", name='" +
               name +
               "'" +
               ", description='" +
               description +
               "'" +
               ", active='" +
               active +
               "'" +
               ", checkMinAge='" +
               checkMinAge +
               "'" +
               ", checkMaxAge='" +
               checkMaxAge +
               "'" +
               ", competitionId='" +
               competitionId +
               "'" +
               '}';
    }
}
