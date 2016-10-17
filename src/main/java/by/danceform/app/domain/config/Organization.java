package by.danceform.app.domain.config;


import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.INamedEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
public class Organization extends AbstractEntity<Long> implements INamedEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "visible")
    private Boolean visible;

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

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Organization{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", visible='" + visible + "'" +
               '}';
    }
}
