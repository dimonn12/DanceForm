package by.danceform.app.dto.config;

import by.danceform.app.domain.config.enums.DanceCategoryEnum;
import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Dmitry_Shanko on 10/17/2016.
 */
public class DanceCategoryDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 32)
    private String name;

    public DanceCategoryDTO() {
    }

    public DanceCategoryDTO(Long id) {
        super(id);
    }

    public DanceCategoryDTO(DanceCategoryEnum enumValue) {
        this(enumValue.getValue());
        setName(enumValue.name());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
