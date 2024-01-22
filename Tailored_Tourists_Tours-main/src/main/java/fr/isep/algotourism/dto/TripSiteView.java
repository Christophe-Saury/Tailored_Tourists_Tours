package fr.isep.algotourism.dto;

import fr.isep.algotourism.database.Buildings;
import fr.isep.algotourism.database.Location;
import fr.isep.algotourism.database.Museum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TripSiteView {
    public long id;
    public String name;
    public String localisation;
    public String historicalPeriod;
    public String description;
    public String type;
    public double latitude;
    public double longitude;
    public double x;
    public double y;
    public double dist;

    public TripSiteView(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.localisation = location.getAdresse();
        this.historicalPeriod = location.getPeriod();
        this.description = location.getDescription();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.x = location.getPos_x();
        this.y = location.getPos_y();
        this.dist = location.getDist();

        if (location instanceof Museum) {
            this.type = "Museum";
        } else if (location instanceof Buildings) {
            this.type = "Buildings";
        } else {
            this.type = "Unknown";
        }
    }
}
