package main.java.com.scheduling.cpu.nonpreemptive;

import main.java.com.scheduling.cpu.base.CPUComplexScheduler;
import main.java.com.scheduling.cpu.process.compare.ExtendedProcessArrivalTimeComparator;
import main.java.com.scheduling.cpu.process.compare.SimpleProcessArrivalTimeComparator;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.base.CPUScheduler;

import java.math.BigDecimal;
import java.util.*;

public class ShortestJobFirstCPUScheduler extends CPUComplexScheduler {

    ExtendedProcess[] processes;


    public ShortestJobFirstCPUScheduler(ExtendedProcess[] processes) {
        super(processes.length);
        this.processes = processes;
        readyQueue = new PriorityQueue<>(new Comparator<ExtendedProcess>() {
            @Override
            public int compare(ExtendedProcess o1, ExtendedProcess o2) {
                if( o1.getBurstTime() < o2.getBurstTime() ) {
                    return -1;
                }else {
                    return 1;
                }
            }
        });
        ganttChart = new LinkedHashMap<>();
    }

    @Override
    public void sortByArrivalTime() {
        Arrays.sort(processes, new Comparator<ExtendedProcess>() {
            @Override
            public int compare(ExtendedProcess p1, ExtendedProcess p2) {
                if (p1.getArrivalTime() < p2.getArrivalTime())
                    return (-1);
                else if (p1.getArrivalTime() == p2.getArrivalTime() && p1.getBurstTime() < p2.getBurstTime())
                    return (-1);
                else
                    return (1);
            }
        });
    }


    @Override
    // 1=arrival, 2=burst, 3=1+2=completion, 5=3-1=tat, 4=5-2=tat-burst=waiting
    public void buildGanttChart() {
        setRemainingTimes(processes);
//        System.out.println(Arrays.toString(processes));

        jobQueue = new LinkedList<>( Arrays.asList(processes) );
        int time = processes[0].getArrivalTime();
        ExtendedProcess currentProcess = jobQueue.remove();
        ganttChart.put( time, currentProcess );

        while(true) {

            while( canProcessStillExecute(currentProcess) ) {
//                System.out.println("TIME = " + time);
//                System.out.println("current: P" + currentProcess.getProcessId());
//                System.out.println("Job Queue: " + jobQueue);
//                System.out.println("Ready Queue: " + readyQueue);

                if( !jobQueue.isEmpty() && time == jobQueue.peek().getArrivalTime()) {
                    ExtendedProcess potentialNextProcess = jobQueue.remove();
                    readyQueue.add( potentialNextProcess );
                }

                currentProcess.execute();

//                System.out.println("remaining time for current process: " + currentProcess.getRemainingTime());
//                System.out.println();
                time++;
            }

            if( !readyQueue.isEmpty() ) {
                currentProcess = readyQueue.remove();
                ganttChart.put( time, currentProcess );
            }
            if(readyQueue.isEmpty() && jobQueue.isEmpty() && !canProcessStillExecute(currentProcess)) break;

        }

        ganttChart.put( time, currentProcess );
    }

    @Override
    public void computeWaitingTime() {
    }


    @Override
    public void computeTurnAroundTime() {
        checkIfWaitingTimeIsComputed();
        for(int i=0; i < numberOfProcesses.intValue() ;i++){
//            System.out.println("i: " + i);
//            System.out.println(processes[i] + ", ct=" + completionTimes[i] + "\n");
            turnAroundTimes[i]= completionTimes[i] - processes[i].getArrivalTime();
            waitingTimes[i] = turnAroundTimes[i] - processes[i].getBurstTime();
        }
    }

    public void sortBackByProcessId() {
        Arrays.sort(processes, new Comparator<ExtendedProcess>() {
            @Override
            public int compare(ExtendedProcess o1, ExtendedProcess o2) {
                if(o1.getProcessId().compareTo( o2.getProcessId() ) < 0) {
                    return -1;
                }
                else if( o1.getProcessId().compareTo(o2.getProcessId()) > 0 ) {
                    return 1;
                }else{
                    return 0;
                }
            }
        });
    }
}
