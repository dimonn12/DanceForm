package by.danceform.app.dto.system;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A DTO for the LanguageString entity.
 */
public class LanguageStringDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 2, max = 5)
    private String lang;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 1, max = 256)
    private String value;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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
        return "LanguageStringDTO{" +
            "id=" + id +
            ", lang='" + lang + "'" +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
