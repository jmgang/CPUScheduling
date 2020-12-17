package main.java.com.scheduling.cpu.preemptive;

import main.java.com.scheduling.cpu.base.CPUComplexScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.process.compare.ExtendedProcessArrivalTimeComparator;

import java.util.*;

public class PreemptivePriorityCPUScheduler extends CPUComplexScheduler {

    private ExtendedProcess[] processes;

    public PreemptivePriorityCPUScheduler(ExtendedProcess[] processes) {
        super(processes.length);
        this.processes = processes;
    }

    @Override
    public void sortByArrivalTime() {
        Arrays.sort( processes, new ExtendedProcessArrivalTimeComparator() );
    }

    @Override
    public void buildGanttChart() {
        sortByArrivalTime();
        jobQueue = new LinkedList<>( Arrays.asList(processes) );
        int time = 0;
        ExtendedProcess currentProcess = null;

        while( true ) {
            while (canProcessStillExecute(currentProcess)) {
//                System.out.println("TIME = " + time);
//                System.out.println("current: P" + currentProcess.getProcessId());
//                System.out.println("Job Queue: " + jobQueue);
//                System.out.println("Ready Queue: " + readyQueue);
//                System.out.println("Potential Next process: " + (!jobQueue.isEmpty() ? "P" + jobQueue.peek().getProcessId() : "N/A"));
                if( !jobQueue.isEmpty() && time == jobQueue.peek().getArrivalTime()) {
                    ExtendedProcess potentialNextProcess = jobQueue.remove();
                    if( isPriorityHigherThanCurrentProcess(potentialNextProcess, currentProcess) ) {
//                        System.out.println("P" + potentialNextProcess.getProcessId() + " is higher than current process: P" + currentProcess.getProcessId());
                        // preempt current process and bring the crown to the next process
                        readyQueue.add( currentProcess );
                        currentProcess = potentialNextProcess;
                        ganttChart.put( time, currentProcess );
//                        System.out.println("NEW CURRENT: P" + currentProcess.getProcessId());
                    } else {
                        readyQueue.add( potentialNextProcess );
                    }
                }

                currentProcess.execute(); //decrement remaining times by 1

//                System.out.println("remaining time for current process: " + currentProcess.getRemainingTime());
//                System.out.println();

                time++;
            }

            if( !jobQueue.isEmpty() ) {
                //
                if( !readyQueue.isEmpty() ) {
                    currentProcess = readyQueue.remove();
                }else{
                    currentProcess = jobQueue.remove();
                }

            }else { //non-preemptive scheduling at this pt
                // get from ready queue
                if( !readyQueue.isEmpty() ) {
                    currentProcess = readyQueue.remove();
                } else{
                    break; // if both are empty
                }

            }
            ganttChart.put( time, currentProcess );

        }

        // +1 on last
        ganttChart.put( time, currentProcess );
    }



    // Turnaround Time = Completion Time - Arrival Time
    // Waiting Time = Turn Around Time - Burst Time
    @Override
    public void computeWaitingTime() {
        for( int i = 0; i < processes.length; i++ ) {
            turnAroundTimes[i] = completionTimes[i] - processes[i].getArrivalTime();
            waitingTimes[i] = turnAroundTimes[i] - processes[i].getBurstTime();
        }

//        System.out.println("Waiting: " + Arrays.toString( waitingTimes ));
//        System.out.println("TurnAround: " + Arrays.toString( turnAroundTimes ));
    }

    @Override
    public void computeTurnAroundTime() {

    }

    @Override
    protected void sortBackByProcessId() {
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
