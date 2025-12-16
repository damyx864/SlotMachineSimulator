package org.example.slotsimulationinterview.slot;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.Random;

@Component
@PropertySource(value = "classpath:slot-machine-config.properties")
public class SlotMachine implements Spin {

    @Getter
    @Value("${columns}")
    private Integer columns;

    @Getter
    @Value("${rows}")
    private Integer rows;

    @Getter
    @Value("${symbols}")
    private String[] symbols;

    @Getter
    @Value("${payout-table}")
    private Map<Integer, Integer> payout;

    Random  random = new Random();

    public SlotMachine() {
    }

    @Override
    public String[] spin() {
        var result = new String[columns * rows];
        int index = 0;
        // Generate the symbols
        while (index < columns * rows) {
            result[index] = symbols[getRandomIndex(columns * rows)];
            index++;
        }

        return result;
    }

    private int getRandomIndex(int min, int max) {
        return random.nextInt(min, max);
    }

    private int getRandomIndex(int max) {
        return random.nextInt(0, max);
    }
}
