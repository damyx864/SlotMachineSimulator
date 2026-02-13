package org.example.slotsimulationinterview.slot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.slotsimulationinterview.slot.service.SpinService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spin")
@Tag(name = "Spin", description = "The Spin API")
public class SpinController {

    private final SpinService spinService;

    public SpinController(SpinService spinService) {
        this.spinService = spinService;
    }

    @GetMapping(path = "/{bet}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Perform a spin", description = "Executes a slot machine spin with the given bet amount")
    public ResponseEntity<?> spin(
            @Parameter(description = "The bet amount", example = "100")
            @PathVariable int bet) {
        return ResponseEntity.ok()
                .body(spinService.spin(bet));
    }
}