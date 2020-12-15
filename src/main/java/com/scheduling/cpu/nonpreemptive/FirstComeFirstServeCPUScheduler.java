package main.java.com.scheduling.cpu.nonpreemptive;

import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.base.CPUScheduler;

import java.math.BigDecimal;

public class FirstComeFirstServeCPUScheduler extends CPUScheduler {

    private ExtendedProcess[] processes;

    public FirstComeFirstServeCPUScheduler(ExtendedProcess[] processes) {
        super(processes.length);
        this.processes = processes;
    }

    @Override
    public void computeWaitingTime() {

        waitingTimes[0] = 0;

        // Calculating waiting time for
        // each process from the given
        // formula
        for (int i = 1; i < numberOfProcesses.intValue(); i++) {
            waitingTimes[i] = (processes[i - 1].getArrivalTime() + processes[i - 1].getBurstTime() + waitingTimes[i - 1]) - processes[i].getArrivalTime();
        }
    }

    @Override
    public void computeTurnAroundTime() {
        for (int i = 0; i < numberOfProcesses.intValue(); i++) {
            turnAroundTimes[i] = processes[i].getBurstTime() + waitingTimes[i];
        }
    }
}
