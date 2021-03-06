package main.java.com.scheduling.cpu.process.compare;

import main.java.com.scheduling.cpu.process.ExtendedProcess;

import java.util.Comparator;

public class ExtendedProcessPriorityComparator implements Comparator<ExtendedProcess> {

    @Override
    public int compare(ExtendedProcess p1, ExtendedProcess p2) {
        if( p1.getPriority() < p2.getPriority() ) {
            return -1;
        }
        else if( p1.getPriority() == p2.getPriority() && p1.getArrivalTime() < p2.getArrivalTime() ) {
            return -1;
        }else {
            return 1;
        }
    }
}
