package org.example.slotsimulationinterview.slot.controller;

import org.example.slotsimulationinterview.slot.config.BoardLayoutConfig;
import org.example.slotsimulationinterview.slot.config.SlotMachineConfig;
import org.example.slotsimulationinterview.slot.model.Board;
import org.example.slotsimulationinterview.slot.util.SlotUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.example.slotsimulationinterview.slot.util.SlotUtil.computePayout;
import static org.example.slotsimulationinterview.slot.util.SlotUtil.computeTotalWin;
import static org.example.slotsimulationinterview.slot.util.SlotUtil.computeWin;
import static org.example.slotsimulationinterview.slot.util.SlotUtil.formatPaylineEntry;
import static org.example.slotsimulationinterview.slot.util.SlotUtil.getNewBoard;
import static org.example.slotsimulationinterview.slot.util.SlotUtil.getPayoutsAsMap;
import static org.example.slotsimulationinterview.slot.util.SlotUtil.getRandomIndex;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SpinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void spin_ShouldReturnSpinResult() throws Exception {

        try (MockedStatic<SlotUtil> utilities = Mockito.mockStatic(SlotUtil.class)) {
            // Mock the randomized slot generation
            utilities.when(() -> getRandomIndex(anyInt()))
                    .thenReturn(2) // J
                    .thenReturn(2) // J
                    .thenReturn(2) // J
                    .thenReturn(3) // Q
                    .thenReturn(4) // K
                    .thenReturn(6) // cat
                    .thenReturn(2) // J
                    .thenReturn(3) // Q
                    .thenReturn(8)// monkey
                    .thenReturn(9)// bird
                    .thenReturn(9)// bird
                    .thenReturn(9)// bird
                    .thenReturn(2) // J
                    .thenReturn(3) // Q
                    .thenReturn(5);// A

            utilities.when(() -> getNewBoard(anyList(), any(BoardLayoutConfig.class)))
                    .thenCallRealMethod();
            utilities.when(() -> computePayout(any(Board.class), anyInt(), any(SlotMachineConfig.class)))
                    .thenCallRealMethod();
            utilities.when(() -> getPayoutsAsMap(anyList()))
                            .thenCallRealMethod();
            utilities.when(() -> computeTotalWin(anyList()))
                            .thenCallRealMethod();
            utilities.when(() -> formatPaylineEntry(anyList()))
                            .thenCallRealMethod();
            utilities.when(() -> computeWin(anyInt(), anyInt(), anyMap()))
                            .thenCallRealMethod();
            int testBetAmount = 100;
            String[] testSymbols = {"J", "J", "J", "Q", "K", "cat", "J", "Q", "monkey", "bird", "bird", "bird", "J", "Q", "A"};
            var testPayout = Map.of("0 4 8 10 12", 3, "0 3 6 9 12", 3);
            mockMvc.perform(get("/api/spin/{bet}", testBetAmount))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.betAmount").value(testBetAmount))
                    .andExpect(jsonPath("$.totalWin").value(40))
                    .andExpect(jsonPath("$.board").value(Matchers.contains(testSymbols)))
                    .andExpect(jsonPath("$.paylines").value(Matchers.equalTo(testPayout)));
        }
    }
}
