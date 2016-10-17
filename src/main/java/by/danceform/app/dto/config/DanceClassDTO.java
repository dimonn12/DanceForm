package by.danceform.app.dto.config;

import by.danceform.app.dto.AbstractDomainDTO;
import by.danceform.app.dto.NamedReferenceDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A DTO for the DanceClass entity.
 */
public class DanceClassDTO extends AbstractDomainDTO<Long> {

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

    private NamedReferenceDTO nextDanceClass;

    public DanceClassDTO() {
    }

    public DanceClassDTO(Long id) {
        super(id);
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

    public NamedReferenceDTO getNextDanceClass() {
        return nextDanceClass;
    }

    public void setNextDanceClass(NamedReferenceDTO nextDanceClass) {
        this.nextDanceClass = nextDanceClass;
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
