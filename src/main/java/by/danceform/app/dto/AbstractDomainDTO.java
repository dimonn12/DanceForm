package by.danceform.app.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by dimonn12 on 07.10.2016.
 */
public abstract class AbstractDomainDTO<I extends Serializable> implements Serializable {

    private static final long serialVersionUID = 4452115445018826718L;

    protected I id;

    public AbstractDomainDTO() {
        super();
    }

    public AbstractDomainDTO(I id) {
        super();
        this.id = id;
    }

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractDomainDTO<I> domainDTO = (AbstractDomainDTO<I>)o;

        if(!Objects.equals(id, domainDTO.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
