package org.example.slotsimulationinterview.slot.util;

import org.example.slotsimulationinterview.slot.config.SlotMachineConfig;
import org.example.slotsimulationinterview.slot.model.Board;
import org.example.slotsimulationinterview.slot.config.BoardLayoutConfig;
import org.example.slotsimulationinterview.slot.model.Payout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class SlotUtil {

    private static int getRandomIndex(int min, int max) {
        return new Random().nextInt(min, max);
    }

    private static int getRandomIndex(int max) {
        return getRandomIndex(0, max);
    }

    private static String formatPaylineEntry (List<Integer> payline) {
        return payline.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    private static int computeWin(int betValue, int matches, Map<Integer, Integer> payout) {
        return (betValue * payout.get(matches)) / 100;
    }

    public static Board getNewBoard(List<String> symbols, BoardLayoutConfig boardLayoutConfig) {
        var boardEntries = boardLayoutConfig.nrOfEntries();
        var result = new ArrayList<String>(boardEntries);
        int index = 0;
        // Generate a new round of symbols for a new board
        while (index < boardEntries) {
            result.add(symbols.get(getRandomIndex(symbols.size())));
            index++;
        }
        return new Board(result, boardLayoutConfig);
    }

    public static List<Payout> computePayout(Board board, int betAmount,  SlotMachineConfig slotMachineConfig) {
        var payouts = new ArrayList<Payout>();
        var normalisedSpinSymbols = board.getNormalizedSpinSymbols();
        var paylines = slotMachineConfig.paylines();
        for (var payline : paylines) {
            int matchingCount = 0;
            for (int i = 0; i < payline.size() - 1; i++) {
                // count matches according to the paylines
                if (normalisedSpinSymbols.get(payline.get(i))
                        .equals(
                                normalisedSpinSymbols.get(payline.get(i + 1))
                        )) {
                    matchingCount++;
                } else {
                    // if no more matches and more than minimal nr of minimal matching symbols
                    // create a new payoutTable and add it to the list of payouts
                    if (matchingCount >= slotMachineConfig
                            .identicalConsecutiveSymbols()) {
                        payouts.add(new Payout(
                                formatPaylineEntry(payline),
                                matchingCount,
                                computeWin(betAmount,
                                        matchingCount,
                                        slotMachineConfig.payoutTable())));
                    }

                    break;
                }
            }
        }

        return payouts;
    }

    public static Map<String, Integer> getPayoutsAsMap(List<Payout> payouts) {
        return payouts.stream()
                .collect(Collectors.toMap(Payout::payline, Payout::matchingCount));
    }

    public static int computeTotalWin(List<Payout> payouts) {
        return  payouts.stream().mapToInt(Payout::win).sum();
    }
}
