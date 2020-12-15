package main.java.com.scheduling.cpu.base;

import java.math.BigDecimal;

public interface SchedulingManager {

    void computeWaitingTime();

    void computeTurnAroundTime();

//    BigDecimal computeAverageWaitingTime();
//
//    BigDecimal computeAverageTurnAroundTime();

    default void computeSchedulingTimes() {
        this.computeWaitingTime();
        this.computeTurnAroundTime();
    }



}
