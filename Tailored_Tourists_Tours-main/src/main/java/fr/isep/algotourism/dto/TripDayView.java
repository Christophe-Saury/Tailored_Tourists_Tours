package fr.isep.algotourism.dto;

import fr.isep.algotourism.database.Location;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
public class TripDayView {
    public String area;
    public String distanceTravelled;
    public int nbOfSites;
    public ArrayList<TripSiteView> sites;

    public TripDayView(List<Location> sites) {
        this.area = "";
        this.distanceTravelled = sites.stream().mapToDouble(Location::getDist).sum() / 1000 + " km";
        this.nbOfSites = sites.size();
        this.sites = sites.stream().map(TripSiteView::new).collect(Collectors.toCollection(ArrayList::new));
    }
}
