package by.danceform.app.domain;


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
import java.io.Serializable;
import java.util.Objects;

/**
 * A DanceClass.
 */
@Entity
@Table(name = "dance_class")
public class DanceClass implements Serializable {

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
    @JoinColumn(unique = true)
    private DanceClass danceClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DanceClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DanceClass description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public DanceClass symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getWeight() {
        return weight;
    }

    public DanceClass weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getTransferScore() {
        return transferScore;
    }

    public DanceClass transferScore(Integer transferScore) {
        this.transferScore = transferScore;
        return this;
    }

    public void setTransferScore(Integer transferScore) {
        this.transferScore = transferScore;
    }

    public DanceClass getDanceClass() {
        return danceClass;
    }

    public DanceClass danceClass(DanceClass danceClass) {
        this.danceClass = danceClass;
        return this;
    }

    public void setDanceClass(DanceClass danceClass) {
        this.danceClass = danceClass;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        DanceClass danceClass = (DanceClass)o;
        if(danceClass.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, danceClass.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
