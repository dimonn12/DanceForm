package by.danceform.app.domain.config;


import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.INamedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * A DanceClass.
 */
@Entity
@Table(name = "dance_class")
public class DanceClass extends AbstractEntity<Long> implements INamedEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Size(max = 256)
    @Column(name = "description", length = 256)
    private String description;

    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "symbol", length = 2, nullable = false)
    private String symbol;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "transfer_score")
    private Integer transferScore;

    @OneToOne
    @JoinColumn(unique = true, updatable = true, insertable = true)
    private DanceClass nextDanceClass;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getTransferScore() {
        return transferScore;
    }

    public void setTransferScore(Integer transferScore) {
        this.transferScore = transferScore;
    }

    public DanceClass getNextDanceClass() {
        return nextDanceClass;
    }

    public void setNextDanceClass(DanceClass danceClass) {
        this.nextDanceClass = danceClass;
    }

    @Override
    public String toString() {
        return "DanceClass{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", description='" + description + "'" +
               ", symbol='" + symbol + "'" +
               ", weight='" + weight + "'" +
               ", transferScore='" + transferScore + "'" +
               '}';
    }
}
