package org.example.slotsimulationinterview.slot.model;

import org.example.slotsimulationinterview.slot.config.BoardLayoutConfig;

import java.util.ArrayList;
import java.util.List;

public record Board(List<String> spinSymbols, BoardLayoutConfig boardLayoutConfig) {

    // Needed to perform the payline matching
    public List<String> getNormalizedSpinSymbols() {
        var normalizedSpinSymbols = new ArrayList<String>(boardLayoutConfig.nrOfEntries());
        int columns = boardLayoutConfig.columns();
        int rows = boardLayoutConfig.rows();

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                int index = row * columns + col;
                normalizedSpinSymbols.add(spinSymbols.get(index));
            }
        }

        return normalizedSpinSymbols;
    }
}
