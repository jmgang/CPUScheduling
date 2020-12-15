package main.java.com.scheduling.cpu.nonpreemptive;

import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.process.compare.ExtendedProcessPriorityComparator;

import java.math.BigDecimal;
import java.util.Arrays;

public class NonPreemptivePriorityCPUScheduler extends CPUScheduler {

    private ExtendedProcess[] processes;

    public NonPreemptivePriorityCPUScheduler(ExtendedProcess[] processes ) {
        super(processes.length);
        this.processes = processes;
        sortAccordingArrivalTimeAndPriority();
    }

    private void sortAccordingArrivalTimeAndPriority() {

//        for (int i = 0; i < numberOfProcesses.intValue(); i++)
//        {
//            for (int j = 0; j < numberOfProcesses.intValue() - i - 1; j++)
//            {
//                if (processes[j].getArrivalTime() > processes[j + 1].getArrivalTime())
//                {
//                    ExtendedProcess temp = processes[j];
//                    processes[j] = processes[j + 1];
//                    processes[j+1] = temp;
//
//                }
//                //sorting according to priority when arrival timings are same
//                if (processes[j].getArrivalTime() == processes[j + 1].getArrivalTime())
//                {
//                    if (processes[j].getPriority() > processes[j + 1].getPriority()){
//                        ExtendedProcess temp = processes[j];
//                        processes[j] = processes[j+1];
//                        processes[j+1] = temp;
//                    }
//                }
//            }
//
//        }
        Arrays.sort( processes, new ExtendedProcessPriorityComparator());
    }

    @Override
    public void computeWaitingTime() {
        completionTimes[0] = processes[0].getArrivalTime() + processes[0].getBurstTime();
        turnAroundTimes[0] = completionTimes[0] - processes[0].getArrivalTime();
        waitingTimes[0] = turnAroundTimes[0] - processes[0].getBurstTime(); //TA - BT

        for (int i = 1; i < numberOfProcesses.intValue(); i++){
            completionTimes[i] = processes[i].getBurstTime() + completionTimes[i - 1];
            turnAroundTimes[i] = completionTimes[i] - processes[i].getArrivalTime();
            waitingTimes[i] = turnAroundTimes[i] - processes[i].getBurstTime();
        }
    }

    @Override
    public void computeTurnAroundTime() {

    }

}

