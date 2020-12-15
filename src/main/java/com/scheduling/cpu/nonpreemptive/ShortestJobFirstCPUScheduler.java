package main.java.com.scheduling.cpu.nonpreemptive;

import main.java.com.scheduling.cpu.process.compare.SimpleProcessArrivalTimeComparator;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.base.CPUScheduler;

import java.math.BigDecimal;
import java.util.Arrays;

public class ShortestJobFirstCPUScheduler extends CPUScheduler {

    ExtendedProcess[] processes;

    public ShortestJobFirstCPUScheduler(ExtendedProcess[] processes) {
        super(processes.length);
        this.processes = processes;
    }

    @Override
    protected void sortByArrivalTime() {
        Arrays.sort( processes, new SimpleProcessArrivalTimeComparator() );
    }



    // 1=arrival, 2=burst, 3=1+2=completion, 5=3-1=tat, 4=5-2=tat-burst=waiting
    private void computeCompletionTime() {
        completionTimes[0] = processes[0].getArrivalTime() + processes[0].getBurstTime();
        turnAroundTimes[0] = completionTimes[0] - processes[0].getArrivalTime();
        waitingTimes[0] = turnAroundTimes[0] - processes[0].getBurstTime(); //TA - BT
        int valueIndex = 0;

        for(int i = 1; i < numberOfProcesses.intValue(); i++) {
            int temp = completionTimes[i-1];
            int minimumBurstTime = processes[i].getBurstTime();

            for( int j = i; j < numberOfProcesses.intValue(); j++ ) {
                if( temp >= processes[j].getArrivalTime() && minimumBurstTime >= processes[j].getBurstTime() ) {
                    minimumBurstTime = processes[j].getBurstTime();
                    valueIndex = j;
                }
            }

            completionTimes[valueIndex] = temp + processes[valueIndex].getBurstTime();
            turnAroundTimes[valueIndex] = completionTimes[valueIndex] - processes[valueIndex].getArrivalTime();
            waitingTimes[valueIndex] = turnAroundTimes[valueIndex] - processes[valueIndex].getBurstTime();
        }
    }

    @Override
    public void computeWaitingTime() {
//        System.out.println("presort: " + Arrays.toString(processes));
        sortByArrivalTime();
//        System.out.println("aftersort: " + Arrays.toString(processes));
        computeCompletionTime();
    }

    @Override
    public void computeTurnAroundTime() {

    }
}
