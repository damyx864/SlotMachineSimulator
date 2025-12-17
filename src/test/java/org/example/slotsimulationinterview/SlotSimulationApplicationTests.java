package org.example.slotsimulationinterview;

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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.example.slotsimulationinterview.slot.util.SlotUtil.computePayout;
import static org.example.slotsimulationinterview.slot.util.SlotUtil.getNewBoard;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SlotSimulationApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SlotMachineConfig  slotMachineConfig;

    @Test
    void contextLoads() {
    }

    @Test
    void testInterviewCase() throws Exception {
        var testSymbols = List.of("J","J","J","Q","K","cat","J","Q","monkey","bird","bird","bird","J","Q","A");
        var testSymbols2 = List.of("J","cat","bird", "J", "J","bird","J", "Q", "J", "Q", "monkey", "K", "bird", "A");
        var testBoardLayoutConfig = new BoardLayoutConfig(5, 3,
                List.of(List.of(0,3,6,9,12),
                List.of(1,4,7,10,13),
                List.of(2,5,8,11,14)));
        var mockedBoard = new Board(testSymbols, testBoardLayoutConfig);

        try (MockedStatic<SlotUtil> utilities = Mockito.mockStatic(SlotUtil.class)) {
            utilities.when(() -> getNewBoard(anyList(), any(BoardLayoutConfig.class)))
                    .thenReturn(mockedBoard);
            utilities.when(() -> computePayout(mockedBoard, 100, slotMachineConfig))
                            .thenCallRealMethod();
            mockMvc.perform(get("/100"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.board").value(Matchers.containsInAnyOrder("J","J","J","Q","K","cat","J","Q","monkey","bird","bird","bird","J","Q","A")))
                    .andExpect(jsonPath("$.paylines").value(Map.of("0 3 6 9 12", 3, "0 4 8 10 12", 3)))
                    .andExpect(jsonPath("$.betAmount").value(100))
                    .andExpect(jsonPath("$.totalWin").value(40));
        }
    }

}
