package by.danceform.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the AgeCategory entity.
 */
public class AgeCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 64)
    private String name;

    @NotNull
    @Min(value = 1)
    private Integer minAge;

    @NotNull
    @Min(value = 1)
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgeCategoryDTO ageCategoryDTO = (AgeCategoryDTO) o;

        if ( ! Objects.equals(id, ageCategoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AgeCategoryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", minAge='" + minAge + "'" +
            ", maxAge='" + maxAge + "'" +
            '}';
    }
}
