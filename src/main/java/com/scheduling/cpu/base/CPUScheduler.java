package main.java.com.scheduling.cpu.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

import main.java.com.scheduling.cpu.process.compare.SimpleProcessArrivalTimeComparator;
import main.java.com.scheduling.exception.WaitingTimeNotYetComputedException;
import main.java.com.scheduling.cpu.process.SimpleProcess;

public abstract class CPUScheduler implements SchedulingManager {

    protected SimpleProcess[] simpleProcesses;

    protected BigInteger numberOfProcesses;

    protected int[] waitingTimes;

    protected int[] turnAroundTimes;

    protected int[] remainingTimes;

    protected int[] completionTimes;

    protected CPUScheduler(int numberOfProcesses ) {
        this.numberOfProcesses = BigInteger.valueOf( numberOfProcesses );
        waitingTimes = new int[numberOfProcesses];
        turnAroundTimes = new int[numberOfProcesses];
        completionTimes = new int[numberOfProcesses];
    }

    protected void setRemainingTimes( SimpleProcess[] simpleProcesses) {
        this.remainingTimes =  Arrays.stream(simpleProcesses).mapToInt( SimpleProcess::getBurstTime ).toArray();
    }

    protected void setProcesses( SimpleProcess[] simpleProcesses) {
        this.simpleProcesses = simpleProcesses;
    }

    protected void checkIfWaitingTimeIsComputed() {
        if( Objects.isNull( waitingTimes ) || waitingTimes.length == 0 ) {
            throw new WaitingTimeNotYetComputedException();
        }
    }

    public abstract void sortByArrivalTime();

    public BigDecimal getAverageWaitingTime() {
        return BigDecimal.valueOf(Arrays.stream(waitingTimes).average().orElse(0.0));
    }

    public BigDecimal getAverageTurnAroundTime() {
        return BigDecimal.valueOf(Arrays.stream(turnAroundTimes).average().orElse(0.0));
    }

    public SimpleProcess[] getProcesses() {
        return simpleProcesses;
    }

    public BigInteger getNumberOfProcesses() {
        return numberOfProcesses;
    }

    public int[] getWaitingTimes() {
        return waitingTimes;
    }

    public int[] getTurnAroundTimes() {
        return turnAroundTimes;
    }

    public int[] getRemainingTimes() {
        return remainingTimes;
    }
}
