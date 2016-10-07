package by.danceform.app.domain.config;


import by.danceform.app.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A AgeCategory.
 */
@Entity
@Table(name = "age_category")
public class AgeCategory extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @NotNull
    @Min(value = 1)
    @Column(name = "min_age", nullable = false)
    private Integer minAge;

    @NotNull
    @Min(value = 1)
    @Column(name = "max_age", nullable = false)
    private Integer maxAge;

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

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String toString() {
        return "AgeCategory{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", minAge='" + minAge + "'" +
               ", maxAge='" + maxAge + "'" +
               '}';
    }
}
