package fr.isep.algotourism.controllers;

import fr.isep.algotourism.algorithms.TripGenerator;
import fr.isep.algotourism.dto.ComputeTripDto;
import fr.isep.algotourism.dto.TripView;
import fr.isep.algotourism.repositories.BuildingsRepository;
import fr.isep.algotourism.repositories.MuseumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IndexController {

    private final MuseumRepository museumRepository;
    private final BuildingsRepository buildingsRepository;

    @PostMapping("/compute-trip")
    public TripView computeTrip(@RequestBody ComputeTripDto body) {
        System.out.println(body);
        var tripGenerator = new TripGenerator(museumRepository, buildingsRepository, body);
        return tripGenerator.generateTrip();
    }

    @RequestMapping(value = "/Home", method = RequestMethod.GET)
    public String homePage() {
        return "forward:/Homepage.html";
    }
}
