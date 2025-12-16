package org.example.slotsimulationinterview.slot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("slot-machine")
public record SlotMachineConfig(List<String> symbols,
                                int identicalConsecutiveSymbols,
                                List<List<Integer>> paylines,
                                Map<Integer, Integer> payoutTable,
                                BoardLayoutConfig boardLayout) {
}
