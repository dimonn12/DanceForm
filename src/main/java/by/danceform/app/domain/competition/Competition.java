package by.danceform.app.domain.competition;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.INamedEntity;

/**
 * A Competition.
 */
@Entity
@Table(name = "competition")
public class Competition extends AbstractEntity<Long> implements INamedEntity {

    private static final long serialVersionUID = -146024457330746645L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "is_visible")
    private boolean visible;

    @Column(name = "organizer")
    private String organizer;

    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "location", length = 512, nullable = false)
    private String location;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Competition{" + "id=" + id + ", name='" + name + "'" + ", date='" + date + "'" + ", visible='" + visible
                + "'" + ", organizer='" + organizer + "'" + ", location='" + location + "'" + '}';
    }
}
