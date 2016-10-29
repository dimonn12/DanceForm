package by.danceform.app.web.rest.errors;

import by.danceform.app.dto.AbstractDomainDTO;
import by.danceform.app.exception.ApplicationExceptionCodes;

/**
 * Created by dimonn12 on 29.10.2016.
 */
public class ApplicationExceptionDTO extends AbstractDomainDTO<Integer> {

    private String value;

    public ApplicationExceptionDTO() {
    }

    public ApplicationExceptionDTO(Integer id) {
        super(id);
    }

    public ApplicationExceptionDTO(ApplicationExceptionCodes code) {
        this(code.getId(), code.getValue());
    }

    public ApplicationExceptionDTO(Integer id, String value) {
        super(id);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
