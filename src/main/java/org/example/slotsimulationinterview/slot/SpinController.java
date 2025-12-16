package org.example.slotsimulationinterview.slot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpinController {

    @GetMapping
    public ResponseEntity<SpinDto> spin() {
        return ResponseEntity.ok().body(new SpinDto());
    }
}