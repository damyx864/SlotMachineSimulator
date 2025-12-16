package org.example.slotsimulationinterview;

import org.example.slotsimulationinterview.slot.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        BoardLayoutConfig.class,
        SlotMachineConfig.class})
@SpringBootApplication
public class SlotSimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlotSimulationApplication.class, args);
    }

}
