package main.java.com.scheduling.cpu.preemptive;

import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.process.SimpleProcess;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

public class PreemptivePriorityCPUScheduler extends CPUScheduler {


    private ExtendedProcess[] processesOfPriority;

    private ExtendedProcess[] processesOfArrivalTime;


    public PreemptivePriorityCPUScheduler(ExtendedProcess[] processes) {
        super(processes.length);
        this.processesOfPriority = processes;
        this.processesOfArrivalTime = processes;
        sortAccordingToPriority();
        sortAccordingToArrivalTime();

        System.out.println(Arrays.toString(this.processesOfPriority));
        System.out.println(Arrays.toString(this.processesOfArrivalTime));
    }

    public void sortByPriorityAndArrivalTime() {
    }

    void sortAccordingToPriority() {
        Arrays.sort(processesOfPriority, Comparator.comparingInt(ExtendedProcess::getPriority));
    }

    void sortAccordingToArrivalTime() {
        Arrays.sort(processesOfArrivalTime, Comparator.comparingInt(ExtendedProcess::getArrivalTime));
    }


    @Override
    public void computeWaitingTime() {
        for(int i = 0; i < numberOfProcesses.intValue(); i++){		//waiting time
            for(int j = 0; j < numberOfProcesses.intValue(); j++){
                if(processesOfArrivalTime[i].getProcessId().intValue() != processesOfPriority[j].getProcessId().intValue()){					//repeat till find the process that is being processed
                    processesOfArrivalTime[i].setWaitingTime( processesOfArrivalTime[i].getWaitingTime() + processesOfPriority[i].getBurstTime() );
                    waitingTimes[i] = processesOfArrivalTime[i].getWaitingTime();
                }else{
                    break;
                }
            }
        }
    }

    @Override
    public void computeTurnAroundTime() {
        int total = 0;
        for(int i = 0; i < numberOfProcesses.intValue(); i++){		//TAT: turnaround time
            processesOfArrivalTime[i].setTurnAroundTime( processesOfArrivalTime[i].getWaitingTime() + processesOfArrivalTime[i].getBurstTime() );
//            System.out.println("Turnaround Time for " + processesOfArrivalTime[i].toString() + ": " + processesOfArrivalTime[i].getTurnAroundTime());
            total += processesOfArrivalTime[i].getTurnAroundTime();
            turnAroundTimes[i] = processesOfArrivalTime[i].getTurnAroundTime();
//            Process.totalTT += arr1[i].turnaroundTime;
        }

//        System.out.println("average turn around time: " + ( (float)total / (float)numberOfProcesses.intValue() ) );
    }

}
