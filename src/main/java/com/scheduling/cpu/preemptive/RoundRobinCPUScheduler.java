package main.java.com.scheduling.cpu.preemptive;

import main.java.com.scheduling.cpu.base.CPUComplexScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.process.compare.ExtendedProcessArrivalTimeComparator;
import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.process.compare.ExtendedProcessPriorityComparator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class RoundRobinCPUScheduler extends CPUComplexScheduler {

    private ExtendedProcess[] extendedProcesses;

    private BigInteger quantum;

    public RoundRobinCPUScheduler(ExtendedProcess[] extendedProcesses, BigInteger quantum ) {
        super( extendedProcesses.length );
        this.quantum = quantum;
        this.extendedProcesses = extendedProcesses;
        readyQueue = new LinkedList<>();
        ganttChart = new LinkedHashMap<>();
    }

    @Override
    public void sortByArrivalTime() {
        Arrays.sort( extendedProcesses, new ExtendedProcessArrivalTimeComparator());
    }

    @Override
    public void buildGanttChart() {
        sortByArrivalTime();
        setRemainingTimes(extendedProcesses);

        jobQueue = new LinkedList<>( Arrays.asList(extendedProcesses) );
        int time = extendedProcesses[0].getArrivalTime();
        ExtendedProcess currentProcess = jobQueue.remove();
        int ctr = 0;
        ganttChart.put( time, currentProcess );

        while( true ) {
//            System.out.println("TIME = " + time);
//            System.out.println("Counter = " + ctr);
//            System.out.println("current: P" + currentProcess.getProcessId());
//            System.out.println("Job Queue: " + jobQueue);
//            System.out.println("Ready Queue: " + readyQueue);
//
//            System.out.println("ctr >= quantum? " + ctr + " >= " + quantum.intValue() + "? " + (ctr >= quantum.intValue()));
            if (ctr >= quantum.intValue() ) {
                if( canProcessStillExecute(currentProcess) ) {
                    readyQueue.add( currentProcess );
                }
                currentProcess = jobQueue.remove();
//                System.out.println("NEW CURRENT: P" + currentProcess.getProcessId());
                ctr = 0;
                ganttChart.put( time, currentProcess );
                continue;
            }else if( !canProcessStillExecute(currentProcess) ) {
                currentProcess = jobQueue.remove();
//                System.out.println("NEW CURRENT: P" + currentProcess.getProcessId());
                ctr = 0;
                ganttChart.put( time, currentProcess );
                continue;
            }

            currentProcess.execute();
            ctr++;
//            System.out.println("remaining time for current process: " + currentProcess.getRemainingTime());
//            System.out.println();
            time++;

//            System.out.println("is job queue empty? " + jobQueue.isEmpty() + "\n");
            if( jobQueue.isEmpty() ) {
                // transfer ready queue to job queue
                while(!readyQueue.isEmpty()) {
                    jobQueue.add(readyQueue.remove());
                }
            }

            if( jobQueue.isEmpty() && readyQueue.isEmpty() && !canProcessStillExecute(currentProcess) ) break;
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
            turnAroundTimes[i]= completionTimes[i] - extendedProcesses[i].getArrivalTime();
            waitingTimes[i] = turnAroundTimes[i] - extendedProcesses[i].getBurstTime();
        }

    }

    @Override
    protected boolean canProcessStillExecute( ExtendedProcess currentProcess ) {
        if( Objects.isNull(currentProcess) ) return false;
        return currentProcess.getRemainingTime() > 0;
    }

    @Override
    protected void sortBackByProcessId() {
        Arrays.sort(extendedProcesses, new Comparator<ExtendedProcess>() {
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
