package fr.isep.algotourism.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class TripTourView {
    public int id;
    public String name;
    public String distanceTravelled;
    public int nbOfDays;
    public String description;
    public List<TripDayView> days;
}
