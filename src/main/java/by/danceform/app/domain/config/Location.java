package by.danceform.app.domain.config;


import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.INamedEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location extends AbstractEntity<Long> implements INamedEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

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

    @Override
    public String toString() {
        return "Location{" +
               "id=" + id +
               ", name='" + name + "'" +
               '}';
    }
}
