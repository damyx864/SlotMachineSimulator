package org.example.slotsimulationinterview.slot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("slot-machine.board-layout")
public record BoardLayoutConfig(int columns, int rows, List<List<Integer>> numbering) {

    public int nrOfEntries() {
        return columns * rows;
    }
}
