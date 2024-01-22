package fr.isep.algotourism.dto;

import fr.isep.algotourism.database.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TripView {
    public ArrayList<TripTourView> tours;

    public TripView(List<List<Location>> locations) {
        tours = new ArrayList<>();
        var tripDays = locations.stream().map(TripDayView::new).collect(Collectors.toCollection(ArrayList::new));
        TripTourView tour = TripTourView.builder()
                .id(1)
                .name("Tour 1")
                .nbOfDays(locations.size())
                .distanceTravelled(locations.stream().mapToDouble(l -> l.stream().mapToDouble(Location::getDist).sum()).sum() / 1000 + " km")
                .description("Description du tour 1")
                .days(tripDays)
                .build();
        tours.add(tour);
    }
}
