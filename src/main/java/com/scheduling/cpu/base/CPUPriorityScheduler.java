package main.java.com.scheduling.cpu.base;

import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.process.compare.ExtendedProcessPriorityComparator;

import java.util.*;

public abstract class CPUPriorityScheduler extends CPUScheduler {

    protected Queue<ExtendedProcess> readyQueue;

    protected Map<Integer, ExtendedProcess> ganttChart;

    protected Queue<ExtendedProcess> jobQueue;

    protected CPUPriorityScheduler(int numberOfProcesses) {
        super(numberOfProcesses);
        readyQueue = new PriorityQueue<>(new ExtendedProcessPriorityComparator());
        ganttChart = new LinkedHashMap<>();
    }

    protected boolean isPriorityHigherThanCurrentProcess(ExtendedProcess potentialNextProcess, ExtendedProcess currentProcess) {
        return potentialNextProcess.getPriority() < currentProcess.getPriority();
    }


    protected boolean canProcessStillExecute( ExtendedProcess currentProcess ) {
        if( Objects.isNull(currentProcess) ) return false;
        return currentProcess.getRemainingTime() > 0;
    }

    @Override
    public void computeSchedulingTimes() {
        buildGanttChart();
        computeCompletionTimes();
        sortBackByProcessId();
        super.computeSchedulingTimes();
    }

    protected void computeCompletionTimes() {

        Integer[] keys = ganttChart.keySet().toArray( new Integer[ganttChart.keySet().size()] );
        Set<ExtendedProcess> processesWithCompletionTime = new HashSet<>();

        for( int i = 1; i < keys.length; i++ ) {
            ExtendedProcess process = ganttChart.get( keys[i-1] );
            process.setCompletionTime(keys[i]);
            processesWithCompletionTime.add( process );
        }

        processesWithCompletionTime.forEach( p -> {
            completionTimes[p.getProcessId().intValue()-1] = p.getCompletionTime();
        });

//        System.out.println("\nCompletion Times: ");
//        System.out.println(Arrays.toString(completionTimes));
    }

    protected abstract void sortBackByProcessId();

    protected abstract void buildGanttChart();

}
