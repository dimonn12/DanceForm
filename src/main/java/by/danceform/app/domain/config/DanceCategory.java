package by.danceform.app.domain.config;


import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.INamedEntity;
import by.danceform.app.domain.config.enums.DanceCategoryEnum;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

/**
 * A DanceCategory.
 */
@Entity
@Table(name = "dance_category")
public class DanceCategory extends AbstractEntity<Long> implements INamedEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    public DanceCategory() {
    }

    public DanceCategory(DanceCategoryEnum enumValue) {
        setId(enumValue.getValue());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId(DanceCategoryEnum enumValue) {
        return enumValue.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DanceCategory{" +
               "id=" + id +
               ", name='" + name + "'" +
               '}';
    }
}
