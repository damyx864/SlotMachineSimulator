package org.example.slotsimulationinterview.slot.util;

import org.example.slotsimulationinterview.slot.config.BoardLayoutConfig;
import org.example.slotsimulationinterview.slot.config.SlotMachineConfig;
import org.example.slotsimulationinterview.slot.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.example.slotsimulationinterview.slot.util.SlotUtil.getRandomIndex;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SlotUtilTest {

    private List<String> testSymbols;
    private List<List<Integer>> testPaylines;
    private Map<Integer, Integer> testPayoutTable;
    private BoardLayoutConfig testBoardLayoutConfig;
    private Board testBoard;
    private SlotMachineConfig testSlotMachineConfig;

    @BeforeEach
    void setUp() {

            testPayoutTable = Map.of(3, 20, 4, 200, 5, 1000);
            testPaylines = List.of(
                    List.of(0, 3, 6, 9, 12),
                    List.of(1, 4, 7, 10, 13),
                    List.of(2, 5, 8, 11, 14),
                    List.of(0, 4, 8, 10, 12),
                    List.of(2, 4, 6, 10, 14));
            testBoardLayoutConfig = new BoardLayoutConfig(
                    5,
                    3,
                    List.of(
                            List.of(0, 3, 6, 9, 12),
                            List.of(1, 4, 7, 10, 13),
                            List.of(2, 5, 8, 11, 14)));

            testSymbols = List.of("J", "J", "J", "Q", "K", "cat", "J", "Q", "monkey", "bird", "bird", "bird", "J", "Q", "A");
            testBoard = new Board(testSymbols, testBoardLayoutConfig);
            testSlotMachineConfig = new SlotMachineConfig(
                    testSymbols,
                    3,
                    testPaylines,
                    testPayoutTable,
                    testBoardLayoutConfig);
    }

    @Test
    void getNewBoard() {
        try (MockedStatic<SlotUtil> utilities = Mockito.mockStatic(SlotUtil.class)) {
            // Mock the randomized slot generation
            utilities.when(() -> getRandomIndex(15))
                    .thenReturn(2) // J
                    .thenReturn(2) // J
                    .thenReturn(2) // J
                    .thenReturn(3) // Q
                    .thenReturn(4) // K
                    .thenReturn(6) // cat
                    .thenReturn(2) // J
                    .thenReturn(3) // Q
                    .thenReturn(13)// monkey
                    .thenReturn(14)// bird
                    .thenReturn(14)// bird
                    .thenReturn(14)// bird
                    .thenReturn(2) // J
                    .thenReturn(4) // J
                    .thenReturn(5);// J

            utilities.when(() -> SlotUtil.getNewBoard(testSymbols, testBoardLayoutConfig))
                    .thenCallRealMethod();

            var board = SlotUtil.getNewBoard(testSymbols, testBoardLayoutConfig);
            assertEquals(testBoard, board);
        }
    }

    @Test
    void computePayout() {
        var testPayout = Map.of("0 3 6 9 12", 3, "0 4 8 10 12", 3);
        try (MockedStatic<SlotUtil> utilities = Mockito.mockStatic(SlotUtil.class)) {

            utilities.when(() -> SlotUtil.computePayout(testBoard, 100, testSlotMachineConfig))
                    .thenCallRealMethod();
            utilities.when(() -> SlotUtil.formatPaylineEntry(List.of(0, 3, 6, 9, 12)))
                    .thenCallRealMethod();
            utilities.when(() -> SlotUtil.formatPaylineEntry(List.of(0, 4, 8, 10, 12)))
                    .thenCallRealMethod();
            utilities.when(() -> SlotUtil.computeWin(100, 3, testSlotMachineConfig.payoutTable()))
                    .thenCallRealMethod();

            var payout = SlotUtil.computePayout(testBoard, 100, testSlotMachineConfig);
            utilities.when(() -> SlotUtil.getPayoutsAsMap(payout))
                    .thenCallRealMethod();
            assertEquals(testPayout, SlotUtil.getPayoutsAsMap(payout));
        }
    }

    @Test
    void getPayoutsAsMap() {
    }

    @Test
    void computeTotalWin() {
    }
}