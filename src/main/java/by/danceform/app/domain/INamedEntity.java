package by.danceform.app.domain;

/**
 * Created by dimonn12 on 10.10.2016.
 */
public interface INamedEntity extends IEntity<Long> {

    String getName();

    void setName(String name);

}
