package org.example.slotsimulationinterview.slot.service;

import org.example.slotsimulationinterview.slot.config.SlotMachineConfig;
import org.example.slotsimulationinterview.slot.model.SpinResult;
import org.springframework.stereotype.Service;

import static org.example.slotsimulationinterview.slot.util.SlotUtil.*;

@Service
public class SpinServiceImpl implements SpinService {

    private final SlotMachineConfig slotMachineConfig;

    public SpinServiceImpl(SlotMachineConfig slotMachineConfig) {
        this.slotMachineConfig = slotMachineConfig;
    }


    @Override
    public SpinResult spin(int bet) {
        // Step 1: Generate a new board
        var board = getNewBoard(slotMachineConfig.symbols(), slotMachineConfig.boardLayout());
        // Step 2: Compute the payoutTable
        var payouts = computePayout(board, bet, slotMachineConfig);
        // Step 3: Build and return the response Dto

        return SpinResult
                .builder()
                .board(board.spinSymbols())
                .paylines(getPayoutsAsMap(payouts))
                .betAmount(bet)
                .totalWin(computeTotalWin(payouts))
                .build();
    }
}
