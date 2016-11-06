package by.danceform.app.validator;

import by.danceform.app.dto.AbstractDomainDTO;

import java.io.Serializable;

/**
 * Created by dimonn12 on 29.10.2016.
 */
public abstract class AbstractValidator<ID extends Serializable, D extends AbstractDomainDTO<ID>> {

    public abstract void validate(D dto);
}
