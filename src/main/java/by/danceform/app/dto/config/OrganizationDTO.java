package by.danceform.app.dto.config;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Dmitry_Shanko on 10/17/2016.
 */
public class OrganizationDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 64)
    private String name;

    private boolean visible;

    public OrganizationDTO() {
    }

    public OrganizationDTO(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
