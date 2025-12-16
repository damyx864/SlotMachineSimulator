package org.example.slotsimulationinterview.slot.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class SpinResult {

    private List<String> board;
    private Map<String, Integer> paylines;
    private Integer betAmount;
    private Integer totalWin;
}
