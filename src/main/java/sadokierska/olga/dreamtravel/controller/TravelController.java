package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sadokierska.olga.dreamtravel.model.Travel;
import sadokierska.olga.dreamtravel.repository.TravelRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/travels")
@CrossOrigin
public class TravelController {

    @Autowired
    private TravelRepository travelRepository;

    @GetMapping(path="")
    public ResponseEntity<List<Travel>> getAllTravels() {
        List<Travel> allTravels = travelRepository.findAll();
        return ResponseEntity.ok(allTravels);
    }
    @PostMapping(path="")
    public ResponseEntity<Travel> addNewTravel(@RequestBody Travel travel) {
        Travel newTravel = travelRepository.save(travel);
        return ResponseEntity.ok(newTravel);
    }
}
