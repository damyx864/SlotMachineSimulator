package org.example.slotsimulationinterview.slot;

import org.springframework.stereotype.Service;

@Service
public class SpinService {

    public SpinDto getSpin() {
        return  new SpinDto();
    }
}
