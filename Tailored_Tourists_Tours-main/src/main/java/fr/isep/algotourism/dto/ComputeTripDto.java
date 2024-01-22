package fr.isep.algotourism.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ComputeTripDto {
    private int posX;
    private int posY;

    private int nbDays;
    private int destinationCount;

    private List<Integer> centuries;

    private ExcludeType excludeType;
}
