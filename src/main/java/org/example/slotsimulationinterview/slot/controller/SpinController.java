package org.example.slotsimulationinterview.slot.controller;

import org.example.slotsimulationinterview.slot.model.SpinResult;
import org.example.slotsimulationinterview.slot.service.SpinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/spin")
public class SpinController {

    private final SpinService spinService;

    public SpinController(SpinService spinService) {
        this.spinService = spinService;
    }

    @GetMapping(path = "/{bet}", produces = "application/json")
    public ResponseEntity<SpinResult> spin(@PathVariable int bet) {
        return ResponseEntity.ok()
                .body(spinService.spin(bet));
    }
}