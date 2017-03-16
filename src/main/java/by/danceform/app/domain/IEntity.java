package by.danceform.app.domain;

import java.io.Serializable;

/**
 * Created by dimonn12 on 07.10.2016.
 */
public interface IEntity<ID extends Serializable> extends Serializable {

    /**
     * Gets a unique identifier of the entity.
     *
     * @return identifier
     */
    ID getId();

}
