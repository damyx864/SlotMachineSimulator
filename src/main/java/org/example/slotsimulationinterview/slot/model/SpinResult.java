package org.example.slotsimulationinterview.slot.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@ToString
@Schema(description = "Result of a spin operation")
public class SpinResult {

    @Schema(description = "The symbols on the board", example = "[\"10\", \"A\", \"Q\"]")
    private List<String> board;

    @Schema(description = "The winning paylines and their payouts")
    private Map<String, Integer> paylines;

    @Schema(description = "The amount bet for this spin", example = "100")
    private Integer betAmount;

    @Schema(description = "The total win from this spin", example = "40")
    private Integer totalWin;
}
