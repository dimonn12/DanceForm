package by.danceform.app.dto.config;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Dmitry_Shanko on 10/17/2016.
 */
public class TrainerDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 32)
    private String name;

    @NotNull
    @Size(min = 1, max = 32)
    private String surname;

    private boolean visible;

    public TrainerDTO() {
    }

    public TrainerDTO(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
