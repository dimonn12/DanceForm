package by.danceform.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the DanceClass entity.
 */
public class DanceClassDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    private String name;

    @Size(max = 256)
    private String description;

    @NotNull
    @Size(min = 1, max = 2)
    private String symbol;

    @NotNull
    private Integer weight;

    private Integer transferScore;


    private Long danceClassId;
    

    private String danceClassName;

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

    public Long getDanceClassId() {
        return danceClassId;
    }

    public void setDanceClassId(Long danceClassId) {
        this.danceClassId = danceClassId;
    }


    public String getDanceClassName() {
        return danceClassName;
    }

    public void setDanceClassName(String danceClassName) {
        this.danceClassName = danceClassName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DanceClassDTO danceClassDTO = (DanceClassDTO) o;

        if ( ! Objects.equals(id, danceClassDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DanceClassDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", symbol='" + symbol + "'" +
            ", weight='" + weight + "'" +
            ", transferScore='" + transferScore + "'" +
            '}';
    }
}
