package by.danceform.app.domain.system;


import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.INamedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A SystemSetting.
 */
@Entity
@Table(name = "system_setting")
public class SystemSetting extends AbstractEntity<Long> implements INamedEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name", length = 256, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "value", length = 256, nullable = false)
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SystemSetting{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", value='" + value + "'" +
               '}';
    }
}
