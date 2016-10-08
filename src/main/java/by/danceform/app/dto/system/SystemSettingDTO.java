package by.danceform.app.dto.system;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by dimonn12 on 08.10.2016.
 */
public class SystemSettingDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    @NotNull
    @Size(min = 1, max = 256)
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SystemSettingDTO{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", value='" + value + "'" +
               '}';
    }
}
