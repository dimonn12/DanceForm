package by.danceform.app.dto.config;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Dmitry_Shanko on 10/17/2016.
 */
public class LocationDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 32)
    private String name;

    public LocationDTO() {
    }

    public LocationDTO(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
