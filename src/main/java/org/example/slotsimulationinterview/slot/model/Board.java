package org.example.slotsimulationinterview.slot.model;

import org.example.slotsimulationinterview.slot.config.BoardLayoutConfig;

import java.util.ArrayList;
import java.util.List;

public record Board(List<String> spinSymbols, BoardLayoutConfig boardLayoutConfig) {

    // Needed to perform the payline matching
    public List<String> getNormalizedSpinSymbols() {
        var normalizedSpinSymbols = new ArrayList<String>(boardLayoutConfig.nrOfEntries());
        for (int i = 0; i < boardLayoutConfig.columns(); i++) {
            for (int j = 0; j < boardLayoutConfig.rows(); j++) {
                normalizedSpinSymbols.add(spinSymbols.get((i + j)));
            }
        }

        return normalizedSpinSymbols;
    }
}
