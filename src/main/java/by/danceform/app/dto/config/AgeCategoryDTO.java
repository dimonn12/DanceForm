package by.danceform.app.dto.config;

import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


/**
 * A DTO for the AgeCategory entity.
 */
public class AgeCategoryDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 64)
    private String name;

    @NotNull
    @Min(value = 1)
    private Integer minAge;

    @NotNull
    @Min(value = 1)
    private Integer maxAge;

    public AgeCategoryDTO() {
    }

    public AgeCategoryDTO(Long id) {
        super(id);
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
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        AgeCategoryDTO ageCategoryDTO = (AgeCategoryDTO)o;

        if(!Objects.equals(id, ageCategoryDTO.id)) {
            return false;
        }

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
