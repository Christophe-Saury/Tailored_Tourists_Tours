package fr.isep.algotourism.algorithms;

import fr.isep.algotourism.database.Buildings;
import fr.isep.algotourism.database.Location;
import fr.isep.algotourism.database.Museum;
import fr.isep.algotourism.dto.ComputeTripDto;
import fr.isep.algotourism.dto.ExcludeType;
import fr.isep.algotourism.dto.TripView;
import fr.isep.algotourism.repositories.BuildingsRepository;
import fr.isep.algotourism.repositories.MuseumRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TripGenerator {

    private final MuseumRepository museumRepository;
    private final BuildingsRepository buildingsRepository;
    private final ComputeTripDto trip;
    private final List<List<Location>> visitedLocations = new ArrayList<>();
    private final List<Integer> visitedIds = new ArrayList<>();
    private String centuries;


    public TripView generateTrip() {
        if (trip.getCenturies().size() > 0)
            centuries = trip.getCenturies().stream().map(String::valueOf).collect(Collectors.joining("|"));
        else
            centuries = ".*";
        System.out.println(centuries);

        Direction direction = Direction.NORTH;

        // nbDays = n
        // destinationCount = m
        // O(n*m)

        //
        for (int i = 0; i < trip.getNbDays(); i++) {
            visitedLocations.add(new ArrayList<>());

            // On va dans une direction jusqu'à la moitié
            for (int j = 0; j < trip.getDestinationCount() / 2; j++) {
                Location location;
                if (j == 0) {
                    location = getNearestLocationFromDirection(trip.getPosX(), trip.getPosY(), direction);
                } else {
                    var previousLocation = visitedLocations.get(i).get(j - 1);
                    location = getNearestLocationFromDirection((int) previousLocation.getPos_x(), (int) previousLocation.getPos_y(), direction);
                }

                visitedLocations.get(i).add(location);
                visitedIds.add(location.getId());
            }

            // Retour au point de départ avec un nearest insertion
            for (int j = trip.getDestinationCount() / 2; j < trip.getDestinationCount(); j++) {
                var previousLocation = visitedLocations.get(i).get(j - 1);
                var location = getNearestInsertion(previousLocation);

                // Si rien n'est trouvé, on change de direction
                if (location == null) {
                    location = getNearestLocationFromDirection((int) previousLocation.getPos_x(), (int) previousLocation.getPos_y(), direction.next());
                }

                visitedLocations.get(i).add(location);
                visitedIds.add(location.getId());
                System.out.println(visitedIds.get(i));
            }

            // Pour le tour d'après on change de direction
            direction = direction.next();
        }


        System.out.println("Trip computed");
        return new TripView(visitedLocations);
    }


    private Location getNearestLocationFromDirection(int ox, int oy, Direction direction) {
        final int DIRECTION_RANGE = 100_000; // 10km
        List<Integer> excludedIds;
        if (visitedIds.isEmpty())
            excludedIds = List.of(-1);
        else
            excludedIds = visitedIds;
        var buildings = switch (direction) {
            case NORTH -> buildingsRepository.findNearestNorth(ox, oy, DIRECTION_RANGE, centuries, excludedIds);
            case EAST -> buildingsRepository.findNearestEast(ox, oy, DIRECTION_RANGE, centuries, excludedIds);
            case SOUTH -> buildingsRepository.findNearestSouth(ox, oy, DIRECTION_RANGE, centuries, excludedIds);
            case WEST -> buildingsRepository.findNearestWest(ox, oy, DIRECTION_RANGE, centuries, excludedIds);
        };
        var museum = switch (direction) {
            case NORTH ->
                    museumRepository.findNearestNorth(trip.getPosX(), trip.getPosY(), DIRECTION_RANGE, excludedIds);
            case EAST -> museumRepository.findNearestEast(trip.getPosX(), trip.getPosY(), DIRECTION_RANGE, excludedIds);
            case SOUTH ->
                    museumRepository.findNearestSouth(trip.getPosX(), trip.getPosY(), DIRECTION_RANGE, excludedIds);
            case WEST -> museumRepository.findNearestWest(trip.getPosX(), trip.getPosY(), DIRECTION_RANGE, excludedIds);
        };
        System.out.println("Buildings: " + buildings + " at " + direction);
        System.out.println("Museum: " + museum + " at " + direction);
        return chooseBetweenLocations(buildings, museum);
    }

    private Location getNearestInsertion(Location location) {
        var buildings = buildingsRepository.findNearestInsertion(location.getPos_x(), location.getPos_y(), trip.getPosX(), trip.getPosY(), visitedIds, centuries);
        var museum = museumRepository.findNearestInsertion(location.getPos_x(), location.getPos_y(), trip.getPosX(), trip.getPosY(), visitedIds);
        System.out.println("Nearest Buildings: " + buildings);
        System.out.println("Nearest Museum: " + museum);
        return chooseBetweenLocations(buildings, museum);
    }

    private Location chooseBetweenLocations(Buildings buildings, Museum museum) {
        if (buildings == null || trip.getExcludeType() == ExcludeType.BUILDING) {
            return museum;
        }
        if (museum == null || trip.getExcludeType() == ExcludeType.MUSEUM) {
            return buildings;
        }
        return buildings.getDist() < museum.getDist() ? buildings : museum;
    }

    private enum Direction implements Iterator<Direction> {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Direction next() {
            return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
        }

    }
}
