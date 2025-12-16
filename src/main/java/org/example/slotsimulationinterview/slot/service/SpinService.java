package org.example.slotsimulationinterview.slot.service;

import org.example.slotsimulationinterview.slot.config.BoardLayoutConfig;
import org.example.slotsimulationinterview.slot.model.SpinResult;

import java.util.List;

public interface SpinService {

    public SpinResult spin(int bet);
}
