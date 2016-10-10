package by.danceform.app.dto;

/**
 * Created by dimonn12 on 10.10.2016.
 */
public class NamedReferenceDTO extends AbstractDomainDTO<Long>  {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
